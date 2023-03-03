package org.m2.service_offres.boundary;

import org.m2.service_offres.control.OffreAssembler;
import org.m2.service_offres.entity.Candidature;
import org.m2.service_offres.entity.Offre;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.net.URI;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Controller
@ResponseBody
@RequestMapping(value="/offres", produces = MediaType.APPLICATION_JSON_VALUE)
public class OffreRepresentation {

    private final OffreRessource or;

    private final OffreAssembler oa;

    public OffreRepresentation(OffreRessource or, OffreAssembler oa) {
        this.or=or;
        this.oa=oa;
    }

    @GetMapping(value="/{offreId}")
    public ResponseEntity<?> getOffre(@PathVariable("offreId") Integer id) {
        return Optional.of(or.findById(id))
                .filter(Optional::isPresent)
                .map(i -> ResponseEntity.ok(oa.toModel(i.get())))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST
     * offres/id_offre
     */

    /**
     * POST
     * offres/
     */

    /**
     * GET
     * offres/
     */
    @GetMapping
    public ResponseEntity<?> getAllOffres() {
        return ResponseEntity.ok(oa.toCollectionModel(or.findAll()));
    }
    /**
     * GET
     * offres/id_offre/users
     */

    /**
     * POST offres/id_offre
     * -------------------------
     * POST offres/create
     */
    @PostMapping(path = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<Offre> createOffre(@RequestBody Offre offre) {
        offre.setIdOffre(UUID.randomUUID().version());
        offre.setActive(true);
        try {
            Offre saved = or.save(offre);
            URI location = org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo(OffreRepresentation.class).slash(saved.getIdOffre()).toUri();
            return ResponseEntity.created(location).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

    }
    /**
     * PUT
     * offres/id_offre/id_candidature
     */


    /**
     * PUT offres/id_offre
     */
    @PutMapping(value="{idOffre}")
    @Transactional
    public ResponseEntity<?> updateOffre(@PathVariable("idOffre") Integer id, @RequestBody Offre new_offre) {
        if (!or.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        new_offre.setIdOffre(id);
        or.save(new_offre);
        return ResponseEntity.ok().build();
    }

    /**
     * DELETE
     * offres/id_offre
     */
    @DeleteMapping(value = "/{idOffre}")
    @Transactional
    public ResponseEntity<?> deleteOffre(@PathVariable("idOffre") Integer id) {
        Optional<Offre> offre = or.findById(id);
        if (offre.isPresent()) {
            Offre to_save = offre.get();
            to_save.setActive(false);
            or.save(to_save);
        }
        return ResponseEntity.noContent().build();
    }
}
