job "keycloak-server" {

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
	
    group "keycloak-server" {
        count = 1
        network {
            mode = "host"
            port "http-port" { to = 8080 }
			port "https-port" { to = 8443 }
        }
		
        task "keycloak-server" {

            driver = "docker"

            config {
                image = "${image}:${tag}"				
                ports = ["http-port", "https-port"]
            }
			
			template {
				data = <<EOH
<<<<<<< HEAD
					KEYCLOAK_ADMIN_USER = {{ with secret "keycloak/keycloak-db" }}{{ .Data.data.keycloak_admin_user }}{{ end }}
					KEYCLOAK_ADMIN_PASSWORD = {{ with secret "keycloak/keycloak-db" }}{{ .Data.data.keycloak_admin_password }}{{ end }}
=======
					KEYCLOAK_ADMIN_USER = {{ with secret "keycloak/keycloak-server" }}{{ .Data.data.keycloak_admin_user }}{{ end }}
					KEYCLOAK_ADMIN_PASSWORD = {{ with secret "keycloak/keycloak-server" }}{{ .Data.data.keycloak_admin_password }}{{ end }}
>>>>>>> 63bc179fbdb2f4f414a4656c0da02ccc37ba819e
					KEYCLOAK_DATABASE_HOST = {{ range service "keycloak-db"}}{{ .Address }}{{ end }}
					KEYCLOAK_DATABASE_PORT = {{ range service "keycloak-db"}}{{ .Port }}{{ end }}
					KEYCLOAK_DATABASE_USER = {{ with secret "keycloak/keycloak-db" }}{{ .Data.data.pgsql_user }}{{ end }}
					KEYCLOAK_DATABASE_PASSWORD = {{ with secret "keycloak/keycloak-db" }}{{ .Data.data.pgsql_password }}{{ end }}
					KEYCLOAK_DATABASE_NAME = {{ with secret "keycloak/keycloak-db" }}{{ .Data.data.pgsql_dbname }}{{ end }}
				EOH
				
				destination = "secrets/file.env"
				env         = true
			}
			
            resources {
                cpu = 1000
                memory = 2000
            }
	    	   
            service {
                name = "keycloak-server-http"
                port = "http-port"
                check {
                    name         = "alive"
                    type         = "tcp"
                    interval     = "10s"
                    timeout      = "5s"
                    port         = "http-port"
                }
            }
        }
    }
}
