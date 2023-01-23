package org.m2.service_offres.boundary;

import org.m2.service_offres.entity.Offre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OffreRessource extends JpaRepository<Offre,String> {

}
