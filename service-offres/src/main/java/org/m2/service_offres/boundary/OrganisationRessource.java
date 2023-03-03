package org.m2.service_offres.boundary;

import org.m2.service_offres.entity.Offre;
import org.m2.service_offres.entity.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganisationRessource extends JpaRepository<Organisation,Integer> {


}
