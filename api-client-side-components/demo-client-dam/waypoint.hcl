project = "editeur/demo-client-dam"

labels = {
  "domaine" = "editeur"
}

runner {
  enabled = true
  profile = "psc-pocs"
  data_source "git" {
    url = "https://github.com/prosanteconnect/proof-of-concept.git"
	path = "api-client-side-components/demo-client-dam"
	ignore_changes_outside_path = true
    ref = "main"
  }
  poll {
    enabled = false
    interval = "24h"
  }
}

app "editeur/demo-client-dam" {
  build {
    use "docker" {
      dockerfile = "${path.app}/Dockerfile"
    }
    registry {
      use "docker" {
        image = "${var.registry}/demo-client-dam"
        tag = gitrefpretty()
 	    username = var.registry_username
        password = var.registry_password
        local    = false      
      }
    }
  }

  deploy {
    use "nomad-jobspec" {
      jobspec = templatefile("${path.app}/demo-client-dam.tpl", {
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

