package fr.ans.psc.dam.bdd;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.jupiter.tools.spring.test.mongo.annotation.MongoDataSet;
import com.jupiter.tools.spring.test.mongo.junit5.MongoDbExtension;

import fr.ans.psc.RassStructureIDApiApplication;
import fr.ans.psc.rass.model.StructureIds;
import fr.ans.psc.rass.repository.GenericRepository;

@SpringBootTest
@ContextConfiguration(classes = RassStructureIDApiApplication.class)
@DirtiesContext
@ExtendWith( MongoDbExtension.class)
@ActiveProfiles("test-mongo")
@AutoConfigureDataMongo


public class MongoDBTest {

	@Autowired
	GenericRepository repo;
	
	@Test
	@MongoDataSet(value = "/data-mongo/rass-structure.json", cleanBefore = true, cleanAfter = true)
	public void readMongoDBTest() {		
		
		StructureIds structureIds = repo.findByStructureTechnicalId("6");
		assertNotNull(structureIds);
		assertEquals(structureIds.getIdentifiantMetier(), "99900007776001");
		
		structureIds = repo.findByStructureTechnicalId("R10000000553881");
		assertNotNull(structureIds);
		assertEquals(structureIds.getIdentifiantMetier(), "10002525375006");
	}
	
	
	@Test
	@MongoDataSet(value = "/data-mongo/rass-structure.json", cleanBefore = true, cleanAfter = true)
	public void readListMongoDBTest() {		
		
		List<String> ids = new ArrayList<String>();
		ids.add("6");
		ids.add("R10000000553881");
		List<StructureIds> structureIds = repo.findByStructureTechnicalIdIn(ids);
		assertNotNull(structureIds);
		assertFalse(structureIds.isEmpty());
		assertEquals(structureIds.size(),2);
		for (StructureIds structureIds2 : structureIds) {
			if (structureIds2.getStructureTechnicalId().equals("6"))
			{assertEquals(structureIds2.getIdentifiantMetier(), "99900007776001");}
			
			if (structureIds2.getStructureTechnicalId().equals("R10000000553881"))
			{assertEquals(structureIds2.getIdentifiantMetier(), "10002525375006");}
		}
		
	}

}
