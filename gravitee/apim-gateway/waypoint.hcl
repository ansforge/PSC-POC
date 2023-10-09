project = "gravitee/apim-gateway"

# Labels can be specified for organizational purposes.
labels = { "domaine" = "gravitee" }

runner {
    enabled = true   
    profile = "gravitee"
    data_source "git" {
        url  = "https://github.com/prosanteconnect/proof-of-concept.git"
        ref  = "main"
        path = "gravitee/apim-gateway/"
        ignore_changes_outside_path = true
    }
    poll {
        enabled = false
        interval = "24h"
    }
}
# An application to deploy.
app "gravitee/apim-gateway" {

    build {
        use "docker-pull" {
            image = var.image
            tag = var.tag
            disable_entrypoint = true
        }
		
       registry {
         use "docker" {
           image    = "${var.registry}/apim-gateway"
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
            jobspec = templatefile("${path.app}/gravitee-apim-gateway.nomad.tpl", {
                datacenter = var.datacenter
                user_java_opts = var.user_java_opts
                apim_gateway_fqdn = var.apim_gateway_fqdn
                image = "${var.registry}/apim-gateway"
                tag = var.tag
            })
        }
    }
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

variable "image" {
  type    = string
  default = "graviteeio/apim-gateway"
}

variable "tag" {
    type = string
    default = "3.18.18"
}

variable "datacenter" {
    type = string
    default = "pocs-ans-psc"
}

variable "user_java_opts" {
    type = string
    default = ""
}

variable "apim_gateway_fqdn" {
    type = string
    default = "gateway.psc.pocs.esante.gouv.fr"
}
