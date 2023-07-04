project = "psc-dam/rass-structure-api"

labels = {
  "domaine" = "psc-dam"
}

runner {
  enabled = true
  profile = "psc-pocs"
  data_source "git" {
    url = "https://github.com/prosanteconnect/proof-of-concept.git"
	path = "dam/psc-dam-rass-repository/"
    ignore_changes_outside_path = true
    ref = "main"
  }
  poll {
    enabled = false
    interval = "24h"
  }
}

app "psc-dam/rass-structure-api" {
  build {
    use "docker" {
      dockerfile = "${path.app}/Dockerfile"
    }
    registry {
      use "docker" {
        image = "${var.registry}/rass-structure-api"
        tag = gitrefpretty()
        username = var.registry_username
        password = var.registry_password
        local    = false
      }
    }
  }

  deploy {
    use "nomad-jobspec" {
      jobspec = templatefile("${path.app}/mongodb-rass-repository.nomad.tpl", {
        datacenter = var.datacenter      
      })
    }
  }
}

variable "datacenter" {
  type = string
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
