package fr.ans.psc.rass.repository;

import java.util.List;

import fr.ans.psc.rass.model.StructureIds;

public interface GenericRepository {

	public StructureIds findByStructureTechnicalId(String technicalStructureId);
	
	public List<StructureIds> findByStructureTechnicalIdIn (List<String> structureTechnicalId);
}
