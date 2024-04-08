project = "esignsante/client-db-esignsante-psc"

# Labels can be specified for organizational purposes.
labels = { "domaine" = "esignsante" }

runner {
  enabled = true
  profile = "psc-pocs"
  data_source "git" {
    url = "https://github.com/ansforge/PSC-POC.git"
    path = "esignsante/client-db-esignsante-psc"
    ignore_changes_outside_path = true
    ref = "main"
  }
  poll {
    enabled = false
  }
}

# An application to deploy.
app "esignsante/client-db-esignsante-psc" {

  build {
    use "docker-pull" {
      image = var.image
      tag   = var.tag
    }
    registry {
      use "docker" {
        image = "${var.registry_username}/clientmongo"
        tag = var.tag
        username = var.registry_username
        password = var.registry_password
	local = true
        }
    }
  }

  # Deploy to Nomad
  deploy {
    use "nomad-jobspec" {
      jobspec = templatefile("${path.app}/client-db-esignsante-psc.nomad.tpl", {
        datacenter = var.datacenter
        image = var.image
        tag = var.tag
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

variable "image" {
  type    = string
  default = "mongo-express"
}

variable "tag" {
  type    = string
  default = "1.0.0-alpha.4"
}