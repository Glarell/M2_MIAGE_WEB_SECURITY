package org.m2.service_offres.boundary;

import org.m2.service_offres.entity.Offre;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.HashMap;


public class OffreSpecification implements Specification<Offre> {

    String columnName;
    String value;
    public OffreSpecification(String columnName, String value) {
        this.columnName=columnName;
        this.value=value;
    }

    @Override
    public Specification<Offre> and(Specification<Offre> other) {
        return Specification.super.and(other);
    }

    @Override
    public Specification<Offre> or(Specification<Offre> other) {
        return Specification.super.or(other);
    }

    @Override
    public Predicate toPredicate(Root<Offre> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        try {
            Integer value = Integer.parseInt(this.value);
            return criteriaBuilder.equal(root.get(this.columnName), value);
        } catch (Exception e ) {
            if (this.value.equals("true")) {
                return criteriaBuilder.isTrue(root.get(this.columnName));
            } else if (this.value.equals("false")) {
                return criteriaBuilder.isFalse(root.get(this.columnName));
            }
            return criteriaBuilder.like(root.get(this.columnName), "%"+this.value+"%");
        }
    }
}
