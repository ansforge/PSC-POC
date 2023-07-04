package fr.ans.psc.rass.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import fr.ans.psc.dam.api.ApiUtil;
import fr.ans.psc.dam.api.StructureApiDelegate;
import fr.ans.psc.rass.model.StructureIds;
import lombok.extern.slf4j.Slf4j;
import nonapi.io.github.classgraph.utils.StringUtils;

@Service
@Slf4j
public class StructureApiImpl implements StructureApiDelegate {

	@Autowired
	ApiExecutor exec;

	@Override
	public ResponseEntity<List<StructureIds>> getIds(List<String> arrayTechnicalId) {
	//public ResponseEntity<StructureIds> getIds(String structureTechnicalId) {
		log.debug("StructureApiImpl::getIds IN List StructureIds: {} ", String.join(", ", arrayTechnicalId));
		getRequest().ifPresent(request -> {

			for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
				if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
					String exampleString = "{ \"structureTechnicalId\" : \"structureTechnicalId\" }";
					ApiUtil.setExampleResponse(request, "application/json", exampleString);
					break;
				}
			}
		});
	
		List<StructureIds> ps = exec.getIds(arrayTechnicalId);
		if (ps == null || ps.isEmpty()) {
			log.debug("Aucun d'Id métier trouvé pour structureTechnicalIds: {} ",
					String.join(", ", arrayTechnicalId));
			return new ResponseEntity<>(HttpStatus.GONE);
		}

		return new ResponseEntity<>(ps, HttpStatus.OK);
	}

}