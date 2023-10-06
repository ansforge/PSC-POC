job "gravitee-mongodb" {
  namespace = "gravitee"
  datacenters = ["${datacenter}"]
  type = "service"

  vault {
    policies = ["gravitee"]
    change_mode = "restart"
  }

  constraint {
    attribute = "$\u007Bnode.class\u007D"
    value     = "data"
   }

  group "gravitee-mongodb" {
    count = 1
    
    volume "gravitee-mongo" {
      type      = "host"
      read_only = false
      source    = "gravitee-mongodb"
    }

    restart {
      attempts = 3
      delay = "60s"
      interval = "1h"
      mode = "fail"
    }

    update {
      max_parallel      = 1
      min_healthy_time  = "30s"
      progress_deadline = "5m"
      healthy_deadline  = "2m"
    }

    network {
      port "db" { to = 27017 }
    }

    task "gravitee-mongodb" {
      driver = "docker"
      volume_mount {
        volume      = "gravitee-mongo"
        destination = "/data/db"
        read_only   = false
      }
    
      template {
        data = <<EOH
MONGO_INITDB_ROOT_USERNAME = {{ with secret "gravitee/mongodb" }}{{ .Data.data.root_user }}{{ end }}
MONGO_INITDB_ROOT_PASSWORD = {{ with secret "gravitee/mongodb" }}{{ .Data.data.root_pass }}{{ end }}
MONGO_INITDB_DATABASE=gravitee
        EOH
        destination = "secrets/.env"
        change_mode = "restart"
        env = true
      }
      config {
        image = "${image}:${tag}"
        ports = ["db"]
      }
    
      resources {
        cpu    = 200
        memory = 3000
      }
      service {
        name = "$\u007BNOMAD_JOB_NAME\u007D"
        port = "db"
        check {
          name         = "alive"
          type         = "tcp"
          interval     = "30s"
          timeout      = "5s"
          failures_before_critical = 5
          port         = "db"
        }
      }
    }

  }
}
