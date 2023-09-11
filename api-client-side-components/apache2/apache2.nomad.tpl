# (c) Copyright 2023, ANS. All rights reserved.
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
      
      #Pages Données Améli (accueil utilisateur non connecté, erreurs )
      artifact {
        source = "https://github.com/prosanteconnect/proof-of-concept/raw/main/api-client-side-components/demo-client-dam/PagesApache/dam.zip"
        destination = "local/tmp"
        mode = "any"
        }
      
      driver = "docker"      
      
      #######################################################
      # env variables
      #######################################################
      template {
        data = <<EOH
PUBLIC_HOSTNAME={{ with secret "editeur/apache2/dam" }}{{ .Data.data.public_dam_hostname }}{{ end }}
EOH
        destination = "local/file.env"
        change_mode = "restart"
        env = true
      }
	  
      #######################################################
      # virtualhost damenligne.psc.pocs.esante.gouv.fr
      #######################################################
      template {
        data = <<EOH
<VirtualHost *:443>
ErrorLogFormat "[%t] [%l] [pid %P] %F: %E: [client %a] %M"
SSLProtocol all
   DocumentRoot /usr/local/apache2/htdocs
   ServerName {{ with secret "editeur/apache2/dam" }}{{ .Data.data.public_dam_hostname }}{{ end }}
   ErrorLog /dev/stdout
   TransferLog /dev/stdout
   LogLevel info

RewriteEngine on
   RewriteRule "^$" "/dam/index.html" [L]
   RewriteRule "^/$" "/dam/index.html" [L]
 

   SSLEngine on
   SSLCertificateFile /secrets/damenligne.pem
   SSLCertificateKeyFile /secrets/damenligne.key
   OIDCHTTPTimeoutShort 10
   OIDCProviderAuthorizationEndpoint https://wallet.bas.psc.esante.gouv.fr/auth
   OIDCProviderMetadataURL https://auth.bas.psc.esante.gouv.fr/auth/realms/esante-wallet/.well-known/wallet-openid-configuration
   OIDCPKCEMethod S256
   OIDCOAuthAcceptTokenAs post
   OIDCCookieHTTPOnly On
{{ with secret "editeur/apache2/common" }}
   OIDCClientID {{ .Data.data.psc_client_id}}
   OIDCClientSecret {{ .Data.data.psc_client_secret}}
{{ end }}
   OIDCAuthRequestParams acr_values=eidas1
{{ with secret "editeur/apache2/dam" }}
   OIDCRedirectURI https://{{ .Data.data.public_dam_hostname }}/secure/psc/redirect

   OIDCCryptoPassphrase 0123456789
   OIDCScope "openid scope_all"
   OIDCSSLValidateServer Off

   OIDCStateTimeout 120
   OIDCDefaultURL https://{{ .Data.data.public_dam_hostname }}/secure/psc
{{ end }}
   OIDCSessionInactivityTimeout 1200
   OIDCAuthNHeader X-Remote-User
   OIDCPassClaimsAs both
   
# mTLS avec PSC   
   OIDCClientTokenEndpointCert /secrets/client.pocs.henix.asipsante.fr.pem
   OIDCClientTokenEndpointKey /secrets/client.pocs.henix.asipsante.fr.key


  <Location /secure>
    AuthType openid-connect
    Require valid-user
    ProxyPassMatch http://{{ range service "demo-client-dam" }}{{ .Address }}:{{ .Port }}{{ end }}
    ProxyPassReverse http://{{ range service "demo-client-dam" }}{{ .Address }}:{{ .Port }}{{ end }}
   </Location>


   <Location /secure/view>
    AuthType openid-connect  
    Require valid-user
{{ with secret "editeur/apache2/common" }}
    STSExchange otx https://auth.server.psc.pocs.esante.gouv.fr:19587/realms/{{ .Data.data.keycloak_realm }}/protocol/openid-connect/token auth=client_cert&cert=/secrets/client.pocs.henix.asipsante.fr.pem&key=/secrets/client.pocs.henix.asipsante.fr.key&ssl_verify=false&params=subject_issuer%3D{{ .Data.data.keycloak_otx_subjet_issuer }}%26client_id%3D{{ .Data.data.keycloak_otx_client_id }}{{ end }}%26scope%3Dopenid%26audience%3D{{ with secret "editeur/apache2/dam" }}{{ .Data.data.keycloak_otx_audience }}{{ end }}

    STSAcceptSourceTokenIn environment name=OIDC_access_token
    STSPassTargetTokenIn header
    ProxyPassMatch  http://{{ range service "demo-client-dam" }}{{ .Address }}:{{ .Port }}{{ end }}
    ProxyPassReverse  http://{{ range service "demo-client-dam" }}{{ .Address }}:{{ .Port }}{{ end }}
    ErrorDocument 401 /dam/401.html
   </Location>
   
   <Location "/logout">
    AuthType openid-connect  
    Require valid-user
#	Header unset Cookie
#    Header unset Set-Cookie
    ProxyPassMatch  http://{{ range service "demo-client-dam" }}{{ .Address }}:{{ .Port }}{{ end }}
    ProxyPassReverse  http://{{ range service "demo-client-dam" }}{{ .Address }}:{{ .Port }}{{ end }}	
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
      # virtualhost application-1.pocs.gateway.esante.gouv.fr
      #######################################################
      template {
        data = <<EOH
<VirtualHost *:443>
ErrorLogFormat "[%t] [%l] [pid %P] %F: %E: [client %a] %M"
SSLProtocol all
   DocumentRoot /usr/local/apache2/htdocs
   ServerName {{ with secret "editeur/apache2/copiercoller" }}{{ .Data.data.public_app1_hostname }}{{ end }}
   ErrorLog /dev/stdout
   TransferLog /dev/stdout
   LogLevel info

#RewriteEngine on
#   RewriteRule "^$" "/dam/index.html" [L]
#   RewriteRule "^/$" "/dam/index.html" [L]

   SSLEngine on
   SSLCertificateFile /secrets/application-1.pocs.gateway.esante.gouv.fr.pem
   SSLCertificateKeyFile /secrets/application-1.pocs.gateway.esante.gouv.fr.key
   OIDCHTTPTimeoutShort 10
   OIDCProviderAuthorizationEndpoint https://wallet.bas.psc.esante.gouv.fr/auth
   OIDCProviderMetadataURL https://auth.bas.psc.esante.gouv.fr/auth/realms/esante-wallet/.well-known/wallet-openid-configuration
   OIDCPKCEMethod S256
   OIDCOAuthAcceptTokenAs post
   OIDCCookieHTTPOnly On
{{ with secret "editeur/apache2/common" }}
   OIDCClientID {{ .Data.data.psc_client_id}}
   OIDCClientSecret {{ .Data.data.psc_client_secret}}
{{ end }}
   OIDCAuthRequestParams acr_values=eidas1
{{ with secret "editeur/apache2/copiercoller" }}
   OIDCRedirectURI https://{{ .Data.data.public_app1_hostname }}/secure/psc/redirect

   OIDCCryptoPassphrase 0123456789
   OIDCScope "openid scope_all"
   OIDCSSLValidateServer Off

   OIDCStateTimeout 120
   OIDCDefaultURL https://{{ .Data.data.public_app1_hostname }}/secure/psc
{{ end }}
   OIDCSessionInactivityTimeout 1200
   OIDCAuthNHeader X-Remote-User
   OIDCPassClaimsAs both
   
# mTLS avec PSC   
   OIDCClientTokenEndpointCert /secrets/client.pocs.henix.asipsante.fr.pem
   OIDCClientTokenEndpointKey /secrets/client.pocs.henix.asipsante.fr.key

   <Location /secure/*>
    AuthType openid-connect  
    Require valid-user
{{ with secret "editeur/apache2/common" }}
    STSExchange otx https://auth.server.psc.pocs.esante.gouv.fr:19587/realms/{{ .Data.data.keycloak_realm }}/protocol/openid-connect/token auth=client_cert&cert=/secrets/client.pocs.henix.asipsante.fr.pem&key=/secrets/client.pocs.henix.asipsante.fr.key&ssl_verify=false&params=subject_issuer%3D{{ .Data.data.keycloak_otx_subjet_issuer }}%26client_id%3D{{ .Data.data.keycloak_otx_client_id }}{{ end }}%26scope%3Dopenid%26audience%3D{{ with secret "editeur/apache2/copiercoller" }}{{ .Data.data.keycloak_otx_audience }}{{ end }}

    STSAcceptSourceTokenIn environment name=OIDC_access_token
    STSPassTargetTokenIn header
    ProxyPassMatch  http://{{ range service "copier-coller-demo-app-1" }}{{ .Address }}:{{ .Port }}{{ end }}
    ProxyPassReverse  http://{{ range service "copier-coller-demo-app-1" }}{{ .Address }}:{{ .Port }}{{ end }}
    ErrorDocument 401 /dam/401.html
   </Location>
   
# A partir de apache 2.2.24 ##########################
   SSLCompression off

   Header set X-Frame-Options SAMEORIGIN 

   SSLCipherSuite RSA:!EXP:!NULL:+HIGH:+MEDIUM:-LOW
</VirtualHost>
EOH
        destination = "local/app1-copiercoller.conf"
        change_mode = "restart"
        env = false
      }
      
      #######################################################
      # certificats server (Vhost)  and client (psc, keycloak)
      #######################################################
	  
	  #####   certificat serveur du Vhost damenligne     #####
	  template {
        data = <<EOH
{{ with secret "editeur/apache2/dam" }}{{ .Data.data.server_cert_pub_value }}{{ end }}
EOH
        destination = "secrets/damenligne.pem"
        change_mode = "restart"
        env = false
      }
      
      template {
        data = <<EOH
{{ with secret "editeur/apache2/dam" }}{{ .Data.data.server_cert_key_value }}{{ end }}
EOH
        destination = "secrets/damenligne.key"
        change_mode = "restart"
        env = false
      }
	  
	  ##### certificat serveur du Vhost app-1 de copier-coller #####
	  
	  template {
        data = <<EOH
{{ with secret "editeur/apache2/copiercoller" }}{{ .Data.data.server_app1_cert_pub_value }}{{ end }}
EOH
        destination = "secrets/application-1.pocs.gateway.esante.gouv.fr.pem"
        change_mode = "restart"
        env = false
      }
      
      template {
        data = <<EOH
{{ with secret "editeur/apache2/copiercoller" }}{{ .Data.data.server_app1_cert_key_value }}{{ end }}
EOH
        destination = "secrets/application-1.pocs.gateway.esante.gouv.fr.key"
        change_mode = "restart"
        env = false
      }
      
	  ##### certificat client pour PSC et Keycloak #####
      template {
        data = <<EOH
{{ with secret "editeur/apache2/common" }}{{ .Data.data.client_cert_pub_value }}{{ end }}
EOH
        destination = "secrets/client.pocs.henix.asipsante.fr.pem"
        change_mode = "restart"
        env = false
      }
      
      
      template {
        data = <<EOH
{{ with secret "editeur/apache2/common" }}{{ .Data.data.client_cert_key_value }}{{ end }}
EOH
        destination = "secrets/client.pocs.henix.asipsante.fr.key"
        change_mode = "restart"
        env = false
      }
      
      #######################################################
      # Conf, resources and service
      #######################################################
      config {
      extra_hosts = ["auth.server.psc.pocs.esante.gouv.fr:$${NOMAD_IP_https}"]
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
        #pages DAM hébergées par Apache
        mount {
          type = "bind"
          target = "/usr/local/apache2/htdocs/dam"
          source = "local/tmp"
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
        tags = ["urlprefix-damenligne.psc.pocs.esante.gouv.fr  proto=tcp"]
#		         "urlprefix-application-1.pocs.gateway.esante.gouv.fr proto=tcp"]
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