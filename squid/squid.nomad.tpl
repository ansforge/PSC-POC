job "squid" {

    namespace = "squid"

    type = "service"
	
	vault {
		policies = ["squid"]
		change_mode = "restart"
    }

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
			port "https-port" { to = 3129 }
        }
		
        task "squid" {

            driver = "docker"
			
			template {
				data = <<EOF
# Log cleanup
acl hasRequest has request
access_log none !hasRequest

debug_options ALL,6

# Configure SSL Bump options (for Squid 5.x)
tls_outgoing_options cert=/etc/squid/mtls.pem key=/etc/squid/mtls.key flags=DONT_VERIFY_PEER,DONT_VERIFY_DOMAIN

# Define ACLs for SSL Bump
acl psc_mtls dstdom_regex -i ^\.\*\.bas\.psc\.esante\.gouv\.fr$

# Use SSL Bump for matching ACLs
ssl_bump peek step1 all
ssl_bump splice psc_mtls

# Forward requests to the identity provider
cache_peer wallet.bas.psc.esante.gouv.fr parent 443 0 no-query originserver name=psc_wallet
cache_peer auth.bas.psc.esante.gouv.fr parent 443 0 no-query originserver name=psc_auth

# Define an ACL for your web applications
acl web_apps src 192.168.0.0/16

# Forward requests to the identity provider only for the specified web applications
http_access allow web_apps psc_mtls

# Default behavior: let all other requests pass through
http_access allow all
EOF
				destination = "local/mtls.conf"
			}
			
			template {
				data = <<EOF
{{ with secret "squid/mtls" }}
{{ .Data.data.mtls_pem }}
{{ end }}
EOF
				destination = "local/mtls.pem"
			}
			
			template {
				data = <<EOF
{{ with secret "squid/mtls" }}
{{ .Data.data.mtls_key }}
{{ end }}
EOF
				destination = "local/mtls.key"
			}

            config {
                image = "${image}:${tag}"				
                ports = ["http-port"]
				
				mount {
				  type = "bind"
				  target = "/etc/squid/conf.d/mtls.conf"
				  source = "local/mtls.conf"
				  readonly = false
				  bind_options {
					propagation = "rshared"
				  }
				}
				
				mount {
				  type = "bind"
				  target = "/etc/squid/mtls.pem"
				  source = "local/mtls.pem"
				  readonly = false
				  bind_options {
					propagation = "rshared"
				  }
				}
				
				mount {
				  type = "bind"
				  target = "/etc/squid/mtls.key"
				  source = "local/mtls.key"
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
