package org.m2.services_personnes.boundary;

import org.m2.services_personnes.control.CandidatureAssembler;
import org.m2.services_personnes.control.PersonneAssembler;
import org.m2.services_personnes.entity.Candidature;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@ResponseBody
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonneRepresentation {


    private final PersonneRessource pr;
    private final CandidatureRessource cr;

    private final PersonneAssembler pa;
    private final CandidatureAssembler ca;

    public PersonneRepresentation(PersonneRessource pr, CandidatureRessource cr, PersonneAssembler pa, CandidatureAssembler ca) {
        this.pr = pr;
        this.cr = cr;
        this.pa = pa;
        this.ca = ca;
    }

    /**
     * GET
     * users/user_id/
     */
    @GetMapping(value = "/{user_id}/")
    public ResponseEntity<?> getPersonne(@PathVariable("user_id") Integer id) {
        return Optional.of(pr.findById(id))
                .filter(Optional::isPresent)
                .map(i -> ResponseEntity.ok(pa.toModel(i.get())))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST
     * users/candidatures/create/
     */
    @PostMapping(value = "/candidatures/create/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Candidature> createCandidature(@RequestBody Candidature candidature) {
        List<Candidature> candidatures = cr.getCandidatureByIdAndUser(candidature.getIdPersonne(), candidature.getIdOffre());
        if (!candidatures.isEmpty())
            throw new EntityExistsException("Une candidature active existe déjà pour cette personne !");
        candidature.setIdCandidature(UUID.randomUUID().version());
        candidature.setActive(true);
        try {
            Candidature saved = cr.save(candidature);
            URI location = org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo(PersonneRepresentation.class).slash(saved.getIdCandidature()).toUri();
            return ResponseEntity.created(location).build();
        } catch (EntityExistsException e) {
            return ResponseEntity.unprocessableEntity().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * DELETE
     * users/user_id/candidatures/offre_id/
     */
    @DeleteMapping(value = "{user_id}/candidatures/{offre_id}/")
    public ResponseEntity<?> deleteCandidature(@PathVariable("user_id") Integer user_id, @PathVariable("offre_id") Integer offre_id) {
        List<Candidature> candidatures = cr.getCandidatureByIdAndUser(user_id, offre_id);
        if (candidatures.isEmpty())
            throw new EntityExistsException("Aucune candidature active pour cette offre existe !");
        try {
            Candidature candidature = candidatures.get(0);
            candidature.setActive(false);
            cr.save(candidature);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * GET
     * users/
     */
    @GetMapping
    public ResponseEntity<?> getAllPersonnes() {
        return ResponseEntity.ok(pa.toCollectionModel(pr.findAll()));
    }

    /**
     * GET
     * users/user_id/offre_id/
     */
    @GetMapping(value = "/{user_id}/{offre_id}/")
    public ResponseEntity<?> getCandidature(@PathVariable("user_id") Integer user_id, @PathVariable("offre_id") Integer offre_id) {
        return ResponseEntity.ok(ca.toCollectionModel(cr.getCandidatureByIdAndUser(user_id, offre_id)));
    }

    /**
     * GET
     * users/user_id/candidatures/
     */
    @GetMapping(value = "/{user_id}/candidatures/")
    public ResponseEntity<?> getCandidaturesById(@PathVariable("user_id") Integer user_id) {
        return ResponseEntity.ok(ca.toCollectionModel(cr.getCandidaturesByUser(user_id)));
    }

    /**
     * GET
     * users/candidatures/id_offre/
     */
    @GetMapping(value = "/candidatures/{offre_id}/")
    public ResponseEntity<?> getCandidaturesByIdOffre(@PathVariable("offre_id") Integer offre_id) {
        return ResponseEntity.ok(ca.toCollectionModel(cr.getCandidaturesByIdOffre(offre_id)));
    }

    /**
     * GET
     * users/candidatures/models/id_offre/
     */
    @GetMapping(value = "/candidatures/models/{offre_id}/")
    public ResponseEntity<?> getCandidaturesModelsByIdOffre(@PathVariable("offre_id") Integer offre_id) {
        return ResponseEntity.ok(cr.getCandidaturesByIdOffre(offre_id));
    }

    /**
     * PUT
     * users/candidatures/id_candidature/
     */
    @PutMapping(value = "/candidatures/{candidature_id}/")
    public ResponseEntity<?> putCandidature(@PathVariable("candidature_id") Integer candidature_id, @RequestBody Candidature candidature) {
        if (!cr.existsById(candidature_id)) {
            return ResponseEntity.notFound().build();
        }
        candidature.setIdCandidature(candidature_id);
        cr.save(candidature);
        return ResponseEntity.ok().build();
    }
}
