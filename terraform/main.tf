
terraform {
  required_providers {
    azurerm = {
      source  = "hashicorp/azurerm"
      version = "~> 3.0"
    }
  }
}

provider "azurerm" {
  features {
    resource_group {
      prevent_deletion_if_contains_resources = false
    }
  }
}

resource "azurerm_resource_group" "main" {
  name     = var.resource_group_name
  location = var.location
}

resource "azurerm_container_registry" "acr" {
  name                = var.acr_name
  resource_group_name = azurerm_resource_group.main.name
  location            = azurerm_resource_group.main.location
  sku                 = "Basic"
  admin_enabled       = true
}

resource "azurerm_container_app_environment" "env" {
  name                = "phone-env"
  location            = azurerm_resource_group.main.location
  resource_group_name = azurerm_resource_group.main.name
}

resource "azurerm_user_assigned_identity" "uai" {
  name                = "phoneapp-identity"
  resource_group_name = azurerm_resource_group.main.name
  location            = azurerm_resource_group.main.location
}

resource "azurerm_role_assignment" "acr_pull" {
  scope                = azurerm_container_registry.acr.id
  role_definition_name = "AcrPull"
  principal_id         = azurerm_user_assigned_identity.uai.principal_id
}

resource "azurerm_container_app" "app" {
  count = var.enable_app ? 1 : 0

  name                         = var.app_name
  container_app_environment_id = azurerm_container_app_environment.env.id
  resource_group_name          = azurerm_resource_group.main.name
  revision_mode                = "Single"

  template {
    container {
      name   = var.image_name
      image  = "${azurerm_container_registry.acr.login_server}/${var.image_name}:${var.container_image_tag}"
      cpu    = 0.5
      memory = "1.0Gi"

      env {
        name  = "OPENAI_API_KEY"
        value = var.openai_api_key
      }
    }
  }

  ingress {
    external_enabled = true
    target_port      = var.app_port
    traffic_weight {
      percentage      = 100
      latest_revision = true
    }
  }

  identity {
    type         = "UserAssigned"
    identity_ids = [azurerm_user_assigned_identity.uai.id]
  }

  registry {
    server = azurerm_container_registry.acr.login_server
    identity = azurerm_user_assigned_identity.uai.id
  }
}

output "container_app_url" {
  value = var.enable_app ? "https://${azurerm_container_app.app[0].ingress[0].fqdn}/swagger-ui/index.html" : null
  description = "Swagger UI URL of the deployed Azure Container App"
}