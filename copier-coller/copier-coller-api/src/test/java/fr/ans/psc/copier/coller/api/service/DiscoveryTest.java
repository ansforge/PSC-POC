package fr.ans.psc.copier.coller.api.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import fr.ans.psc.copier.coller.api.CopierCollerApiApplication;
import fr.ans.psc.copier.coller.api.exception.PscSchemaException;
import fr.ans.psc.copier.coller.api.service.DiscoveryService;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ContextConfiguration(classes = CopierCollerApiApplication.class)
public class DiscoveryTest {

    @Autowired
    private DiscoveryService discovery;

//    @Test
//    @DisplayName("should get all schemas")
//    public void getAllSchemasTest() throws PscSchemaException, IOException {
//        List<String> schemas = discovery.getAllSchemas();
//        String schemasResponse = Files.readString(new ClassPathResource("all-schemas.txt").getFile().toPath());
//        assertEquals(schemasResponse, schemas.toString());
//    }

//    @Test
//    @DisplayName("should get specific schema")
//    public void getSchemaByIdTest() throws PscSchemaException, IOException {
//        String schema = discovery.getPscJsonContextSchema("alt");
//        String schemaResponse = Files.readString(new ClassPathResource("alt.txt").getFile().toPath());
//        assertEquals(schemaResponse, schema);
//    }
}
