package org.m2.services_personnes.boundary;

import org.m2.services_personnes.entity.Candidature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CandidatureRessource extends JpaRepository<Candidature, Integer> {

    @Query(value = "SELECT * from candidature WHERE idOffre=?2 AND idPersonne=?1 AND isActive=TRUE", nativeQuery = true)
    List<Candidature> getCandidatureByIdAndUser(Integer user_id, Integer offre_id);

    @Query(value = "SELECT * FROM candidature WHERE idPersonne=?1", nativeQuery = true)
    List<Candidature> getCandidaturesByUser(Integer user_id);

    @Query(value = "SELECT * FROM candidature WHERE idOffre=?1 AND isActive=TRUE", nativeQuery = true)
    List<Candidature> getCandidaturesByIdOffre(Integer offre_id);
}
