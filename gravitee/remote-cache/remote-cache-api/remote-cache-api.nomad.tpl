job "remote-cache-api" {
  datacenters = ["${datacenter}"]
  type = "service"
   namespace = "remote-cache"

  vault {
    policies = ["remote-cache"]
    change_mode = "restart"
  }

  affinity {
    attribute = "$\u007Bnode.class\u007D"
    value = "standard"
  }
  

  group "remote-cache" {
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

	
    task "api" {
      driver = "docker"
      volume_mount {
          volume      = "json-schemas"
          destination = "/data"
          read_only   = false
      }	
      artifact {
        source = "https://github.com/ansforge/PSC-POC/raw/main/remote-cache/resources/json-schemas.zip"
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
#JAVA_TOOL_OPTIONS="-Xms256m -Xmx1g -XX:+UseG1GC -Dspring.config.location=/secrets/application.properties -Dlogging.level.fr.ans.psc=${log_level}"
JAVA_TOOL_OPTIONS="-Xms256m -Xmx1g -XX:+UseG1GC -Dspring.config.location=/secrets/application.properties -Dlogging.level.fr.ans.psc=DEBUG"
EOH
      }

      #application.properties
      template {
        destination = "secrets/application.properties"
        change_mode = "restart"
        data = <<EOF
spring.application.name=psc-remote-cache-api
server.servlet.contextPath=/remote-cache-api
logging.level.org.springframework=ERROR
logging.level.fr.ans.psc.remote.cache.api.*=DEBUG

spring.redis.database=0
spring.redis.host={{ range service "redis-remote-cache" }}{{ .Address }}{{ end }}
spring.redis.port={{ range service "redis-remote-cache" }}{{ .Port }}{{ end }}
{{ with secret "remote-cache/redis" }}
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
       # name = "$\u007BNOMAD_JOB_NAME\u007D"
	    name = "remote-cache-api"
        tags = ["urlprefix-/remote-cache-api/"]
        port = "http"
        check {
          type = "http"
          path = "/remote-cache-api/check"
          port = "http"
          interval = "30s"
          timeout = "2s"
          failures_before_critical = "3"
        }
      }
       
	  service {
                name = "metrics-exporter"
				port = "http"
                tags = ["_endpoint=/remote-cache-api/actuator/prometheus",
                                "_app=remote-cache-api",]
                        }
    }
  }
}
