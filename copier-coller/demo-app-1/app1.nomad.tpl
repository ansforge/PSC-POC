#
# (c) Copyright 2023, ANS. All rights reserved.
#

job "copier-coller-demo-app-1" {
  datacenters = ["${datacenter}"]
  type = "service"
   namespace = "copier-coller"

  vault {
    policies = ["copier-coller"]
    change_mode = "restart"
  }

  affinity {
    attribute = "$\u007Bnode.class\u007D"
    value = "standard"
  }

  group "demo-app-1" {
    count = "1"
    restart {
      attempts = 3
      delay = "60s"
      interval = "1h"
      mode = "fail"
    }

    network {
      port "http" {
        to = 8080
      }
    }

    task "demo-app-1" {
      driver = "docker"

      config {
	    extra_hosts = ["gateway.psc.pocs.esante.gouv.fr:$${NOMAD_IP_http}"]
        image = "${artifact.image}:${artifact.tag}"
        ports = ["http"]
      }

      # env variables
      template {
        destination = "local/file.env"
        env = true
        data = <<EOH
PUBLIC_HOSTNAME={{ with secret "copier-coller/app" }}{{ .Data.data.demo_app_1_public_hostname }}{{ end }}
JAVA_TOOL_OPTIONS="-Xms256m -Xmx1g -XX:+UseG1GC -Dspring.config.location=/secrets/application.properties -Dlogging.level.fr.ans.psc=${log_level}"
EOH
      }

      # application.properties
      template {
        destination = "secrets/application.properties"
        change_mode = "restart"
        data = <<EOF
spring.application.name=app1-copier-coller
server.servlet.context-path=/secure
psc.context.sharing.api.url=https://gateway.psc.pocs.esante.gouv.fr:19587/copier-coller/cc-api/cache
server.use-forward-headers=true
server.forward-headers-strategy=NATIVE
server.tomcat.protocol-header=X-Forwarded-Proto
server.max-http-header-size=20KB
client.poc.keystore.location=/secrets/keystore.jks
client.poc.keystore.password={{ with secret "copier-coller/app" }}{{ .Data.data.client_poc_keystore_password }}{{ end }}
client.poc.truststore.location=/local/truststore.jks
EOF
      }

     template {
        destination = "secrets/keystore.jks"
        change_mode = "restart"
        data = <<EOH
{{ with secret "copier-coller/app" }}{{base64Decode .Data.data.client_poc_keystore_base64 }}{{ end }}
EOH
      }
	  
      template {
        destination = "local/truststore.jks"
        change_mode = "restart"
        data = <<EOH
{{ with secret "copier-coller/app" }}{{base64Decode .Data.data.client_poc_truststore_base64 }}{{ end }}
EOH
      }

      resources {
        cpu = 500
        memory = 1152
      }

      service {
        name = "$\u007BNOMAD_JOB_NAME\u007D"
        tags = ["urlprefix-/app1-copier-coller"]
        port = "http"		
        check {
          type = "http"
          path = "secure/check"
          port = "http"
          interval = "30s"
          timeout = "2s"
          failures_before_critical = "3"
        }
      }
      }
  }
}
