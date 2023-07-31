job "keycloak-db" {

    namespace = "keycloak"

    type = "service"

    datacenters = ["${datacenter}"]

    update {
        stagger = "30s"
        max_parallel = 1
    }
	
	vault {
		policies = ["keycloak"]
		change_mode = "restart"
	}
	
    group "keycloak-db" {
        count = 1
        network {
            mode = "host"
            port "psql-port" { to = 5432 }
        }
		
        task "keycloak-db" {

            driver = "docker"

            config {
                image = "${image}:${tag}"				
                ports = ["psql-port"]
            }
			
			template {
				data = <<EOH
					POSTGRES_USER = {{ with secret "keycloak/keycloak-db" }}{{ .Data.data.pgsql_user }}{{ end }}
					POSTGRES_PASSWORD = {{ with secret "keycloak/keycloak-db" }}{{ .Data.data.pgsql_password }}{{ end }}
					POSTGRES_DB = {{ with secret "keycloak/keycloak-db" }}{{ .Data.data.pgsql_dbname }}{{ end }}
				EOH
				
				destination = "secrets/file.env"
				env         = true
			}
			
            resources {
                cpu = 1000
                memory = 4000
            }
	    	   
            service {
                name = "keycloak-db"
                port = "psql-port"
                check {
                    name         = "alive"
                    type         = "tcp"
                    interval     = "10s"
                    timeout      = "5s"
                    port         = "psql-port"
                }
            }
        }
    }
}
