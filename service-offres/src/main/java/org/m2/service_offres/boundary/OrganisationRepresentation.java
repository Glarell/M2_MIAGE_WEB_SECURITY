package org.m2.service_offres.boundary;

import org.m2.service_offres.control.OffreAssembler;
import org.m2.service_offres.control.OrganisationAssembler;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
@ResponseBody
@RequestMapping(value = "/organisations", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrganisationRepresentation {

    private final OrganisationRessource orgar;
    private final OrganisationAssembler orgaa;

    public OrganisationRepresentation(OrganisationRessource orgar, OrganisationAssembler orgaa) {
        this.orgar=orgar;
        this.orgaa=orgaa;
    }

    /**
     * GET
     * organisations/
     */
    @GetMapping
    public ResponseEntity<?> getAllOrganisations() {
        return ResponseEntity.ok(orgaa.toCollectionModel(orgar.findAll()));
    }

    /**
     * GET
     * organisations/id_org
     */
    @GetMapping(value="/{organisation_id}/")
    public ResponseEntity<?> getOrganisation(@PathVariable("organisation_id") Integer id) {
        return Optional.of(orgar.findById(id))
                .filter(Optional::isPresent)
                .map(i -> ResponseEntity.ok(orgaa.toModel(i.get())))
                .orElse(ResponseEntity.notFound().build());
    }
}
