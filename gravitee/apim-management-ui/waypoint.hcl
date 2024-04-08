project = "gravitee/apim-management-ui"

# Labels can be specified for organizational purposes.
labels = { "domaine" = "gravitee" }

runner {
    enabled = true   
    profile = "gravitee"
    data_source "git" {
        url  = "https://github.com/ansforge/PSC-POC.git"
        ref  = "main"
        path = "gravitee/apim-management-ui"
        ignore_changes_outside_path = true
    }
    poll {
        enabled = false
        interval = "24h"
    }
}
# An application to deploy.
app "gravitee/apim-management-ui" {

    build {
        use "docker-pull" {
            image = "graviteeio/apim-management-ui"
            tag   = var.tag
            disable_entrypoint = true
        }
	    registry {
           use "docker" {
           image    = "${var.registry}/apim-management-ui"
           tag      = var.tag
           username = var.registry_username
           password = var.registry_password
           local    = true
        }
     }

    }

    # Deploy to Nomad
    deploy {
        use "nomad-jobspec" {
		
            jobspec = templatefile("${path.app}/gravitee-apim-management-ui.nomad.tpl", {
                datacenter = var.datacenter
                apim_ui_fqdn = var.apim_ui_fqdn
                user_java_opts = var.user_java_opts
                image = "graviteeio/apim-management-ui"
                tag = var.tag
            })
        }
    }
}

variable "datacenter" {
    type = string
    default = "pocs-ans-psc"
}

variable "apim_ui_fqdn" {
	type = string
	default = "pocs.apimgmtui.esante.gouv.fr"
}

variable "tag" {
  type    = string
  default = "3.18.18"
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

variable "registry" {
  type    = string
  default = ""
  env     = ["REGISTRY"]
}

variable "user_java_opts" {
	type = string
	default = ""
}
