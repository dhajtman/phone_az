variable "resource_group_name" {
  default = "phone-az-rg"
}

variable "location" {
  default = "westeurope"
}

variable "acr_name" {
  default = "phoneregistry"
}

variable "app_name" {
  default = "phoneapp"
}

variable "container_image_tag" {
  default = "latest"
}

variable "openai_api_key" {
  description = "OpenAI API key"
  type        = string
  sensitive   = true
}

variable "enable_app" {
  type    = bool
  default = false
}

variable "app_port" {
  description = "Port on which the app will run"
  type        = number
  default     = 8080
}

variable "image_name" {
  description = "Name of the container image"
  type        = string
  default     = "phone-app"
}