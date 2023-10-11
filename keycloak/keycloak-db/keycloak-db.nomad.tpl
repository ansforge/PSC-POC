#
# (c) Copyright 2023, ANS. All rights reserved.
#
job "keycloak-db" {

    namespace = "keycloak"

    type = "service"

    datacenters = ["${datacenter}"]
    
    constraint {
      attribute = "$\u007Bnode.class\u007D"
      value     = "data"
    }

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
		
		volume "keycloak-db" {
			type      = "host"
			read_only = false
			source    = "keycloak-db"
		}
		
        task "keycloak-db" {

            driver = "docker"
			
			volume_mount {
				volume      = "keycloak-db"
				destination = "/var/lib/postgresql/data"
				read_only   = false
			}

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
			    check {
	              name         = "db request"   
		          type         = "script"
		          command      = "/bin/bash"
                  args         = [
                    "-c",
                    "if [ \"$(psql -dkeycloak-db -Uroot -X -A -w -t -q -c "SELECT datname FROM pg_database WHERE datname='keycloak-db';")\" = \"keycloak-db\" ]; then exit 0; else exit 2; fi"
                   ]
                  interval     = "30s"          
                  timeout      = "5s"				  
                }	
            }
        }
    }
}
