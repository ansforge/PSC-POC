package fr.ans.psc.dam.model;

public class Activity {
	
	  private String identifiantLieuDeTravail = null;
	  
	  private String identifiantTechniqueDeLaStructure = null;
	  
	  private String codeModeExercice = null;

	  private String codeProfession = null;
	  
	  private String nomDexercice = null;
	  
	  private String raisonSocialeSite = null;


	public Activity(String identifiantTechniqueDeLaStructure, String codeModeExercice, String codeProfession,
			String nomDexercice, String raisonSocialeSite) {
		super();
		this.identifiantTechniqueDeLaStructure = identifiantTechniqueDeLaStructure;
		this.codeModeExercice = codeModeExercice;
		this.codeProfession = codeProfession;
		this.nomDexercice = nomDexercice;
		this.raisonSocialeSite = raisonSocialeSite;
	}

	public String getIdentifiantLieuDeTravail() {
		return identifiantLieuDeTravail;
	}

	public void setIdentifiantLieuDeTravail(String identifiantLieuDeTravail) {
		this.identifiantLieuDeTravail = identifiantLieuDeTravail;
	}

	public String getIdentifiantTechniqueDeLaStructure() {
		return identifiantTechniqueDeLaStructure;
	}

	public void setIdentifiantTechniqueDeLaStructure(String identifiantTechniqueDeLaStructure) {
		this.identifiantTechniqueDeLaStructure = identifiantTechniqueDeLaStructure;
	}

	public String getCodeModeExercice() {
		return codeModeExercice;
	}

	public void setCodeModeExercice(String codeModeExercice) {
		this.codeModeExercice = codeModeExercice;
	}

	public String getCodeProfession() {
		return codeProfession;
	}

	public void setCodeProfession(String codeProfession) {
		this.codeProfession = codeProfession;
	}

	public String getNomDexercice() {
		return nomDexercice;
	}

	public void setNomDexercice(String nomDexercice) {
		this.nomDexercice = nomDexercice;
	}

	public String getRaisonSocialeSite() {
		return raisonSocialeSite;
	}

	public void setRaisonSocialeSite(String raisonSocialeSite) {
		this.raisonSocialeSite = raisonSocialeSite;
	}

	  

}
