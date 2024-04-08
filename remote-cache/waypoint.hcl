project = "remote-cache"

runner {
  enabled = true
  profile = "psc-pocs"
  data_source "git" {
    url = "https://github.com/ansforge/PSC-POC.git"
    ref = "main"
    path = "remote-cache/"
	ignore_changes_outside_path = true
  }
  poll {
    enabled = false
  }
}

app "redis" {
  build {
    use "docker-pull" {
      image = "redis"
      tag   = var.tag
    }
    registry {
      use "docker" {
        image    = "prosanteconnect/redis"
        tag      = var.tag
        username = var.registry_username
        password = var.registry_password
        local    = true
      }
    }
  }

  deploy {
    use "nomad-jobspec" {
      jobspec = templatefile("${path.app}/redis-deployment/redis.nomad.tpl", {
        datacenter      = var.datacenter
        image           = "redis"
        tag             = var.tag
      })
    }
  }
}

app "api" {
  build {
    use "docker" {
      dockerfile = "${path.app}/remote-cache-api/Dockerfile"
    }

    registry {
      use "docker" {
        image = "${var.registry_username}/remote-cache-api"
        tag = gitrefpretty()
        username = var.registry_username
        password = var.registry_password
        local = true
      }
    }
  }

  deploy {
    use "nomad-jobspec" {
      jobspec = templatefile("${path.app}/remote-cache-api/remote-cache-api.nomad.tpl", {
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

variable "tag" {
  type    = string
  default = "7.0.8"
}
