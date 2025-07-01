# Simple Spring Boot Application running on KinD

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
   docker build -t telekom-app-api .
   ```
3. **Run the Docker container:**
   ```bash
   docker run --name telekom-app-api -p 8080:8080 -t telekom-spring-boot-docker
   ```

### Deploying app to k8s
1. **Create KinD cluster:**
   ```bash
   kind create cluster --config k8s/kind-cluster.yml
   ```
2. **Install Ingress Controller:**
   ```bash
   kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/main/deploy/static/provider/kind/deploy.yaml
   ```
3. **Build and tag docker image:**
   ```bash
   docker build -t telekom-app-api:1.0.0 -f k8s/Dockerfile .
   ```
4. **Load Docker image into KinD:**
   ```bash
   kind load docker-image telekom-app-api:1.0.0 --name=phone
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

### Setting up AWS
1. **Create Service Account and key:**
   ```bash
   IAM_USER_NAME="cicd_sa"
   aws iam create-user --user-name $IAM_USER_NAME
   aws iam put-user-policy \
     --user-name cicd_sa \
     --policy-name FullTerraformCICDPolicy \
     --policy-document '{
       "Version": "2012-10-17",
       "Statement": [
         {
           "Effect": "Allow",
           "Action": [
             "ec2:*",
             "elasticloadbalancing:*",
             "ecr:*",
             "ecs:*",
             "iam:*",
             "logs:*"
           ],
           "Resource": "*"
         }
       ]
     }'
   aws iam create-access-key --user-name $IAM_USER_NAME
   ```
2. **Set Gitlab CI/CD variables:**
   - `AWS_ACCOUNT_ID`: your AWS account ID
   - `AWS_ACCESS_KEY_ID`: your AWS access key ID
   - `AWS_SECRET_ACCESS_KEY`: your AWS secret access key