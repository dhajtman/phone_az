# Spring Boot Application running on KinD or Azure Container Apps

## Project Requirements

### Prerequisites
- Java 17
- Maven 3.6+
- Docker

### Case study
Create a Java application that returns a sorted list of phones using a REST interface. The phones in the list should be sorted in ascending order by name and the list should contain only unique items. Each phone should have an identifier and a name. The list of phones will be stored directly in memory. The task also includes updating the data from the primary source (which can be statically defined in a Java class) into memory. The list of phones should contain at least 3 items. The project should be covered by unit tests. (Preferred use of Spring Boot framework and Swagger interface)

### Building the Project
1. **Package the project using Maven:**
   ```bash
   mvn package
   ```
2. **Build the Docker image:**
   ```bash
   docker build -t phone-app .
   ```
3. **Run the Docker container:**
   ```bash
   docker run --name phone-app-api -p 8080:8080 -t phone-app
   ```

### Deploying app to KinD k8s
1. **Create KinD cluster:**
   ```bash
   kind create cluster --bean k8s/kind-cluster.yml
   ```
2. **Install Ingress Controller:**
   ```bash
   kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/main/deploy/static/provider/kind/deploy.yaml
   ```
3. **Build and tag docker image:**
   ```bash
   docker build -t phone-app-api:1.0.0 -f k8s/Dockerfile .
   ```
4. **Load Docker image into KinD:**
   ```bash
   kind load docker-image phone-app-api:1.0.0 --name=phone
   ```
5. **Apply Deployment, Service and Ingress configuration:**
   ```bash
   kubectl apply -f k8s/app-deploy.yml
   ```
6. **Delete KinD cluster:**
   ```bash
   kind delete cluster --name=phone 
   ```
7. **Access the application:**
   http://localhost/swagger-ui/index.html

### Deploying KinD Dashboard 
1. **Add repository:**
   ```bash
   helm repo add kubernetes-dashboard https://kubernetes.github.io/dashboard/
   ```
2. **Install dashboard:**
   ```bash
   helm upgrade --install kubernetes-dashboard k8s-dashboard/kubernetes-dashboard --create-namespace -n kubernetes-dashboard
   ```
3. **List Dashboard resources:**
   ```bash
   kubectl get all -n kubernetes-dashboard
   ```
4. **Forward dashboard service:**
   ```bash
   kubectl port-forward service/kubernetes-dashboard-kong-proxy 8443:443 -n kubernetes-dashboard
   ```
5. **Deploy service account:**
   ```bash
   kubectl apply -f k8s/service-account.yml
   ```
6. **Generate token:**
   ```bash
   kubectl -n kube-system create token admin-user
   ```
7. **Access the dashboard:**
   http://localhost:8443/ with generated token

### Running Tests
To run the tests, use the following Maven command:
```bash
mvn test
```

### Application Endpoints
- **GET /phone/all: Retrieve all phones**
- **POST /phone/update: Update phones from the primary source**

### Configuration
The application can be configured using the application.properties file located in src/main/resources:
```properties
spring.application.name=phone
```

### Application Access
Access application Swagger UI at: http://localhost:8080/swagger-ui/index.html

## Deploying to Azure Container Apps with Terraform using Github Actions

### Setting up Azure
1. **Create Service Principal:**
   ```bash
   az login
   az account list --output table
   az account set --subscription "<your-subscription-name-or-id>"
   az ad sp create-for-rbac \
     --name "cicd-sp" \
     --role Contributor \
     --scopes /subscriptions/<subscription-id> \
     --sdk-auth
   az role assignment create \
     --assignee <clientId-from-previous-command> \
     --role "User Access Administrator" \
     --scope /subscriptions/<subscription-id>
   az provider register --namespace Microsoft.App
   ```
   
2. **Set Github variables based on your Terraform Cloud values:**
   - `AZURE_CLIENT_ID`: your Azure Client ID from the service principal
   - `AZURE_CLIENT_SECRET`: your Azure Client Secret from the service principal
   - `AZURE_TENANT_ID`: your Azure Tenant ID from the service principal
   - `AZURE_SUBSCRIPTION_ID`: your Azure Subscription ID
   - `TF_API_TOKEN`: your Terraform Cloud API token
   - `TF_CLOUD_ORGANIZATION`: your Terraform Cloud organization name
   - `TF_CLOUD_WORKSPACE`: your Terraform Cloud workspace name

3. **Terraform Cloud variables:**
   - `AZURE_CLIENT_ID`: your Azure Client ID from the service principal
   - `AZURE_CLIENT_SECRET`: your Azure Client Secret from the service principal
   - `AZURE_TENANT_ID`: your Azure Tenant ID from the service principal
   - `AZURE_SUBSCRIPTION_ID`: your Azure Subscription ID
   - `ARM_CLIENT_ID`: your Azure Client ID from the service principal
   - `ARM_CLIENT_SECRET`: your Azure Client Secret from the service principal
   - `ARM_TENANT_ID`: your Azure Tenant ID from the service principal
   - `ARM_SUBSCRIPTION_ID`: your Azure Subscription ID
   - `OPENAI_API_KEY`: your OpenAI API key

### Accessing Azure Deployed Application
Look at deploy Output Github action job report
```
Outputs:
container_app_url = "https://phoneapp.bravestone-704482eb.westeurope.azurecontainerapps.io/swagger-ui/index.html"
```

## Start Docker services locally
```bash
./compose-keycloak/start.sh
```
### Accessing Keycloak
Open your browser and navigate to: http://localhost:9090

### Obtaining access token from Keycloak
```bash
curl -X POST http://localhost:9090/realms/spring-app/protocol/openid-connect/token \
  -d "client_id=spring-client" \
  -d "username=testuser" \
  -d "password=password" \
  -d "grant_type=password" \
  -d "client_secret=5esbIFnW4taZvNXeoVRJFafhzthweA7t"
```

### Testing secured endpoint
```bash
curl -X GET http://localhost:8090/api/hello \                                       
  -H "Authorization: Bearer <access_token>"
```