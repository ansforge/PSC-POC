#(c) Copyright 2023, ANS. All rights reserved.
FROM httpd:2.4-bookworm
COPY ./ANS_ssl.conf /usr/local/apache2/conf/extra/
RUN set -eux \
        && apt-get update \
        && apt-get install -y --no-install-recommends \
            curl \
			libjansson-dev \
			libcjose-dev \
			libpcre3-dev \
			pkg-config \
			libapr1-dev \
			libaprutil1-dev \
            libcurl4-openssl-dev \
			libssl-dev \
			liboauth2-dev \
			liboauth2-apache-dev \
			libxml2-dev \
			gcc \
			make \
			vim \
	#create VirtualHost folder
	  && mkdir /usr/local/apache2/conf/sites \
	# compile and install mod_auth_openidc
		&& cd /usr/src \
		&& curl -fLO https://github.com/OpenIDC/mod_auth_openidc/releases/download/v2.4.14.2/mod_auth_openidc-2.4.14.2.tar.gz \
        && tar xzf mod_auth_openidc-2.4.14.2.tar.gz \
		&& cd mod_auth_openidc-2.4.14.2 \
		&& ./configure --with-apxs=/usr/local/apache2/bin/apxs --prefix=/usr/local/apache2 \
		&& make \
		&& make install \
		&& cd /usr/src \
		&& rm -f mod_auth_openidc-2.4.14.2.tar.gz \
		&& rm -rf mod_auth_openidc-2.4.14.2 \
	# compile and install mod_sts	
	    && cd /usr/src \
		&& curl -fLO https://github.com/OpenIDC/mod_sts/releases/download/v3.3.0/mod_sts-3.3.0.tar.gz \
        && tar xzf mod_sts-3.3.0.tar.gz \
		&& cd mod_sts-3.3.0 \
		&& ./configure --with-apxs=/usr/local/apache2/bin/apxs --prefix=/usr/local/apache2 \
		&& make \
		&& make install \
		&& cd /usr/src \
		&& rm -f mod_sts-3.3.0.tar.gz \
		&& rm -rf mod_sts-3.3.0 \
	#load the compiled modules 
		&& sed -i \
		     '/^#LoadModule .*mod_rewrite.so$/a LoadModule auth_openidc_module modules\/mod_auth_openidc.so\nLoadModule sts_module modules\/mod_sts.so' \
			  /usr/local/apache2/conf/httpd.conf \
	#SSL  https://hub.docker.com/_/httpd with custom SSL configuration file
		&& sed -i \
		-e 's/^#\(Include .*\)\(httpd-ssl.conf\)/\1ANS_ssl.conf/' \
		-e 's/^#\(LoadModule .*mod_ssl.so\)/\1/' \
		-e 's/^#\(LoadModule .*mod_socache_shmcb.so\)/\1/' \
		/usr/local/apache2/conf/httpd.conf \
	#load necessary modules for our sites and define vhosts location
		&& sed -i \
		-e 's/^#\(LoadModule .*mod_rewrite.so\)/\1/' \
		-e 's/^#\(LoadModule .*mod_proxy.so\)/\1/' \
		-e 's/^#\(LoadModule .*mod_proxy_http.so\)/\1/' \
		-e 's/^#\(LoadModule .*mod_http2.so\)/\1/' \
		-e 's/^#\(LoadModule .*mod_session.so\)/\1/' \
		-e 's/^#\(LoadModule .*mod_session.cookie.so\)/\1/' \
		-e 's/^#\(LoadModule .*mod_proxy_http2.so\)/\1/' \
		-e 's/^#Include .*httpd-vhosts.conf/IncludeOptional conf\/sites\/\*\.conf/' \
	     /usr/local/apache2/conf/httpd.conf \
	#certificat(s) and virtualhost(s) will be injected by Nomad
	#purge and security
  	     && apt-get purge -y --no-install-recommends libjansson-dev \
			gcc \
			make \
	# smoke test
	    && httpd -v
		
		
