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
    use "docker-pull" {
      image = "postgres"
      tag   = var.db_tag
    }
    registry {
      use "docker" {
        image    = "postgres"
        tag      = var.db_tag
        username = var.registry_username
        password = var.registry_password
        local    = true
      }
    }
  }

  deploy {
    use "nomad-jobspec" {
      jobspec = templatefile("${path.app}/keycloak-db/keycloak-db.nomad.tpl", {
        datacenter      = var.datacenter
        image           = "postgres"
        tag             = var.db_tag
      })
    }
  }
}

app "keycloak-server" {
  build {
    use "docker-pull" {
      image = "bitnami/keycloak"
      tag   = var.server_tag
    }
    registry {
      use "docker" {
        image    = "bitnami/keycloak"
        tag      = var.server_tag
        username = var.registry_username
        password = var.registry_password
        local    = true
      }
    }
  }

  deploy {
    use "nomad-jobspec" {
      jobspec = templatefile("${path.app}/keycloak-server/keycloak-server.nomad.tpl", {
        datacenter = var.datacenter
        log_level = var.log_level
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

variable "log_level" {
  type    = string
  default = "INFO"
}

variable "db_tag" {
  type    = string
  default = "15.3"
}

variable "server_tag" {
  type    = string
  default = "21.1.2"
}
