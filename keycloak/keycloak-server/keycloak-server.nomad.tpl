job "keycloak-server" {

    namespace = "keycloak"

    type = "service"

    datacenters = ["${datacenter}"]

    update {
        stagger = "30s"
        max_parallel = 1
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
			
			service {
                name = "keycloak-server-https"
                port = "https-port"
                check {
                    name         = "alive"
                    type         = "tcp"
                    interval     = "10s"
                    timeout      = "5s"
                    port         = "https-port"
                }
            }
        }
    }
}
