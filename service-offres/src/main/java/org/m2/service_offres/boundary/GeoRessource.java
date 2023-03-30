package org.m2.service_offres.boundary;

import org.m2.service_offres.entity.Geo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeoRessource extends JpaRepository<Geo, Integer> {


}
