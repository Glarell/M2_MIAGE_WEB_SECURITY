package org.m2.service_offres.control;

import org.m2.service_offres.boundary.LieuStageRepresentation;
import org.m2.service_offres.boundary.OrganisationRepresentation;
import org.m2.service_offres.entity.LieuStage;
import org.m2.service_offres.entity.Organisation;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class LieuStageAssembler implements RepresentationModelAssembler<LieuStage, EntityModel<LieuStage>> {
    @Override
    public EntityModel<LieuStage> toModel(LieuStage organisation) {
        return EntityModel.of(organisation,
                linkTo(methodOn(LieuStageRepresentation.class).getLieuStage(organisation.getIdLieuStage())).withSelfRel(),
                linkTo(methodOn(LieuStageRepresentation.class).getAllLieuStages()).withRel("lieustages"));
    }

    @Override
    public CollectionModel<EntityModel<LieuStage>> toCollectionModel(Iterable<? extends LieuStage> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}