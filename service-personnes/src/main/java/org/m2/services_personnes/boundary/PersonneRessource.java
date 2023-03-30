package org.m2.services_personnes.boundary;

import org.m2.services_personnes.entity.Candidature;
import org.m2.services_personnes.entity.Personne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface PersonneRessource extends JpaRepository<Personne,Integer> {

}
