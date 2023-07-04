project = "gravitee/mongodb"

labels = { "domaine" = "gravitee" }

runner {
    enabled = true
	profile = "gravitee"
    data_source "git" {
        url  = "https://github.com/prosanteconnect/proof-of-concept.git"
        ref  = "main"
        path = "gravitee/mongodb"
        ignore_changes_outside_path = true
    }
    poll {
        enabled = false
        interval = "24h"
    }
}

app "gravitee/mongodb" {

    build {
        use "docker-pull" {
            image = var.image
            tag   = var.tag
            disable_entrypoint = true
        }
    registry {
      use "docker" {
        image    = "${var.registry}/${var.image}"
        tag      = var.tag
        username = var.registry_username
        password = var.registry_password
        local    = true
      }
     }
    }

    deploy {
        use "nomad-jobspec" {
            jobspec = templatefile("${path.app}/gravitee-mongodb.nomad.tpl", {
                datacenter = var.datacenter
				image = var.image
				tag   = var.tag
            })
		}
	}
}

variable "datacenter" {
  type  = string
  default = "pocs-ans-psc"
  env = ["NOMAD_DATACENTER"]
}

variable "image" {
  type    = string
  default = "mongo"
}

variable "tag" {
  type    = string
  default = "6.0"
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
