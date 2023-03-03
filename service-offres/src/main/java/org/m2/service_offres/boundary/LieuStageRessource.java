package org.m2.service_offres.boundary;

import org.m2.service_offres.entity.Geo;
import org.m2.service_offres.entity.LieuStage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LieuStageRessource extends JpaRepository<LieuStage,Integer> {


}
