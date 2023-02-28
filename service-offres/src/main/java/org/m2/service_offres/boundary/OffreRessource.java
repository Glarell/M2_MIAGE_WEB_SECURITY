package org.m2.service_offres.boundary;

import org.m2.service_offres.entity.Candidature;
import org.m2.service_offres.entity.Offre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface OffreRessource extends JpaRepository<Offre,Integer> {

    //ArrayList<Candidature> findAllByidOffre(Integer idOffre);

}
