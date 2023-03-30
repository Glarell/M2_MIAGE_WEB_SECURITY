package org.m2.services_personnes.control;

import org.m2.services_personnes.boundary.PersonneRepresentation;
import org.m2.services_personnes.entity.Candidature;
import org.m2.services_personnes.entity.Personne;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CandidatureAssembler implements RepresentationModelAssembler<Candidature, EntityModel<Candidature>> {
    @Override
    public EntityModel<Candidature> toModel(Candidature candidature) {
        return EntityModel.of(candidature,
                linkTo(methodOn(PersonneRepresentation.class).getCandidature(candidature.getIdPersonne(),candidature.getIdCandidature())).withSelfRel(),
                linkTo(methodOn(PersonneRepresentation.class).getPersonne(candidature.getIdPersonne())).withSelfRel(),
                linkTo(methodOn(PersonneRepresentation.class).getAllPersonnes()).withRel("personnes"));
    }

    @Override
    public CollectionModel<EntityModel<Candidature>> toCollectionModel(Iterable<? extends Candidature> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
