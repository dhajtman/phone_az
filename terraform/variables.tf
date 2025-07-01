variable "aws_region" {
  description = "The AWS region"
  type        = string
  default     = "us-east-1"
}

variable "ecr_repository" {
  description = "The AWS repository"
  type        = string
  default     = "docker-repo"
}

variable "ecs_cluster" {
  description = "The name of the ECS cluster"
  type        = string
  default     = "ecs-cluster"
}

variable "ecs_service" {
  description = "The name of the ECS service"
  type        = string
  default     = "ecs-service"
}

variable "openai_api_key" {
  description = "OpenAI API key"
  type        = string
  sensitive   = true
}