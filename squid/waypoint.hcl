project = "squid"

# Labels can be specified for organizational purposes.
labels = { "domaine" = "squid" }

runner {
    enabled = true   
    profile = "squid"
    data_source "git" {
        url  = "https://github.com/prosanteconnect/proof-of-concept.git"
        ref  = "main"
        path = "squid/"
        ignore_changes_outside_path = true
    }
    poll {
        enabled = false
        interval = "24h"
    }
}
# An application to deploy.
app "squid" {
    build {
        use "docker-pull" {
            image = var.image
            tag = var.tag
            disable_entrypoint = true
        }
		
       registry {
         use "docker" {
           image    = "${var.registry}/squid"
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
            jobspec = templatefile("${path.app}/squid.nomad.tpl", {
                datacenter = var.datacenter
                image = "${var.registry}/squid"
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
  default = "ubuntu/squid"
}

variable "tag" {
    type = string
    default = "5.2-22.04_beta"
}

variable "datacenter" {
    type = string
    default = "pocs-ans-psc"
}
