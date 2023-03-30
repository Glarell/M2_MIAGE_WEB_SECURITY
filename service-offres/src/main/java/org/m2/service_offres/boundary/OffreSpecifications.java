package org.m2.service_offres.boundary;

import org.m2.service_offres.entity.Offre;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;

public class OffreSpecifications extends ArrayList<OffreSpecification> {

    Specification<Offre> offre;

    public Specification<Offre> getOffreSpecification(Specification<Offre> offre) {
        //return offre;
        if (this.size() < 2) {
            return offre.and(this.get(0));
        }
        this.offre = this.get(this.size() - 2);
        this.remove(this.size() - 1);
        return getOffreSpecification(offre.and(this.offre));
    }
}
