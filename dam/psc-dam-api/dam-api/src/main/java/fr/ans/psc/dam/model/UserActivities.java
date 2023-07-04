package fr.ans.psc.dam.model;

import java.util.List;

public class UserActivities {
	
    private String idNat; 
    
	private List<Activity> activites;
	
    public UserActivities(String idNat, List<Activity> activites) {
		super();
		this.idNat = idNat;
		this.activites = activites;
	}

	public String getIdNat() {
		return idNat;
	}

	public void setIdNat(String idNat) {
		this.idNat = idNat;
	}

	public List<Activity> getActivites() {
		return activites;
	}

	public void setActivites(List<Activity> activites) {
		this.activites = activites;
	}
}
