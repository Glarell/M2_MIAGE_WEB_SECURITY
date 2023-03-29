package org.m2.service_offres.boundary;

import org.m2.service_offres.entity.Offre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;

public interface OffreRessource extends JpaRepository<Offre,Integer> {

    //ArrayList<Candidature> findAllByidOffre(Integer idOffre);

    @Query(value = "SELECT * from Offre WHERE isActive=TRUE",nativeQuery = true)
    List<Offre> findAllActive();
}
