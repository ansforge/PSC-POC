job "apache2" {
  namespace = "editeur"
  datacenters = ["${datacenter}"]
  type = "service"

  vault {
    policies = ["editeur"]
    change_mode = "restart"
  }

  group "apache2" {
    count = 1

    restart {
      attempts = 3
      delay = "60s"
      interval = "1h"
      mode = "fail"
    }

    update {
      max_parallel = 1
      min_healthy_time = "30s"
      progress_deadline = "5m"
      healthy_deadline = "2m"
    }

    network {
      port "https" {
        to = 443
      }
    }

    task "apache2" {
      driver = "docker"
	  
	  #######################################################
      # env variables
	  #######################################################
      template {
        data = <<EOH
PUBLIC_HOSTNAME={{ with secret "editeur/apache2/dam" }}{{ .Data.data.public_hostname }}{{ end }}
EOH
        destination = "local/file.env"
        change_mode = "restart"
        env = true
      }
	  #######################################################
	  # virtualhost damenligne.pocs.henix.asipsante.fr
	  #######################################################
	   template {
        data = <<EOH
{{ with secret "editeur/apache2/dam" }}
<VirtualHost *:443>
Define demo_client_dam_base_url {{ range service "demo-client-dam" }}{{ .Address }}{{ end }}
Define keycloak_base_url {{ range service "keycloak-server-https" }}{{ .Address }}{{ end }}
ErrorLogFormat "[%t] [%l] [pid %P] %F: %E: [client %a] %M"
SSLProtocol all
   DocumentRoot /var/www/html
   ServerName {{ .Data.data.server_name }}

   ErrorLog /var/log/apache2/ssl_error_log_{{ .Data.data.server_name }}.log
   LogLevel info

   SSLEngine on
   SSLCertificateFile /etc/ssl/certs/damenligne.pocs.henix.asipsante.fr.pem
   SSLCertificateKeyFile /etc/ssl/private/damenligne.pocs.henix.asipsante.fr.key
   OIDCHTTPTimeoutShort 10
   OIDCProviderAuthorizationEndpoint https://wallet.bas.psc.esante.gouv.fr/auth
   OIDCProviderMetadataURL https://auth.bas.psc.esante.gouv.fr/auth/realms/esante-wallet/.well-known/wallet-openid-configuration
   OIDCPKCEMethod S256
   OIDCOAuthAcceptTokenAs post
   OIDCCookieHTTPOnly On

   OIDCClientID {{ .Data.data.psc_client_id}}
   OIDCClientSecret {{ .Data.data.psc_client_secret}}
   OIDCAuthRequestParams acr_values=eidas1

   OIDCRedirectURI https://{{ .Data.data.server_name }}/demo/psc/redirect

   OIDCCryptoPassphrase 0123456789
   OIDCScope "openid scope_all"
   OIDCSSLValidateServer Off

   OIDCStateTimeout 120
   OIDCDefaultURL https://{{ .Data.data.server_name }}/demo/psc
   OIDCSessionInactivityTimeout 1200
   OIDCAuthNHeader X-Remote-User
   OIDCPassClaimsAs headers 
   
# mTLS avec PSC   
    OIDCClientTokenEndpointCert /etc/ssl/certs/client.pocs.henix.asipsante.fr.pem
    OIDCClientTokenEndpointKey /etc/ssl/private/client.pocs.henix.asipsante.fr.key
{{ end }}
  <Location /demo>
    AuthType openid-connect
    Require valid-user
    ProxyPassMatch ${demo_client_dam_base_url}
    ProxyPassReverse ${demo_client_dam_base_url}
   </Location>


   <Location /demo/exchange>
    AuthType openid-connect
    Require valid-user
{{ with secret "editeur/apache2/dam" }}
     STSExchange otx https://keycloak:8443/realms/{{ .Data.data.keycloak_realm }}/protocol/openid-connect/token auth=client_cert&cert=/etc/ssl/certs/client.pocs.henix.asipsante.fr.pem&key=/etc/ssl/certs/client.pocs.henix.asipsante.fr.pem&ssl_verify=false&params=subject_issuer%3D{{ .Data.data.keycloak_otx_subjet_issuer }}%26audience%3D{{ .Data.data.keycloak_otx_audience }}%26client_id%3D{{ .Data.data.keycloak_otx_client_id }}%26scope%3Dopenid
{{ end }}
	 STSAcceptSourceTokenIn environment name=OIDC_access_token
	 STSPassTargetTokenIn header
     ProxyPassMatch ${demo_client_dam_base_url}
     ProxyPassReverse ${demo_client_dam_base_url}

   </Location>
   
  
   # A partir de apache 2.2.24 ##########################
   SSLCompression off

   Header set X-Frame-Options SAMEORIGIN 

   SSLCipherSuite RSA:!EXP:!NULL:+HIGH:+MEDIUM:-LOW
</VirtualHost>
EOH
        destination = "local/damenligne.conf"
        change_mode = "restart"
        env = false
      }
	  
	  #######################################################
      # certificats server and client (psc, keycloak)
	  #######################################################
      template {
        data = <<EOH
{{ with secret "editeur/apache2/dam" }}{{ .Data.data.server_cert_pub_value }}{{ end }}
EOH
        destination = "secrets/damenligne.pocs.henix.asipsante.fr.pem"
        change_mode = "restart"
        env = false
      }
	  
	  template {
        data = <<EOH
{{ with secret "editeur/apache2/dam" }}{{ .Data.data.server_cert_key_value }}{{ end }}
EOH
        destination = "secrets/damenligne.pocs.henix.asipsante.fr.key"
        change_mode = "restart"
        env = false
      }
	  
	  template {
        data = <<EOH
{{ with secret "editeur/apache2/dam" }}{{ .Data.data.client_cert_key_value }}{{ end }}
EOH
        destination = "secrets/client.pocs.henix.asipsante.fr.pem"
        change_mode = "restart"
        env = false
      }
	  
	  
	  template {
        data = <<EOH
{{ with secret "editeur/apache2/dam" }}{{ .Data.data.client_cert_key_value }}{{ end }}
EOH
        destination = "secrets/client.pocs.henix.asipsante.fr.key"
        change_mode = "restart"
        env = false
      }
	  
	  #######################################################
	  # Conf, resources and service
	  #######################################################
      config {
        image = "${artifact.image}:${artifact.tag}"
        ports = ["https"]     
		# vhost dam
        mount {
          type = "bind"
          target = "/usr/local/apache2/conf/sites/damenligne.conf"
          source = "local/damenligne.conf"
          readonly = false
          bind_options {
            propagation = "rshared"
          }
        }  
        # cert pub server dam		
        mount {
          type = "bind"
          target = "/etc/ssl/certs/damenligne.pocs.henix.asipsante.fr.pem"
          source = "secrets/damenligne.pocs.henix.asipsante.fr.pem"
          readonly = true
          bind_options {
            propagation = "rshared"
          }
        } 
		# cert key server dam
	    mount {
          type = "bind"
          target = "/etc/ssl/private/damenligne.pocs.henix.asipsante.fr.key"
          source = "secrets/damenligne.pocs.henix.asipsante.fr.key"
          readonly = true
          bind_options {
            propagation = "rshared"
          }
        } 
		# cert pub client psc keycloak
         mount {
          type = "bind"
          target = "/etc/ssl/certs/client.pocs.henix.asipsante.fr.pem"
          source = "secrets/client.pocs.henix.asipsante.fr.pem"
          readonly = true
          bind_options {
            propagation = "rshared"
          }
        }   
		# cert key client psc keycloak
         mount {
          type = "bind"
          target = "/etc/ssl/private/client.pocs.henix.asipsante.fr.key"
          source = "secrets/client.pocs.henix.asipsante.fr.key"
          readonly = true
          bind_options {
            propagation = "rshared"
          }
        }           		
      }
	  
      resources {
        cpu = 200
        memory = 512
      }
	  
      service {
        name = "$\u007BNOMAD_JOB_NAME\u007D"
        port = "https"
        check {
          name         = "alive"
          type         = "tcp"
          interval     = "30s"
          timeout      = "5s"
          failures_before_critical = 5
          port         = "https"
        }
      }
    }
  }
}