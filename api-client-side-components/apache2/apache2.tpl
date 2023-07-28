job "apache2" {
  namespace = "editeur"
  datacenters = [
    "${datacenter}"]
  type = "service"

  vault {
    policies = [
      "editeur"]
    change_mode = "restart"
  }

  group "apache2" {
    count = 1

    restart {
      attempts = 3
      delay = "60s"
      interval = "1h"
      mode = "fail"
    }

    update {
      max_parallel = 1
      min_healthy_time = "30s"
      progress_deadline = "5m"
      healthy_deadline = "2m"
    }

    network {
      port "https" {
        to = 443
      }
    }

    task "apache2" {
      driver = "docker"
     
      template {
        data = <<EOH
PUBLIC_HOSTNAME={{ with secret "editeur/apache2" }}{{ .Data.data.public_hostname }}{{ end }}
EOH
        destination = "local/file.env"
        change_mode = "restart"
        env = true
      }
      config {
        image = "${image}:${tag}"
        ports = ["https"]             
      }
      resources {
        cpu = 200
        memory = 512
      }
      service {
        name = "$\u007BNOMAD_JOB_NAME\u007D"
        port = "https"
        check {
          name         = "alive"
          type         = "tcp"
          interval     = "30s"
          timeout      = "5s"
          failures_before_critical = 5
          port         = "https"
        }
      }
    }
  }
}
