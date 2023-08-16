job "gravitee-apim-management-api" {

    namespace = "gravitee"  

    type = "service"

    datacenters = ["${datacenter}"]

    update {
        stagger = "30s"
        max_parallel = 1
    }

    vault {
        policies = ["gravitee"]
        change_mode = "restart"
    }
	
    group "apim-management-api" {
        count = 1
        network {
			mode = "host"
            port "apim-manager-api" { to = 8083 }
        }
		
        task "apim-management-api" {

	    artifact {
	    	source	= "https://github.com/prosanteconnect/proof-of-concept/releases/download/v1.0.0/api-proxy-1.0.1.zip"
		    options {
			  archive = false
	    	}
	      }	    
	    
        driver = "docker"

        config {
           image = "prosanteconnect/apim-management-api:${tag}"
           ports = ["apim-manager-api"]
           mount {
		          type = "bind"
			      target = "/opt/graviteeio-management-api/plugins/api-proxy-1.0.1.zip"
			      source = "local/api-proxy-1.0.1.zip"
			      readonly = true
			      bind_options {
				    propagation = "rshared"
				  }
		    	}	

		 }          
					
            resources {
                cpu = 200
                memory = 2048
            }

            template {
                data = <<EOD
gravitee.management.mongodb.host = {{ range service "gravitee-mongodb" }}{{.Address}}{{end}}
gravitee.management.mongodb.port = {{ range service "gravitee-mongodb" }}{{.Port}}{{end}}
gravitee.management.mongodb.authSource  = admin
gravitee.management.mongodb.username = {{ with secret "gravitee/mongodb" }}{{.Data.data.root_user}}{{end}}
gravitee.management.mongodb.password = {{ with secret "gravitee/mongodb" }}{{.Data.data.root_pass}}{{end}}
gravitee_analytics_elasticsearch_endpoints_0=http://{{ range service "gravitee-elasticsearch" }}{{.Address}}:{{.Port}}{{end}}

gravitee.analytics.elasticsearch.security.username={{ with secret "gravitee/elasticsearch" }}{{.Data.data.root_user}}{{end}}
gravitee.analytics.elasticsearch.security.password={{ with secret "gravitee/elasticsearch" }}{{.Data.data.root_pass}}{{end}}
# Default admin override
gravitee_security_providers_0_users_1_username={{ with secret "gravitee/apim" }}{{.Data.data.admin_username}}{{end}}
gravitee_security_providers_0_users_1_password={{ with secret "gravitee/apim" }}{{.Data.data.admin_password}}{{end}}
# Other default users disabling
gravitee_security_providers_0_users_0_password=
gravitee_security_providers_0_users_2_password=
gravitee_security_providers_0_users_3_password=
gravitee_email_enabled=false
#groovy whitelist append
gravitee_groovy_whitelist_mode = append
gravitee_groovy_whitelist_liste = class sun.security.ssl.SSLSessionImpl
# jwt secret override
gravitee_jwt_secret={{ with secret "gravitee/apim" }}{{.Data.data.jwt_secret}}{{end}}
# api properties encryption secret override
gravitee_api_properties_encryption_secret={{ with secret "gravitee/apim" }}{{.Data.data.encryption_secret}}{{end}}
_JAVA_OPTIONS="${user_java_opts}"
# Disabling newsletter from apim management api to avoid this 10s request that times out 
# at first connection
gravitee_newsletter_enabled=false
# Gateway related management parameters
gravitee.gateway.unknown.expire.after=1
# Fermeture de l'API interne APIM qui n'est pas utilisée.
gravitee_service_core_http_enabled=false
EOD
                destination = "secrets/.env"
                env = true
            }

            service {
                name = "$\u007BNOMAD_JOB_NAME\u007D"
                tags = ["urlprefix-${apim_api_fqdn}/"]
                port = "apim-manager-api"
                meta {
                    fqdn = "${apim_api_fqdn}"
                }
                check {
                    name        = "alive"
                    type        = "http"
                    interval    = "10s"
                    timeout     = "5s"
                    port 	= "apim-manager-api"
                    path        = "management/organizations/DEFAULT/console" 
                }
            }
        }
    }
}
