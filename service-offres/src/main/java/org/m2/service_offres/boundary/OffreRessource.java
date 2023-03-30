package org.m2.service_offres.boundary;

import org.m2.service_offres.entity.Offre;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OffreRessource extends JpaRepository<Offre,Integer>, JpaSpecificationExecutor<Offre> {

    @Query(value = "SELECT * from Offre WHERE isActive=FALSE", nativeQuery = true)
    List<Offre> findAllInactive();
}
