project = "keycloak"

runner {
  enabled = true
  profile = "psc-pocs"
  data_source "git" {
    url = "https://github.com/prosanteconnect/proof-of-concept.git"
    ref = "main"
	path = "keycloak/"
	ignore_changes_outside_path = true
  }
  poll {
    enabled = false
  }
}

app "keycloak-db" {
  build {
    use "docker-ref" {
      image = var.db_image
      tag   = var.db_tag
    }
  }

  deploy {
    use "nomad-jobspec" {
      jobspec = templatefile("${path.app}/keycloak-db/keycloak-db.nomad.tpl", {
        datacenter      = var.datacenter
        image           = var.db_image
        tag             = var.db_tag
      })
    }
  }
}

app "keycloak-server" {
  build {
    use "docker-ref" {
      image = "bitnami/keycloak"
      tag   = var.server_tag
    }
  }

  deploy {
    use "nomad-jobspec" {
      jobspec = templatefile("${path.app}/keycloak-server/keycloak-server.nomad.tpl", {
        datacenter 		= var.datacenter
        image           = artifact.image
        tag             = artifact.tag
      })
    }
  }
}

variable "datacenter" {
  type    = string
  default = "pocs-ans-psc"
  env     = ["NOMAD_DATACENTER"]
}

variable "registry_username" {
  type      = string
  default   = ""
  env       = ["REGISTRY_USERNAME"]
  sensitive = true
}

variable "registry_password" {
  type      = string
  default   = ""
  env       = ["REGISTRY_PASSWORD"]
  sensitive = true
}

variable "db_image" {
  type    = string
  default = "postgres"
}

variable "db_tag" {
  type    = string
  default = "15.3"
}

variable "server_tag" {
  type    = string
  default = "21.1.2"
}
