project = "esignsante/esignsante-psc"

labels = { "domaine" = "esignsante" }

runner {
    enabled = true
    profile = "psc-pocs"
    data_source "git" {
        url = "https://github.com/ansforge/PSC-POC.git"
        ref = "main"
        path = "esignsante/front-esignsante-root/"
	    ignore_changes_outside_path = true
    }
    poll {
        enabled = false
		interval = "24h"
    }
}

# An application to deploy.
app "esignsante/esignsante-psc" {
    build {
        use "docker" {
           dockerfile = "${path.app}/Dockerfile"
        }
        registry {
           use "docker" {
             image = "${var.registry}/esignsante-psc"
             tag   = gitrefpretty()
             username = var.registry_username
             password = var.registry_password
             local    = true
           }
        }
    }

	deploy {
		use "nomad-jobspec" {
			jobspec = templatefile("${path.app}/esignsante-psc.nomad.tpl", {
				datacenter = var.datacenter			
				user_java_opts = var.user_java_opts
				swagger_ui = var.swagger_ui
				spring_http_multipart_max_file_size = "200MB"
				spring_http_multipart_max_request_size = "200MB"
				esignsantews_service_isexternal = ""				
				esignsantefsepsc_appserver_mem_size = 2048
			})
		}
	}
}

variable datacenter {
  default = "pocs-ans-psc"
  env = ["NOMAD_DATACENTER"]
}

variable "registry" {
  type    = string
  default = ""
  env     = ["REGISTRY"]
}

variable "registry_username" {
  type    = string
  default = ""
  env     = ["REGISTRY_USERNAME"]
  sensitive = true
}

variable "registry_password" {
  type    = string
  default = ""
  env     = ["REGISTRY_PASSWORD"]
  sensitive = true
}

variable "user_java_opts" {
  type = string
  default = ""
}

variable "swagger_ui" {
  type = string
  default = ""
}






