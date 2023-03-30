package org.m2.services_personnes.boundary;

import org.m2.services_personnes.entity.Personne;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonneRessource extends JpaRepository<Personne, Integer> {

}
