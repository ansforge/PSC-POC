package fr.ans.psc.remote.cache.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

public class DataWrapper {
    @JsonProperty("key")
    private String key;

    @JsonProperty(value = "schemaId", required = true)
    private String schemaId;

    @JsonProperty(value = "bag", required = true)
    private JsonNode bag;
    
    public DataWrapper() {
    }

    public DataWrapper(String key, String schemaId, JsonNode baggie) {
        this.key = key;
        this.schemaId = schemaId;
        bag = baggie;
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

    public JsonNode getBag() {
        return bag;
    }

    public void setBag(JsonNode baggie) {
        bag = baggie;
    }
    
}
