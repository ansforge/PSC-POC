job "gravitee-apim-portal-ui" {

    namespace = "gravitee"

    type = "service"

    datacenters = ["${datacenter}"]

    update {
        stagger = "30s"
        max_parallel = 1
    }
	
	affinity {
      attribute = "$\u007Bnode.class\u007D"
      value     = "standard"
    } 
	
	group "gravitee-apim-portal-ui" {
		count = "1"
		restart {
			attempts = 3
			delay = "10s"
			interval = "1h"
			mode = "fail"
		}
		network {
			mode = "host"
			port "ui" { to = 8080 }
		}
		task "gravitee-portal-ui" {
			driver = "docker"
			config {
				image = "graviteeio/apim-portal-ui:${tag}"
				ports = ["ui"]			
			}
			resources {
				cpu = 500
				memory = 1000
			}
			service {
				name = "$\u007BNOMAD_JOB_NAME\u007D"
				tags = ["urlprefix-${portal_ui_fqdn}/"]
				port = "ui"
				check {
					name        = "alive"
					type        = "http"
					interval    = "10s"
					timeout     = "5s"
					port        = "ui"
					path        = "/"
				}
			}
			template{
				data = <<EOH
				PORTAL_API_URL=http://{{ range service "gravitee-apim-management-api" }}{{.ServiceMeta.fqdn}}{{end}}/portal/environments/DEFAULT
				PORTAL_URL=${portal_ui_fqdn}/
				EOH
				destination = "local/file.env"
				env = true
			}
		}
	}
}
