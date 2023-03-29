package org.m2.service_offres.stash;

import com.google.gson.Gson;

public class Candidature {
    private int idCandidature;
    private int idPersonne;
    private int idOffre;
    private String dateCandidature;
    private boolean isActive;

    public int getIdCandidature() {
        return idCandidature;
    }

    public void setIdCandidature(int idCandidature) {
        this.idCandidature = idCandidature;
    }

    public int getIdPersonne() {
        return idPersonne;
    }

    public void setIdPersonne(int idPersonne) {
        this.idPersonne = idPersonne;
    }

    public int getIdOffre() {
        return idOffre;
    }

    public void setIdOffre(int idOffre) {
        this.idOffre = idOffre;
    }

    public String getDateCandidature() {
        return dateCandidature;
    }

    public void setDateCandidature(String dateCandidature) {
        this.dateCandidature = dateCandidature;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this, Candidature.class);
    }
}
