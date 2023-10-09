job "gravitee-elasticsearch" {

    namespace = "gravitee"

    type = "service"

    datacenters = ["${datacenter}"]

    constraint {
      attribute = "$\u007Bnode.class\u007D"
      value     = "data"
    }
	
    update {
        stagger = "30s"
        max_parallel = 1
    }

    vault {
        policies = ["gravitee"]
        change_mode = "restart"
    }
    
    group "elasticsearch" {
        count = 1       
    
        volume "gravitee-elasticsearch" {
          type      = "host"
		  read_only = false
          source    = "gravitee-elasticsearch"
        }
    
        network {
            port "es" { to = 9200 }
            port "ed" { to = 9300 }
        }
        task "elasticsearch" {
            driver = "docker"
            volume_mount {
              volume      = "gravitee-elasticsearch"
              destination = "/bitnami/elasticsearch/data"
              read_only   = false
            }
            config {
                image = "${image}:${tag}"
                ports = ["es", "ed"]   
               # Ajout fragment de config pour désactiver geoip
                mount {
                    type = "bind"
                    target = "/opt/bitnami/elasticsearch/config/my_elasticsearch.yml"
                    source = "local/my_elasticsearch.yml"
                    readonly = true
                }
                mount {
                    type = "bind"
                    target = "/opt/bitnami/elasticsearch/config/users_roles"
                    source = "local/users_roles"
                    readonly = true
                }
                mount {
                    type = "bind"
                    target = "/docker-entrypoint-initdb.d/userinit.sh"
                    source = "secrets/userinit.sh"
                    readonly = true
                }				
            }

            resources {
                cpu = 200
                memory = 8000
            }

            env = {
                "discovery.type" = "single-node"
            }

            service {
                tags = ["urlprefix-${es_repo_fqdn}/"]
                name = "$\u007BNOMAD_JOB_NAME\u007D"
                port = "es"
                check {
                    name = "alive"
                    type = "tcp"
                    interval = "10s"
                    timeout = "2s"
                }
            }
            template{
		data = <<EOH
ingest.geoip.downloader.enabled: false
EOH
		destination="local/my_elasticsearch.yml"
            }
            template{
                data = <<EOH
superuser:{{ with secret "gravitee/elasticsearch" }}{{.Data.data.root_user}}{{end}}
EOH
                destination="local/users_roles"
            }
            template{
                data = <<EOH
#!/bin/sh

elasticsearch-users useradd {{ with secret "gravitee/elasticsearch" }}{{.Data.data.root_user}} -p {{.Data.data.root_pass}}{{end}}
EOH
                destination="secrets/userinit.sh"
                perms="777"
            }
            template{
                data = <<EOH
ELASTICSEARCH_ENABLE_SECURITY=true
ELASTICSEARCH_SKIP_TRANSPORT_TLS=true
EOH
                destination = "secrets/.env"
                env = true
            }
        }
    }
}
