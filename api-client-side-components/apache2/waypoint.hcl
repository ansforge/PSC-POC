project = "editeur/apache2"

labels = { "domaine" = "editeur"}

runner {
  enabled = true
  profile = "psc-pocs"
  data_source "git" {
    url = "https://github.com/prosanteconnect/proof-of-concept.git"
    path = "api-client-side-components/apache2"
    ignore_changes_outside_path = true
    ref = "main"
  }
  poll {
    enabled = false
    interval = "24h"
  }
}

app "editeur/apache2" {
  build {
   use "docker" {
      dockerfile = "${path.app}/Dockerfile"
    }
    registry {
      use "docker" {
        image    = "${var.registry}/${var.image}"
        tag      = var.tag
        username = var.registry_username
        password = var.registry_password
        local    = false
      }
    }
  }

  deploy {
    use "nomad-jobspec" {
      jobspec = templatefile("${path.app}/apache2.tpl", {
        image = var.image
        tag = var.tag
        datacenter = var.datacenter
      })
    }
  }
}

variable "image" {
  type    = string
  default = "customised-httpd-2.4-bookworm-v1"
}

variable "tag" {
  type    = string
  default = "0.0.1"
}

variable "datacenter" {
  type = string
  default = "pocs-ans-psc"
  env = ["NOMAD_DATACENTER"]
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
