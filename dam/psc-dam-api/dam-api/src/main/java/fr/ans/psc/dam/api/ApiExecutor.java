package fr.ans.psc.dam.api;

import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import fr.ans.psc.api.client.dam.reader.GetFullDamsApi;
//import fr.ans.psc.api.client.dam.reader.invoker.ApiClient;
import fr.ans.psc.api.client.dam.reader.model.SimpleDams;
import fr.ans.psc.api.client.structure.reader.StructureApi;
import fr.ans.psc.api.client.structure.reader.model.StructureIds;
import fr.ans.psc.dam.api.called.ConsumedWsConfiguration;
import fr.ans.psc.dam.api.exception.ThrowDamException;
import fr.ans.psc.dam.model.PsDAMs;
import fr.ans.psc.dam.model.RichDam;
import fr.ans.psc.dam.model.UserActivities;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ApiExecutor {

	@Autowired
	ConsumedWsConfiguration wsConf;

	fr.ans.psc.api.client.dam.reader.invoker.ApiClient damApiClient = new fr.ans.psc.api.client.dam.reader.invoker.ApiClient();

	fr.ans.psc.api.client.structure.reader.invoker.ApiClient structureApiClient = new fr.ans.psc.api.client.structure.reader.invoker.ApiClient();

	GetFullDamsApi damReader = new GetFullDamsApi();

	StructureApi structureReader = new StructureApi();
	
	public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	public PsDAMs getDAMs(String idNational, Boolean dontFermes, String idTechniqueStructure,
			String modeExercice) {

		// lecture de la totalité des DAMs du PS et convertion en Objet PsDAMs (test facility)
		return get_PsDAMs(idNational, dontFermes, idTechniqueStructure, modeExercice); 
		
	}

	
	// *************************************************************************************

	public PsDAMs get_PsDAMs(String NationalID, Boolean dontFermes, String idTechniqueStructure, String modeExercice) {

		SimpleDams simpleDAMs = get_dams(NationalID);
		log.debug("Lecture des 'userDAMS' effectuée, simpleDAMs.size: {}", simpleDAMs.size());

		// filtre sur les DAMs fermés depuis moins de 3 mois
		if (!dontFermes) {
			simpleDAMs = filterActiveDAM(simpleDAMs);
		}

		//filtre sur la structure
		if (idTechniqueStructure != null) {
			simpleDAMs = filterIdTechniqueStructure(simpleDAMs, idTechniqueStructure);
		}

		//filtre mode activité: libérale / salariée
		if (modeExercice != null) {
			if (modeExercice.equals("0") || modeExercice.equals("1")) {
				simpleDAMs = filterModeExercice(simpleDAMs, modeExercice);
			} else {
				ThrowDamException.throwExceptionRequestError("Le mode d'exercice doit avoir pour valeur 0 ou 1",
						HttpStatus.BAD_REQUEST);
			}
		}

		// ajout des libéllés
		PsDAMs psDAMs = convertSimpleDAMsToPsDAMs(NationalID, simpleDAMs);
		log.debug("fin conversion vers PsDAMs");
		return psDAMs;
	}

	public SimpleDams filterActiveDAM(SimpleDams simpleDAMs) {
		LocalDate today = LocalDate.now();
		log.debug("filtre sur la date de fermeture IN inital size: {}, nous sommes le {} (jj-mm-aaaa)", simpleDAMs.size(), today.format(formatter));
		SimpleDams filteredsimpleDAMs = new SimpleDams();
		simpleDAMs.forEach(simpleDAM -> {
			if (simpleDAM.getDateFinValidite() == null || simpleDAM.getDateFinValidite().isBlank()
					|| simpleDAM.getDateFinValidite().isEmpty() 
					||  LocalDate.parse(simpleDAM.getDateFinValidite(),formatter).isAfter(today)
							) {
				filteredsimpleDAMs.add(simpleDAM);
			}
		});
		log.debug("filtre sur la date de fermeture OUT final size: {}", filteredsimpleDAMs.size());
		if (filteredsimpleDAMs.isEmpty()) {
			ThrowDamException.throwExceptionRequestError("Liste des DAMs vide après filtrage sur les DAMs actuels",
					HttpStatus.GONE);
		}
		return filteredsimpleDAMs;
	}

	public SimpleDams filterModeExercice(SimpleDams simpleDAMs, String modeExercice) {
		log.debug("filtre sur le ModeExercice IN inital size: {}", simpleDAMs.size());
		SimpleDams filteredsimpleDAMs = new SimpleDams();

		simpleDAMs.forEach(simpleDAM -> {
			if (simpleDAM.getCodeModeExercice().equals(modeExercice)) {
				filteredsimpleDAMs.add(simpleDAM);
			}
		});
		log.debug("filtre sur ModeExercice OUT final size: {}", filteredsimpleDAMs.size());
		if (filteredsimpleDAMs.isEmpty()) {
			ThrowDamException.throwExceptionRequestError("Liste des DAMs vide après filtrage sur le ModeExercice",
					HttpStatus.GONE);
		}
		return filteredsimpleDAMs;
	}

	public SimpleDams get_dams(String NationalID) {
		SimpleDams simpleDAMs = null;

		try {
			damApiClient.setBasePath(wsConf.getDamReaderUrl());
			damReader.setApiClient(damApiClient);
			// Encodage de l'url à cause des NationalID en xxx/yyy
			String encodedNationalId = URLEncoder.encode(NationalID, "UTF-8");
			log.debug("NationalID {}, encodedNationalId {}", NationalID, encodedNationalId);
			simpleDAMs = damReader.lectureDesDAMs(encodedNationalId);

			log.debug("Appel WS backend lecture des DAMs OK!");
			log.debug("simpleDAMs.size {}", simpleDAMs.size());

		} catch (Exception e) {
			log.error("Exception sur la lecture des DAMs wsConf.getDamReaderUrl() :", wsConf.getDamReaderUrl());
			switch (e.getClass().getCanonicalName()) {
			case "org.springframework.web.client.HttpClientErrorException.Gone":
				ThrowDamException.throwExceptionRequestError("Pas de PS ( " + NationalID + ") ou PS sans DAMs",
						HttpStatus.GONE);
				break;
			case "org.springframework.web.client.ResourceAccessException":
				ThrowDamException.throwExceptionRequestError(
						"Exception sur appel du WS de lecture des DAMs. Service inaccessible",
						HttpStatus.SERVICE_UNAVAILABLE);
				break;
			}
			ThrowDamException.throwExceptionRequestError("Erreur sur lecture des DAMs ...",
					HttpStatus.SERVICE_UNAVAILABLE);
		}

		if (simpleDAMs == null) {
			ThrowDamException.throwExceptionRequestError("PS non trouvé ou PS sans DAMs", HttpStatus.GONE);
		}
		return simpleDAMs;
	}

	public SimpleDams filterIdTechniqueStructure(SimpleDams simpleDAMs, String idTechniqueStructure) {

		SimpleDams filteredsimpleDAMs = new SimpleDams();

		//appel Backend RASS pour conversion idTechniqueStructure en identifiantLieuDeTravail
		String identifiantLieuDeTravail= readIdLieuDeTravail(idTechniqueStructure);
		
		simpleDAMs.forEach(simpleDAM -> {
			if (simpleDAM.getIdentifiantLieuDeTravail().equalsIgnoreCase(identifiantLieuDeTravail))// (identifiantLieuDeTravail))
			{
				filteredsimpleDAMs.add(simpleDAM);
			}
		});

		log.debug("Taille de la liste après filtre sur l'IdTechnicalStrcuture: {}", filteredsimpleDAMs.size());
		if (filteredsimpleDAMs.isEmpty()) {
			ThrowDamException.throwExceptionRequestError(
					"Liste des DAMs vide après filtrage sur l'IdTEchniqcalStructure", HttpStatus.GONE);
		}
		simpleDAMs = filteredsimpleDAMs;
		return filteredsimpleDAMs;
	}

	public PsDAMs convertSimpleDAMsToPsDAMs(String NationalID, SimpleDams simpleDAMs) {
		if (simpleDAMs == null || simpleDAMs.size() == 0) {
			log.error("!!! Il y aurait du y avoir une exception levée ....");
		}
		PsDAMs psDAMs = new PsDAMs();
		psDAMs.setNationalId(NationalID);
		List<RichDam> richDams = new ArrayList<RichDam>();
		simpleDAMs.forEach(simpleDAM -> {
			RichDam richDam = new RichDam(simpleDAM);
			richDams.add(richDam);
		});
		psDAMs.setDams(richDams);
		return psDAMs;
	}

	public String readIdLieuDeTravail(String idTechniqueStructure) {
		structureApiClient.setBasePath(wsConf.getStructureReaderUrl());
		log.debug(" appel Backend IdStructure, wsConf.getStructureReaderUrl {}", wsConf.getStructureReaderUrl());
		structureReader.setApiClient(structureApiClient);
		List<StructureIds> structureIds = null;
		try {
			 List<String> ids = new ArrayList<String>();
			 ids.add(idTechniqueStructure);
			structureIds = structureReader.getIds(ids);
			log.debug("Appel WS backend lecture de l'IdLieuDeTravail OK!");
		} catch (Exception e) {
			switch (e.getClass().getCanonicalName()) {
			case "org.springframework.web.client.HttpClientErrorException.Gone":
				ThrowDamException.throwExceptionRequestError(
						"Pas de Lieu de travail pour IdStrcutureTechnique  ( " + idTechniqueStructure + ") ",
						HttpStatus.GONE);
				break;
			case "org.springframework.web.client.ResourceAccessException":
				ThrowDamException.throwExceptionRequestError(
						"Exception sur appel du WS de lecture des identifiants Lieu de travail. Service inaccessible",
						HttpStatus.SERVICE_UNAVAILABLE);
				break;
			}
			ThrowDamException.throwExceptionRequestError("Erreur sur lecture idLieuDeTravail ...",
					HttpStatus.SERVICE_UNAVAILABLE);
		}

		if (structureIds.size() != 1) {
			ThrowDamException.throwExceptionRequestError("Erreur sur lecture idLieuDeTravail. Aucun ou plusieurs id metier trouvés pour un identifiant technique de structure",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return structureIds.get(0).getIdentifiantMetier();
	}

}
