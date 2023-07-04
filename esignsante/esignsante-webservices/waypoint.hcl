project = "esignsante/webservices"

# Labels can be specified for organizational purposes.
labels = { "domaine" = "esignsante" }

runner {
  enabled = true
  profile = "psc-pocs"
  data_source "git" {
    url = "https://github.com/prosanteconnect/proof-of-concept.git"
    ref = "main"
    path = "esignsante/esignsante-webservices"
	ignore_changes_outside_path = true
  }
  poll {
    enabled = false
    interval = "24h"
  }
}

# An application to deploy.
app "esignsante/webservices" {

  # Build specifies how an application should be deployed.
  build {
    use "docker" {
      dockerfile = "${path.app}/Dockerfile"
    }

    registry {
      use "docker" {
        image = "${var.registry}/esign-webservices"
        tag   = "3.0.0.0-SNAPSHOT"
		username = var.registry_username
        password = var.registry_password
        local    = true
	  }
    }
  }

  # Deploy to Nomad
  deploy {
    use "nomad-jobspec" {
      jobspec = templatefile("${path.app}/esignsante.nomad.tpl", {
	datacenter = var.datacenter

      })
    }
  }
}

variable datacenter {
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



