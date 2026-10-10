terraform {
  required_version = ">= 1.0"
  required_providers {
    google = {
      source  = "hashicorp/google"
      version = "~> 5.0"
    }
  }
}

provider "google" {
  project = var.project_id
  region  = var.region
  zone    = var.zone
}

# Firewall rule to allow incoming HTTP traffic on port 80
resource "google_compute_firewall" "allow_http" {
  name    = "allow-http-iac"
  network = "default"

  allow {
    protocol = "tcp"
    ports    = ["80"]
  }

  source_ranges = ["0.0.0.0/0"]
  target_tags   = ["http-server"]
}

# Compute Engine VM Instance (e2-micro free-tier eligible)
resource "google_compute_instance" "web_server" {
  name         = "iac-web-server"
  machine_type = "e2-micro"
  zone         = var.zone

  tags = ["http-server"]

  boot_disk {
    initialize_params {
      image = "debian-cloud/debian-12"
    }
  }

  network_interface {
    network = "default"
    access_config {
      // Gives the instance a public external IP
    }
  }

  # Startup script runs on boot to install Apache and configure the web page
  metadata_startup_script = <<-EOF
    #!/bin/bash
    apt-get update -y
    apt-get install -y apache2
    echo "Eu ${var.student_name} consegui usar IaC na Google Cloud na disciplina de Gerência de Configuração - UFC Quixadá" > /var/www/html/index.html
    systemctl restart apache2
  EOF
}
