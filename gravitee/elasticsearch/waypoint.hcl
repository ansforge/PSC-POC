project = "gravitee/elasticsearch"

# Labels can be specified for organizational purposes.
labels = { "domaine" = "gravitee" }

runner {
    enabled = true
    profile = "gravitee"
    data_source "git" {
        url  = "https://github.com/ansforge/PSC-POC.git"
        ref  = "main"
        path = "gravitee/elasticsearch"
        ignore_changes_outside_path = true
    }
    poll {
        enabled = false
        interval = "24h"
    }
}
# An application to deploy.
app "gravitee/elasticsearch" {

    build {
        use "docker-pull" {
            image = var.image
            tag   = var.tag
            disable_entrypoint = true
        }

    registry {
      use "docker" {
        image    = "${var.registry}/elasticsearch"
        tag      = var.tag
        username = var.registry_username
        password = var.registry_password
        local    = false
      }
     }

    }

    # Deploy to Nomad
    deploy {
        use "nomad-jobspec" {
            jobspec = templatefile("${path.app}/gravitee-elasticsearch.nomad.tpl", {
                datacenter = var.datacenter
                image = var.image
                tag = var.tag
                es_repo_fqdn = var.es_repo_fqdn
            })
        }
    }
}

variable "datacenter" {
    type = string
    default = "pocs-ans-psc"
}

variable "image" {
  type    = string
  default = "bitnami/elasticsearch"
}

variable "tag" {
  type    = string
  default = "7.17.2"
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

variable "es_repo_fqdn" {
    type = string
    default = "pocs.apim-es.api.esante.gouv.fr"
}
