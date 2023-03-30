package org.m2.service_offres.control;

import org.m2.service_offres.boundary.OrganisationRepresentation;
import org.m2.service_offres.entity.Organisation;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrganisationAssembler implements RepresentationModelAssembler<Organisation, EntityModel<Organisation>> {
    @Override
    public EntityModel<Organisation> toModel(Organisation organisation) {
        return EntityModel.of(organisation,
                linkTo(methodOn(OrganisationRepresentation.class).getOrganisation(organisation.getIdOrganisation())).withSelfRel(),
                linkTo(methodOn(OrganisationRepresentation.class).getAllOrganisations()).withRel("organisations"));
    }

    @Override
    public CollectionModel<EntityModel<Organisation>> toCollectionModel(Iterable<? extends Organisation> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}