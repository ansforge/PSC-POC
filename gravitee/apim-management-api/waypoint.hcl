project = "gravitee/apim-management-api"

# Labels can be specified for organizational purposes.
labels = { "domaine" = "gravitee" }

runner {
    enabled = true   
    profile = "gravitee"
    data_source "git" {
        url  = "https://github.com/prosanteconnect/proof-of-concept.git"
        ref  = "main"
        path = "gravitee/apim-management-api/"
        ignore_changes_outside_path = true
    }
    poll {
        enabled = false
        interval = "24h"
    }
}
# An application to deploy.
app "gravitee/apim-management-api" {

    build {
           use "docker-pull" {
      image = var.image
      tag = var.tag
      disable_entrypoint = true
    }	
    registry {
       use "docker" {
         image    = "prosanteconnect/apim-management-api"
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
          jobspec = templatefile("${path.app}/gravitee-apim-management-api.nomad.tpl", {
            datacenter = var.datacenter
		    apim_api_fqdn = var.apim_api_fqdn
		    user_java_opts = var.user_java_opts
		    image = var.image
		    tag = var.tag
          })        
        }
    }
}

variable "datacenter" {
    type = string
    default = "pocs-ans-psc"
}

variable "image" {
  type    = string
  default = "graviteeio/apim-management-api"
}

variable "tag" {
  type    = string
  default = "3.18.18"
}


variable "apim_api_fqdn" {
	type = string
	default = "pocs.apimgmt.esante.gouv.fr"
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
