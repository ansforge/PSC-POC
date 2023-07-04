package fr.ans.psc.remote.cache.api.model;

public class DataSchema {

    private String schemaId;

    private String schema;

    public DataSchema() {
    }

    public DataSchema(String schemaId, String schema) {
        this.schemaId = schemaId;
        this.schema = schema;
    }

    public String getSchemaId() {
        return schemaId;
    }

    public void setSchemaId(String schemaId) {
        this.schemaId = schemaId;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }
}
