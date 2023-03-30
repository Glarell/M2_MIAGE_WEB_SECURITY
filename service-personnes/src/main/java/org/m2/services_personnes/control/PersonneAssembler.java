package org.m2.services_personnes.control;

import org.m2.services_personnes.boundary.PersonneRepresentation;
import org.m2.services_personnes.entity.Personne;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PersonneAssembler implements RepresentationModelAssembler<Personne, EntityModel<Personne>> {
    @Override
    public EntityModel<Personne> toModel(Personne personne) {
        return EntityModel.of(personne,
                linkTo(methodOn(PersonneRepresentation.class).getPersonne(personne.getIdPersonne())).withSelfRel(),
                linkTo(methodOn(PersonneRepresentation.class).getAllPersonnes()).withRel("personnes"));
    }

    @Override
    public CollectionModel<EntityModel<Personne>> toCollectionModel(Iterable<? extends Personne> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
