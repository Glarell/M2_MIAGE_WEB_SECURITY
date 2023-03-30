package org.m2.service_offres.control;

import org.m2.service_offres.boundary.OffreRepresentation;
import org.m2.service_offres.entity.Offre;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.HashMap;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OffreAssembler implements RepresentationModelAssembler<Offre, EntityModel<Offre>> {
    @Override
    public EntityModel<Offre> toModel(Offre offre) {
        return EntityModel.of(offre,
                linkTo(methodOn(OffreRepresentation.class).getOffre(offre.getIdOffre())).withSelfRel(),
                linkTo(methodOn(OffreRepresentation.class).getAllOffres(new HashMap<String, String>() {{
                    put("isActive", "true");
                }})).withRel("offres"));
    }

    @Override
    public CollectionModel<EntityModel<Offre>> toCollectionModel(Iterable<? extends Offre> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
