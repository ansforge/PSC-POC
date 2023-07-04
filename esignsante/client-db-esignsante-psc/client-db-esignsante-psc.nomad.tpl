job "psc-mongo-express" {
  datacenters = ["${datacenter}"]
  type = "service"
  namespace = "esignsante"
  vault {
    policies = ["esignsante"]
    change_mode = "restart"
  }

  group "psc-mongo-express" {
    count = 1
    restart {
      attempts = 3
      delay = "60s"
      interval = "1h"
      mode = "fail"
    }

    network {
      port "ui" { to = 8081 }
    }

    task "psc-mongo-express" {
      driver = "docker"
      template {
        data = <<EOH
ME_CONFIG_MONGODB_SERVER = {{ range service "db-esignsante-psc" }}{{ .Address }}{{ end }}
ME_CONFIG_MONGODB_PORT = {{ range service "db-esignsante-psc" }}{{ .Port }}{{ end }}
ME_CONFIG_MONGODB_ADMINUSERNAME = {{ with secret "esignsante-psc/esignsante-psc-db" }}{{ .Data.data.root_user }}{{ end }}
ME_CONFIG_MONGODB_ADMINPASSWORD = {{ with secret "esignsante-psc/esignsante-psc-db" }}{{ .Data.data.root_pass }}{{ end }}
ME_CONFIG_SITE_BASEURL = "/psc-db/"
ME_CONFIG_MONGODB_ENABLE_ADMIN = true

ME_CONFIG_OPTIONS_READONLY = true
ME_CONFIG_OPTIONS_NO_DELETE = true
EOH
        destination = "secrets/file.env"
        change_mode = "restart"
        env = true
      }

      config {
        image = "${artifact.image}:${artifact.tag}"
        ports = ["ui"]
      }
      resources {
        cpu    = 1000
        memory = 512
      }
      service {
        name = "$\u007BNOMAD_JOB_NAME\u007D"
        port = "ui"
        tags = ["urlprefix-/psc-db/"]
        check {
          name         = "alive"
          type         = "tcp"
          interval     = "30s"
          timeout      = "5s"
          failures_before_critical = 5
          port         = "ui"
        }
      }
    }
  }
}
