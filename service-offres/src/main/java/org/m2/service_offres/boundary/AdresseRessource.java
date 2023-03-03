package org.m2.service_offres.boundary;

import org.m2.service_offres.entity.Adresse;
import org.m2.service_offres.entity.Offre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdresseRessource extends JpaRepository<Adresse,Integer> {


}
