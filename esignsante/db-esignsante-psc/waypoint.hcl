project = "esignsante/db-esignsante-psc"

labels = { "domaine" = "esignsante" }

runner {
    enabled = true
    profile = "psc-pocs"
    data_source "git" {
        url = "https://github.com/ansforge/PSC-POC.git"
        ref = "main"
        path = "esignsante/db-esignsante-psc/"
	    ignore_changes_outside_path = true
    }
    poll {
        enabled = false
		interval = "24h"
    }
}

# An application to deploy.
app "esignsante/db-esignsante-psc" {
    build {
        use "docker-pull" {
           image = "mongo"
		   tag = var.tag
        }
        registry {
          use "docker" {
            image = "${var.registry}/mongodb"
            tag   = var.tag
		    username = var.registry_username
            password = var.registry_password
            local    = true
	      }
        }    
    }

	deploy {
		use "nomad-jobspec" {
			jobspec = templatefile("${path.app}/db-esignsante-psc.nomad.tpl", {
				datacenter = var.datacenter			
                tag = var.tag				
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

variable "tag" {
  type    = string
  default = "6.0"
}

