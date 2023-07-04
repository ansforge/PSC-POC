job "copier-coller-api" {
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

  group "copier-coller" {
    count = "1"
    restart {
      attempts = 3
      delay = "60s"
      interval = "1h"
      mode = "fail"
    }

    volume "json-schemas" {
      type      = "host"
      read_only = false
      source    = "json-schemas"
    }

    network {
      port "http" {
        to = 8080
      }
    }

	
    task "api" {
      driver = "docker"
      volume_mount {
          volume      = "json-schemas"
          destination = "/data"
          read_only   = false
      }	
      artifact {
        source = "https://github.com/prosanteconnect/proof-of-concept/raw/main/copier-coller/resources/json-schemas.zip"
      }

      config {
        image = "${artifact.image}:${artifact.tag}"
        ports = ["http"]    
        mount {
          type = "bind"
          target = "/app/json-schemas-repo/"
          source = "local/json-schemas/"
          readonly = "false"
          bind_options {
            propagation = "rshared"
          }
        }		
	  }

      # env variablesmkdi
      template {
        destination = "local/file.env"
        env = true
        data = <<EOH
PUBLIC_HOSTNAME={{ with secret "copier-coller/api" }}{{ .Data.data.api_public_hostname }}{{ end }}
#JAVA_TOOL_OPTIONS="-Xms256m -Xmx1g -XX:+UseG1GC -Dspring.config.location=/secrets/application.properties -Dlogging.level.fr.ans.psc=${log_level}"
JAVA_TOOL_OPTIONS="-Xms256m -Xmx1g -XX:+UseG1GC -Dspring.config.location=/secrets/application.properties -Dlogging.level.fr.ans.psc=DEBUG"
EOH
      }

      #application.properties
      template {
        destination = "secrets/application.properties"
        change_mode = "restart"
        data = <<EOF
spring.application.name=psc-copier-coller-api
server.servlet.contextPath=/cc-api
logging.level.org.springframework=ERROR
logging.level.fr.ans.psc.copier.coller.api.*=DEBUG

spring.redis.database=0
spring.redis.host={{ range service "redis-copier-coller" }}{{ .Address }}{{ end }}
spring.redis.port={{ range service "redis-copier-coller" }}{{ .Port }}{{ end }}
{{ with secret "copier-coller/redis" }}
#spring.redis.username={{ .Data.data.redis_username }}
spring.redis.password={{ .Data.data.redis_password }}
{{ end }}
schemas.file.repository=/app/json-schemas-repo

management.endpoints.web.exposure.include=prometheus,metrics
EOF
      }

      resources {
        cpu = 500
        memory = 1152
      }

      service {
	    name = "copier-coller-api"
        tags = ["urlprefix-/cc-api/"]
        port = "http"
        check {
          type = "http"
          path = "/cc-api/check"
          port = "http"
          interval = "30s"
          timeout = "2s"
          failures_before_critical = "3"
        }
      }
	  
	  service {
                name = "metrics-exporter"
				port = "http"
                tags = ["_endpoint=/cc-api/actuator/prometheus",
                                "_app=copier-coller-api",]
                        }
    }
  }
}
