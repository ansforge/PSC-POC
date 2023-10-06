job "db-esignsante-psc" {
	namespace = "esignsante"	             	
	datacenters = ["${datacenter}"]
	type = "service"

	vault {
		policies = ["esignsante"]
		change_mode = "restart"
	}
	
	constraint {
      attribute = "$\u007Bnode.class\u007D"
      value     = "data"
    }

	group "esignsante-mongodb" {
		count = "1"
		restart {
			attempts = 3
			delay = "60s"
			interval = "1h"
			mode = "fail"
		}
		network {
			port "db" { to = 27017 }
		}
			
        volume "esignsante" {
          type      = "host"
          read_only = false
          source    = "esignsante"
        }
		
		task "db-esignsante-psc" {
			driver = "docker"
		    volume_mount {
              volume      = "esignsante"
              destination = "/data/db"
              read_only   = false
            }
			config {
				image = "mongo:${tag}"
				ports  = [ "db" ]	
			}
		    template {
data = <<EOH
  {{ with secret "esignsante-psc/esignsante-psc-db" }}
MONGO_INITDB_ROOT_USERNAME = {{ .Data.data.root_user }}
MONGO_INITDB_ROOT_PASSWORD = {{ .Data.data.root_pass }}{{ end }}
EOH
			destination = "secrets/.env"
			change_mode = "restart"
			env = true
		    }

			resources {
				cpu = 500
				memory = 2048
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
