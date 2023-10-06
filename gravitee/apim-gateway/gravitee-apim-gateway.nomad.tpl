job "gravitee-apim-gateway" {

    namespace = "gravitee"

    type = "service"

    datacenters = ["${datacenter}"]
	
	affinity {
      attribute = "$\u007Bnode.class\u007D"
      value     = "standard"
    } 

    update {
        stagger = "30s"
        max_parallel = 1
    }

    vault {
        policies = ["gravitee"]
        change_mode = "restart"
    }
	
    group "apim-gateway" {
        count = 1
        network {
            mode = "host"
            port "gateway-port" { to = 8082 }
            port "core-port" { to = 18082 }	  
        }
		
        scaling {
            enabled = true
            min     = 1
            max     = 5

            policy {
                # On sélectionne l'instance la moins chargée de toutes les instances en cours,
                # on rajoute une instance (ou on en enlève une) si les seuils spécifiés
                # de charge de cpu sont franchis.
                cooldown = "180s"
                check "low_cpu" {
                    source = "prometheus"
                    query = "min(system_cpu_usage{_app='apim-gateway'})"
                    strategy "threshold" {
                        upper_bound = 0.4
                        delta = -1
                    }
                }

                check "high_cpu" {
                    source = "prometheus"
                    query = "min(system_cpu_usage{_app='apim-gateway'})"
                    strategy "threshold" {
                        lower_bound = 0.95
                        delta = 1
                    }
                }
            }
        }
		
        task "apim-gateway" {
		    
	    artifact {
	    	source	= "https://github.com/prosanteconnect/proof-of-concept/releases/download/v1.0.0/api-proxy-1.0.1.zip"
		    options {
			  archive = false
	    	}
	      }

	    artifact {
	    	source = "https://mos.esante.gouv.fr/NOS/JDV_J65-SubjectRole-DMP/JDV_J65-SubjectRole-DMP.xml"
			options {
		    	archive = false
		    }
	    }
			   
            driver = "docker"

            config {
                image = "${image}:${tag}"				
                ports = ["gateway-port", "core-port"]
                mount {
		          type = "bind"
			      target = "/opt/graviteeio-gateway/plugins/api-proxy-1.0.1.zip"
			      source = "local/api-proxy-1.0.1.zip"
			      readonly = true
			      bind_options {
				    propagation = "rshared"
				  }
		    	}	
                mount {
			      type = "bind"
			      target = "/opt/graviteeio-gateway/lib/ext/JDV_J65-SubjectRole-DMP.xml"
		          source = "local/JDV_J65-SubjectRole-DMP.xml"
		          readonly = true
			      bind_options {
				    propagation = "rshared"
				 }
			    }			
				mount {
					type = "bind"
					target = "/opt/graviteeio-gateway/config/ca_bundle.pem"
					source = "local/ca_bundle.pem"
					readonly = "false"
					bind_options {
						propagation = "rshared"
					}
				}
				mount {
					type = "bind"
					target = "/opt/graviteeio-gateway/config/gateway_key_cert.key"
					source = "secrets/gateway_key_cert.key"
					readonly = "false"
					bind_options {
						propagation = "rshared"
					}
				}
				mount {
					type = "bind"
					target = "/opt/graviteeio-gateway/config/gateway_pub_cert.pem"
					source = "secrets/gateway_pub_cert.pem"
					readonly = "false"
					bind_options {
						propagation = "rshared"
					}
				}				
             }

            resources {
                cpu = 1000
                memory = 2000
            }

            template {
                data = <<EOD
# mongodb
gravitee.ds.mongodb.host = {{ range service "gravitee-mongodb" }}{{.Address}}{{end}}
gravitee.ds.mongodb.port = {{ range service "gravitee-mongodb" }}{{.Port}}{{end}}
gravitee.management.mongodb.authSource = admin
gravitee.management.mongodb.username = {{ with secret "gravitee/mongodb" }}{{.Data.data.root_user}}{{end}}
gravitee.management.mongodb.password = {{ with secret "gravitee/mongodb" }}{{.Data.data.root_pass}}{{end}}
gravitee.ratelimit.mongodb.uri = mongodb://{{ with secret "gravitee/mongodb" }}{{.Data.data.root_user}}:{{.Data.data.root_pass}}{{end}}@{{ range service "gravitee-mongodb" }}{{.Address}}:{{.Port}}{{end}}/gravitee?authSource=admin
# elasticsearch
gravitee.ds.elastic.host = {{ range service "gravitee-elasticsearch" }}{{.Address}}{{end}}
gravitee.ds.elastic.port = {{ range service "gravitee-elasticsearch" }}{{.Port}}{{end}}
gravitee.reporters.elasticsearch.security.username={{ with secret "gravitee/elasticsearch" }}{{.Data.data.root_user}}{{end}}
gravitee.reporters.elasticsearch.security.password={{ with secret "gravitee/elasticsearch" }}{{.Data.data.root_pass}}{{end}}
# mTLS
gravitee.http.secured = true 
gravitee.http.ssl.clientAuth = request
gravitee.http.ssl.keystore.type = pem
gravitee_http_ssl_keystore_certificates_0_cert = /opt/graviteeio-gateway/config/gateway_pub_cert.pem
gravitee_http_ssl_keystore_certificates_0_key = /opt/graviteeio-gateway/config/gateway_key_cert.key
gravitee.http.ssl.truststore.type = pem
gravitee.http.ssl.truststore.path = /opt/graviteeio-gateway/config/ca_bundle.pem
#groovy whitelist append
gravitee_groovy_whitelist_mode = append
gravitee_groovy_whitelist_list_0 = class sun.security.ssl.SSLSessionImpl
gravitee_groovy_whitelist_list_1 = class javax.net.ssl.SSLSessionImpl
gravitee_groovy_whitelist_list_2 = class javax.net.ssl.SSLSession
gravitee_groovy_whitelist_list_3 = class sun.security.x509.X509CertImpl
gravitee_groovy_whitelist_list_4 = class java.security.cert.Certificate
gravitee_groovy_whitelist_list_5 = class java.security.cert.X509Certificate
gravitee_groovy_whitelist_list_6 = class java.security.MessageDigest
gravitee_groovy_whitelist_list_7 = class javax.xml.bind.DatatypeConverter
# api properties encryption secret override
gravitee_api_properties_encryption_secret={{ with secret "gravitee/apim" }}{{.Data.data.encryption_secret}}{{end}}
# prometheus
gravitee_services_core_http_enabled=true
gravitee_services_core_http_host=0.0.0.0
gravitee_services_core_http_authentication_users_supervision={{ with secret "gravitee/prometheus" }}{{.Data.data.auth_password}}{{end}}
gravitee_services_core_http_authentication_users_admin=
gravitee_services_metrics_enabled=true
_JAVA_OPTIONS="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=0.0.0.0:5005"
# Le heartbeat est en doublon avec Nomad et se marie mal avec l'allocation dynamique
gravitee_services_heartbeat_enabled=false
EOD
                destination = "secrets/.env"
                env = true
            }
			
			template {
				data = <<EOF
{{ with secret "gravitee/ssl" }}{{.Data.data.gateway_cert}}{{end}}
EOF
				destination = "secrets/gateway_pub_cert.pem"
			}
			
			template {
				data = <<EOF
{{ with secret "gravitee/ssl" }}{{.Data.data.gateway_key}}{{end}}
EOF
				destination = "secrets/gateway_key_cert.key"
			}

			template {
				data = <<EOF
{{ with secret "gravitee/ssl" }}{{.Data.data.ca_bundle}}{{end}}
EOF
				destination = "local/ca_bundle.pem"
			}

	    	   
            service {
                name = "$\u007BNOMAD_JOB_NAME\u007D"
				tags = ["urlprefix-${apim_gateway_fqdn} proto=tcp"]
                port = "gateway-port"
                check {
                    name         = "alive"
                    type         = "tcp"
                    interval     = "10s"
                    timeout      = "5s"
                    port         = "gateway-port"
                }
            }
			
			service {
				name = "metrics-exporter-auth"
				port = "core-port"
				tags = ["_endpoint=/_node/metrics/prometheus", "_app=apim-gateway"]
			}
        }
    }
}
