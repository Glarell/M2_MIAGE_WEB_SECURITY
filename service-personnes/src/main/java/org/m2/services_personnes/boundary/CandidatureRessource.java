package org.m2.services_personnes.boundary;

import org.m2.services_personnes.entity.Candidature;
import org.m2.services_personnes.entity.Personne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface CandidatureRessource extends JpaRepository<Candidature,Integer> {

    @Query(value = "SELECT * from candidature WHERE idOffre=?2 AND idPersonne=?1 AND isActive=TRUE",nativeQuery = true)
    ArrayList<Candidature> getCandidatureByIdAndUser(Integer user_id, Integer offre_id);

    @Query(value = "SELECT * FROM candidature WHERE idPersonne=?1", nativeQuery = true)
    ArrayList<Candidature> getCandidaturesByUser(Integer user_id);
}
