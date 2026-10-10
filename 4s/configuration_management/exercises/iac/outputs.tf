output "public_ip" {
  description = "The external public IP address of the web server"
  value       = google_compute_instance.web_server.network_interface[0].access_config[0].nat_ip
}

output "website_url" {
  description = "URL to test in your browser"
  value       = "http://${google_compute_instance.web_server.network_interface[0].access_config[0].nat_ip}"
}
