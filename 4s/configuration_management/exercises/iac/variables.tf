variable "project_id" {
  description = "The GCP Project ID where resources will be created"
  type        = string
}

variable "student_name" {
  description = "Your name to be displayed on the web page"
  type        = string
  default     = "Warley"
}

variable "region" {
  description = "GCP Region"
  type        = string
  default     = "us-central1"
}

variable "zone" {
  description = "GCP Zone"
  type        = string
  default     = "us-central1-a"
}
