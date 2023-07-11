job "squid" {

    namespace = "squid"

    type = "service"

    datacenters = ["${datacenter}"]

    update {
        stagger = "30s"
        max_parallel = 1
    }
	
    group "squid" {
        count = 1
        network {
            mode = "host"
            port "http-port" { to = 3128 }
        }
		
        task "squid" {

            driver = "docker"
			
			template {
				data = <<EOF
acl hasRequest has request
access_log none !hasRequest
EOF
				destination = "local/main.conf"
			}

            config {
                image = "${image}:${tag}"				
                ports = ["http-port"]
				
				mount {
				  type = "bind"
				  target = "/etc/squid/conf.d/main.conf"
				  source = "local/main.conf"
				  readonly = false
				  bind_options {
					propagation = "rshared"
				  }
				}
            }
			 

			
            resources {
                cpu = 1000
                memory = 2000
            }
	    	   
            service {
                name = "squid"
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
