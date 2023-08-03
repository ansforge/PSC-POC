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
        image = "${artifact.image}:${artifact.tag}"
        ports = ["http"]
      }

      template {
        destination = "local/file.env"
        env = true
        data = <<EOH
        JAVA_TOOL_OPTIONS="-Xms1g -Xmx2g -XX:+UseG1GC -Dspring.config.location=/local/application.properties"
EOH
      }

      template {
        destination = "local/application.properties"
        change_mode = "restart"
        data = <<EOH
server.servlet.context-path=/secure
server.use-forward-headers=true
server.forward-headers-strategy=NATIVE
server.tomcat.protocol-header=X-Forwarded-Proto
dam.api.key={{ with secret "editeur/demo-client-dam" }}{{ .Data.data.dam_api_key }}{{ end }}
dam.api.url=https://{{ with secret "editeur/demo-client-dam" }}{{ .Data.data.dam_api_url }}{{ end }}
EOH
      }

      resources {
        cpu = 300
        memory = 2148
      }

      service {
        name = "$\u007BNOMAD_JOB_NAME\u007D"
        tags = ["urlprefix-/demo-client-dam/v1"]
        port = "http"
        check {
          type = "http"
          path = "demo-client-dam/v1/check"
          port = "http"
          interval = "30s"
          timeout = "2s"
          failures_before_critical = 3
        }
      }
    }
  }
}
