job "esignsante-psc" {
    namespace = "esignsante"	    
	datacenters = ["${datacenter}"]
	type = "service"
	vault {
		policies = ["esignsante"]
		change_mode = "restart"
	}
    affinity {
      attribute = "$\u007Bnode.class\u007D"
      value     = "standard"
    }  
	group "esignsante-psc" {
		count = "1"
		restart {
			attempts = 3
			delay = "60s"
			interval = "1h"
			mode = "fail"
		}
		
		network {
				port "http" { to = 8080 }
		}

		update {
			max_parallel      = 1
			canary            = 1
			min_healthy_time  = "30s"
			progress_deadline = "5m"
			healthy_deadline  = "2m"
			auto_revert       = true
			auto_promote      = false
		}
			
		task "esignsante_psc" {
			env {
				JAVA_TOOL_OPTIONS="${user_java_opts} -Dspring.config.location=/secrets/application.properties -Dspring.profiles.active=swagger"
			}
			driver = "docker"
			config {
				image = "${artifact.image}:${artifact.tag}"
				ports = [ "http" ]
			}
			template {
data = <<EOF
logging.level.org.springframework=ERROR
logging.level.fr.ans=info

spring.servlet.multipart.max-file-size=${spring_http_multipart_max_file_size}
spring.servlet.multipart.max-request-size=${spring_http_multipart_max_request_size}
spring.application.name=esignsante-psc

server.servlet.context-path=/esignsante-psc
server.max-http-header-size=20KB
{{with secret "esignsante-psc/esignsante-ws"}}
esignsante.webservices.signature.confId={{.Data.data.signature_confid}}
esignsante.webservices.signature.secret={{.Data.data.signature_secret}}
esignsante.webservices.proof.confId={{.Data.data.proof_confid}}
esignsante.webservices.checksignature={{.Data.data.checksignature}}
esignsante.webservices.appliantId={{.Data.data.appliantid}}
esignsante.webservices.proofTag={{.Data.data.prooftag}}

#Conf. pour fse (pkcs7)
esignsante.webservices.signature.fse.confId={{.Data.data.signature_confid_fse}}
esignsante.webservices.signature.fse.secret={{.Data.data.signature_secret_fse}}
esignsante.webservices.signature.fse.checksignature={{.Data.data.checksignature_fse}}
{{end}}

{{range service ("esignsante-webservices") }}
esignsante.webservices.basepath=http://{{.Address}}:{{.Port}}{{end}}/esignsante/v1


{{range service ( "db-esignsante-psc") }}
spring.data.mongodb.host={{.Address}}
spring.data.mongodb.port={{.Port}}{{end}}

{{with secret "esignsante-psc/esignsante-psc-db"}}
spring.data.mongodb.database=esignsante_psc
spring.data.mongodb.user={{.Data.data.root_user}}
spring.data.mongodb.password={{.Data.data.root_pass}}
{{end}}

management.endpoints.web.exposure.include=prometheus,metrics
EOF
			destination = "secrets/application.properties"
			}
			resources {
				cpu = 1000
				memory = ${esignsantefsepsc_appserver_mem_size}
			}
			service {
				name = "$\u007BNOMAD_JOB_NAME\u007D"
				meta {
				       gravitee_path = "/esignsante-psc"
				       gravitee_ssl = false
				}
				tags = ["urlprefix-/esignsante-psc/"]
				canary_tags = ["canary instance to promote"]
				port = "http"
				check {
					type = "http"
					port = "http"
					path = "/esignsante-psc/v1/ca"
					header {
						Accept = ["application/json"]
					}
					name = "alive"
					interval = "30s"
					timeout = "10s"
				}
			}
			service {
				name = "metrics-exporter"
				port = "http"
				tags = ["_endpoint=/esignsante-psc/actuator/prometheus",
								"_app=esignsante-psc",]
			}
		}
	}
}
