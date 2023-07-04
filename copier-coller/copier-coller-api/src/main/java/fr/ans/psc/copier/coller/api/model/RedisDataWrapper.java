package fr.ans.psc.copier.coller.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash(value = "RedisDataWrapper")
public class RedisDataWrapper {
	
	@Id
    private String key;

    private String schemaId;

    private String bag;
    
    @TimeToLive
    private Long ttl;

    public RedisDataWrapper() {
    }

    public RedisDataWrapper(String key, String schemaId, String baggie, Long timeToLive) {
        this.key = key;
        this.schemaId = schemaId;
        bag = baggie;
        ttl = timeToLive;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSchemaId() {
        return schemaId;
    }

    public void setSchemaId(String schemaId) {
        this.schemaId = schemaId;
    }

    public String getBag() {
        return bag;
    }

    public void setBag(String baggie) {
        bag = baggie;
    }

	public Long getTtl() {
		return ttl;
	}

	public void setTtl(Long ttl) {
		this.ttl = ttl;
	}
    
    
}
