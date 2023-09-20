#
# (c) Copyright 2023, ANS. All rights reserved.
#

job "demo-client-dam" {
  namespace = "editeur"
  datacenters = ["${datacenter}"]
  type = "service"

  vault {
    policies = ["editeur"]
    change_mode = "restart"
  }

  group "demo-client-dam" {
    count = "1"
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
      port "http" {
        to = 8080
      }
    }

    task "demo-client-dam" {
      driver = "docker"
      config {
	    extra_hosts = ["gateway.psc.pocs.esante.gouv.fr:$${NOMAD_IP_http}"]
        image = "${artifact.image}:${artifact.tag}"
        ports = ["http"]
      }

      template {
        destination = "local/file.env"
        env = true
        data = <<EOH
        JAVA_TOOL_OPTIONS="-Xms1g -Xmx2g -XX:+UseG1GC -Dspring.config.location=/secrets/application.properties"
EOH
      }

      template {
        destination = "secrets/application.properties"
        change_mode = "restart"
        data = <<EOH
spring.application.name=demo-client-dam
server.servlet.context-path=/secure
server.use-forward-headers=true
server.max-http-header-size=20KB
server.forward-headers-strategy=NATIVE
server.tomcat.protocol-header=X-Forwarded-Proto
dam.api.key={{ with secret "editeur/demo-client-dam" }}{{ .Data.data.dam_api_key }}{{ end }}
dam.api.path={{ with secret "editeur/demo-client-dam" }}{{ .Data.data.dam_api_path }}{{ end }}
client.poc.keystore.location=/secrets/keystore.jks
client.poc.keystore.password={{ with secret "editeur/demo-client-dam" }}{{ .Data.data.client_poc_keystore_password }}{{ end }}
client.poc.truststore.location=/local/truststore.jks
EOH
      }

     template {
        destination = "secrets/keystore.jks"
        change_mode = "restart"
        data = <<EOH
{{ with secret "editeur/demo-client-dam" }}{{base64Decode .Data.data.client_poc_keystore_base64 }}{{ end }}
EOH
      }
	  
      template {
        destination = "local/truststore.jks"
        change_mode = "restart"
        data = <<EOH
{{ with secret "editeur/demo-client-dam" }}{{base64Decode .Data.data.client_poc_truststore_base64 }}{{ end }}
EOH
      }

      resources {
        cpu = 300
        memory = 2148
      }

      service {
        name = "$\u007BNOMAD_JOB_NAME\u007D"
        tags = ["urlprefix-/secure"]
        port = "http"
        check {
          type = "http"
          path = "secure/check"
          port = "http"
          interval = "30s"
          timeout = "2s"
          failures_before_critical = 3
        }
      }
    }
  }
}
