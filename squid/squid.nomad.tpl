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
        }
		
        task "squid" {

            driver = "docker"
			
			template {
				data = <<EOF
#acl localnet src 0.0.0.1-0.255.255.255	# RFC 1122 "this" network (LAN)
#acl localnet src 10.0.0.0/8		# RFC 1918 local private network (LAN)
#acl localnet src 100.64.0.0/10		# RFC 6598 shared address space (CGN)
#acl localnet src 169.254.0.0/16 	# RFC 3927 link-local (directly plugged) machines
#acl localnet src 172.16.0.0/12		# RFC 1918 local private network (LAN)
#acl localnet src 192.168.0.0/16		# RFC 1918 local private network (LAN)
#acl localnet src fc00::/7       	# RFC 4193 local private network range
#acl localnet src fe80::/10      	# RFC 4291 link-local (directly plugged) machines
#acl SSL_ports port 443
#acl Safe_ports port 80		# http
#acl Safe_ports port 21		# ftp
#acl Safe_ports port 443		# https
#acl Safe_ports port 70		# gopher
#acl Safe_ports port 210		# wais
#acl Safe_ports port 1025-65535	# unregistered ports
#acl Safe_ports port 280		# http-mgmt
#acl Safe_ports port 488		# gss-http
#acl Safe_ports port 591		# filemaker
#acl Safe_ports port 777		# multiling http
#http_access deny !Safe_ports
#http_access deny CONNECT !SSL_ports
#http_access allow localhost manager
#http_access deny manager
#http_access allow localhost
#http_access deny all
http_port 3128
include /etc/squid/conf.d/*.conf
coredump_dir /var/spool/squid
refresh_pattern ^ftp:		1440	20%	10080
refresh_pattern ^gopher:	1440	0%	1440
refresh_pattern -i (/cgi-bin/|\?) 0	0%	0
refresh_pattern \/(Packages|Sources)(|\.bz2|\.gz|\.xz)$ 0 0% 0 refresh-ims
refresh_pattern \/Release(|\.gpg)$ 0 0% 0 refresh-ims
refresh_pattern \/InRelease$ 0 0% 0 refresh-ims
refresh_pattern \/(Translation-.*)(|\.bz2|\.gz|\.xz)$ 0 0% 0 refresh-ims
refresh_pattern .		0	20%	4320
EOF
					destination = "local/squid.conf"
			}
			
			template {
				data = <<EOF
#
# Squid configuration settings for Debian
#

# Logs are managed by logrotate on Debian
logfile_rotate 0

# For extra security Debian packages only allow
# localhost to use the proxy on new installs
#
#http_access allow localnet
EOF
				destination = "local/debian.conf"
			}
			
			template {
				data = <<EOF
# Log cleanup
acl hasRequest has request
access_log none !hasRequest

debug_options ALL,2

# Configure SSL Bump options (for Squid 5.x)
#tls_outgoing_options cert=/etc/squid/mtls.pem key=/etc/squid/mtls.key flags=DONT_VERIFY_PEER,DONT_VERIFY_DOMAIN

# Define ACLs for SSL Bump
acl psc_mtls dstdomain auth.bas.psc.esante.gouv.fr
acl test_squid dst 195.154.197.206

# Use SSL Bump for matching ACLs
ssl_bump bump psc_mtls
ssl_bump bump test_squid
ssl_bump splice all

cache_peer 195.154.197.206 parent 443 0 tls sslcert=/etc/squid/mtls.pem sslkey=/etc/squid/mtls.key sslflags=DONT_VERIFY_PEER,DONT_VERIFY_DOMAIN proxy-only

http_access allow psc_mtls
http_access allow test_squid
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
				  target = "/etc/squid/squid.conf"
				  source = "local/squid.conf"
				  readonly = false
				  bind_options {
					propagation = "rshared"
				  }
				}
				
				mount {
				  type = "bind"
				  target = "/etc/squid/conf.d/debian.conf"
				  source = "local/debian.conf"
				  readonly = false
				  bind_options {
					propagation = "rshared"
				  }
				}
				
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
