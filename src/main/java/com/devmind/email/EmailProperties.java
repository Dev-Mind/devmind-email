package com.devmind.email;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Dev-Mind <guillaume@dev-mind.fr>
 * @since 04/01/18.
 */
@ConfigurationProperties(prefix = "devmind")
public class EmailProperties {
    private ExternalApi elasticmail;

    public ExternalApi getElasticmail() {
        return elasticmail;
    }

    public void setElasticmail(ExternalApi elasticmail) {
        this.elasticmail = elasticmail;
    }

    public static final class ExternalApi {
        private String apikey;
        private String host;
        private String version;

        public String getApikey() {
            return apikey;
        }

        public void setApikey(String apikey) {
            this.apikey = apikey;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }
}
