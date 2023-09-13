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
   
   OIDCSSLValidateServer On
   OIDCCABundlePath /secrets/ssl/ca-certificates.crt

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
    STSExchange otx https://auth.server.psc.pocs.esante.gouv.fr:19587/realms/{{ .Data.data.keycloak_realm }}/protocol/openid-connect/token auth=client_cert&cert=/secrets/client.pocs.henix.asipsante.fr.pem&key=/secrets/client.pocs.henix.asipsante.fr.key&ssl_verify=true&params=subject_issuer%3D{{ .Data.data.keycloak_otx_subjet_issuer }}%26client_id%3D{{ .Data.data.keycloak_otx_client_id }}{{ end }}%26scope%3Dopenid%26audience%3D{{ with secret "editeur/apache2/dam" }}{{ .Data.data.keycloak_otx_audience }}{{ end }}

    STSAcceptSourceTokenIn environment name=OIDC_access_token
    STSPassTargetTokenIn header
    ProxyPassMatch  http://{{ range service "demo-client-dam" }}{{ .Address }}:{{ .Port }}{{ end }}
    ProxyPassReverse  http://{{ range service "demo-client-dam" }}{{ .Address }}:{{ .Port }}{{ end }}
    ErrorDocument 401 /dam/401.html
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
   OIDCSSLValidateServer On
   OIDCCABundlePath /local/ssl/client.cert.chain.pem

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
      
	  ##### certificat client pour PSC et Keycloak - Chaine de confiance #####
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
	  
	        template {
        data = <<EOH
{{ with secret "editeur/apache2/common" }}{{ .Data.data.client_cert_chain_accepted }}{{ end }}
EOH
        destination = "secrets/ssl/ca-certificates.crt"
        change_mode = "restart"
        env = false
      }
	  
	  	        template {
        data = <<EOHclient.cer
-----BEGIN CERTIFICATE-----
MIIHPDCCBSSgAwIBAgISESBlOmv0rqXMPTj6BWIj9azxMA0GCSqGSIb3DQEBCwUA
MHkxCzAJBgNVBAYTAkZSMRMwEQYDVQQKDApBU0lQLVNBTlRFMRcwFQYDVQQLDA4w
MDAyIDE4NzUxMjc1MTESMBAGA1UECwwJSUdDLVNBTlRFMSgwJgYDVQQDDB9BQyBS
QUNJTkUgSUdDLVNBTlRFIEVMRU1FTlRBSVJFMB4XDTEzMDYyNTAwMDAwMFoXDTMz
MDYyNDAwMDAwMFowgYAxCzAJBgNVBAYTAkZSMRMwEQYDVQQKDApBU0lQLVNBTlRF
MRcwFQYDVQQLDA4wMDAyIDE4NzUxMjc1MTESMBAGA1UECwwJSUdDLVNBTlRFMS8w
LQYDVQQDDCZBQyBJR0MtU0FOVEUgRUxFTUVOVEFJUkUgT1JHQU5JU0FUSU9OUzCC
AiIwDQYJKoZIhvcNAQEBBQADggIPADCCAgoCggIBAKuma07Nw0krbXtveIwQLDPG
M7KnaEJRW4rsxqXRc45gTCjZCfyxMo7mb88jtE6OrBORkMOhIWVxiR7Xplf6gqMh
LL+OCb1R0wjBcOEwyrEB2rVTKebUvKFsEEOZsLttzIMYYNV+2aPgEj9s+TiOkS+L
SgYgz/Pj6z/i1gi3skGD7wEUq9/j4h54xmTjt1gO8fenejsCUH5ZJjrzQa/TtYKG
IV87y9AdQZOofEz4Y8AwNU0oMpmmW4pucAu1A/jmgZ7ZAUr87+jBwJqSWzkkkWUh
3yR0VTqJigJafg0V+/O+kw7OaZWApK9cgyKG7n4VVs3c2JLHgBA4L2r4HJyAnGn9
Wi1d+0PTXOJuO7Lc7QsR3XjHLj90YkKs9p08F5/GFQlYqpoQaI2gj6AycWsBgJWv
2FLiLUnUaiZkiX/wgssVDbBfuOktDEf+hcVgTjmNEXxAhLARpF2XxVRrJTWwFNvg
9i+qxAboPt2c7BzdhuyoOWhm3JtmTa87Xjo8DpnUV3yAGSguO1g62dMWcAJEEswL
37XzWo0K/lwjeGTYrbgKR8UnyYVg7RD2rHdkWpofxhIA8fil3kozlgSOeqBWuzwd
Dk9rKesGPFYPVY1W1dvvtGt/50ggSzfV/e2wlMSFXU0uY1QOP6H5GNMtvk8pnnwr
6JCwmMjrqlBU6uZ7JJXNAgMBAAGjggG0MIIBsDASBgNVHRMBAf8ECDAGAQH/AgEA
MA4GA1UdDwEB/wQEAwIBBjBDBgNVHSAEPDA6MDgGBFUdIAAwMDAuBggrBgEFBQcC
ARYiaHR0cDovL2lnYy1zYW50ZS5lc2FudGUuZ291di5mci9QQzCCAQMGA1UdHwSB
+zCB+DA0oDKgMIYuaHR0cDovL2lnYy1zYW50ZS5lc2FudGUuZ291di5mci9DUkwv
QUNSLUVMLmNybDCBv6CBvKCBuYaBtmxkYXA6Ly9hbm51YWlyZS1pZ2MuZXNhbnRl
LmdvdXYuZnIvQ049QUMlMjBSQUNJTkUlMjBJR0MtU0FOVEUlMjBFTEVNRU5UQUlS
RSxPVT1JR0MtU0FOVEUsT1U9MDAwMiUyMDE4NzUxMjc1MSxPPUFTSVAtU0FOVEUs
Qz1GUj9jZXJ0aWZpY2F0ZXJldm9jYXRpb25saXN0O2JpbmFyeT9iYXNlP29iamVj
dGNsYXNzPXBraUNhMB0GA1UdDgQWBBTX+/rc1QJ/58FThk1L6KRKHgu7YzAfBgNV
HSMEGDAWgBSMb+rVi4L6+b6H3HMOJxUHR8SeLzANBgkqhkiG9w0BAQsFAAOCAgEA
IDLRXaIA/qcy9LlQBDc2lqul0V9Zga2XWmC0R71JcWUKZn++sk42UJD18TxctJxQ
t+03HOeBhpH6GdWUtOPW+kVDt+C9dWjFq0E4W/B0QD35iUNIu3nCE3Ke6NNitJ5+
q5a4wODaH9w2bA/8TO3kbxx98W9BCX+oXI7WN8/LpHxce6JdAd18PKZST9glk/ms
o0jtWOKw/jLL2S6AnZfYIJ+ZhpAJ5AxdkexWhTTnOekLwTcr3EVSQ9J7e7mzJ6iD
4AvtEdTCM6sJcL7QBvmIC4K/V9a7E47LoXsIDAWQgfWlt3SiOQG/19djzZod7z9B
ya37bji1VKzdOMYH1M1HlXW8Z4S0DwqjqQsSYeKejUwgLv+laKpsm5wvJOa6PLHn
LMzSD4KvyuOpiKXCS5pmAVdwOmVB+41C9lrp5uq2IV7hpOH6m/674+D4WXPB+ITd
2mqrRfwdzr5xjFfSOuMRZJwPJ+3LBtThCeDoZ/dyFwH6ichBi2+qPs38t8bw+j+W
0jzA9ulGsuSP5aTPwRKiodLXeVAPR9UtMGoUv3hXCWMqBGxxhb2e+k3qL+c5/S3F
a7pcoiK+h9hT0e5RPhWttgtVoOKJ0TWWK7JahaPRlI4IX/YxURsz3tsJnEi/i0gG
a0OcI0DJWSyIc6wGQYaIZCatZQZhTj+OsMrWP0vZJmI=
-----END CERTIFICATE-----
EOH
        destination = "secrets/aci.pem"
        change_mode = "restart"
        env = false
      }
	  
	  	        template {
        data = <<EOH
-----BEGIN CERTIFICATE-----
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
-----END CERTIFICATE-----
EOH
        destination = "secrets/acr.pem"
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
		
		# ACI 
		  mount {
          type = "bind"
          target = "/etc/ssl/certs/aci.pem"
          source = "secrets/aci.pem"
          readonly = true
          bind_options {
            propagation = "rshared"
          }
        }
		
		#ACR 
		  mount {
          type = "bind"
          target = "/etc/ssl/certs/acr.pem"
          source = "secrets/acr.pem"
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