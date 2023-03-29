package org.m2.service_offres.stash;

import com.google.gson.Gson;

import java.util.ArrayList;

public class CandidatureList extends ArrayList<Candidature> {

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this, Candidature.class);
    }
}
