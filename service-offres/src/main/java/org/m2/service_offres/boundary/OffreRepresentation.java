package org.m2.service_offres.boundary;

import org.apache.coyote.Response;
import org.m2.service_offres.control.OffreAssembler;
import org.m2.service_offres.entity.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@Controller
@ResponseBody
@RequestMapping(value = "/offres", produces = MediaType.APPLICATION_JSON_VALUE)
public class OffreRepresentation {

    private final OffreRessource or;

    private final OrganisationRessource orgar;
    private final LieuStageRessource ltr;
    private final AdresseRessource ar;
    private final GeoRessource gr;
    private final OffreAssembler oa;

    public OffreRepresentation(OffreRessource or, OffreAssembler oa, OrganisationRessource orgar, LieuStageRessource ltr, AdresseRessource ar, GeoRessource gr) {
        this.or = or;
        this.oa = oa;
        this.orgar = orgar;
        this.ltr = ltr;
        this.ar = ar;
        this.gr = gr;
    }

    @GetMapping(value = "/{offreId}")
    public ResponseEntity<?> getOffre(@PathVariable("offreId") Integer id) {
        return Optional.of(or.findById(id))
                .filter(Optional::isPresent)
                .map(i -> ResponseEntity.ok(oa.toModel(i.get())))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET
     * offres/
     */
    @GetMapping
    public ResponseEntity<?> getAllOffres() {
        return ResponseEntity.ok(oa.toCollectionModel(or.findAllActive()));
    }

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
        offre.setOrganisation(this.manageOrganisation(offre));
        offre.setLieuStage(this.manageLieuStage(offre));
        offre.setActive(true);
        try {
            if (offre.getNomStage() == null) {
                throw new DataIntegrityViolationException("Nom stage incorrect !");
            }
            offre.setIdOffre(3);
            Offre saved = or.save(offre);
            System.out.println("SAVED DONE");
            System.out.println(saved);
            URI location = org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo(OffreRepresentation.class).slash(saved.getIdOffre()).toUri();
            return ResponseEntity.created(location).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().build();
        }

    }

    public Organisation manageOrganisation(Offre offre){
        int idOrga = offre.getOrganisation().getIdOrganisation();
        Organisation orga = offre.getOrganisation();
        if (idOrga != 0 && this.orgar.findById(idOrga).isEmpty()){
            return null;
        }
        if (idOrga == 0){
            Adresse adr = orga.getAdresse();
            if (adr.getIdAdresse() != 0 && this.ar.findById(adr.getIdAdresse()).isEmpty()){
                return null;
            }
            if (adr.getIdAdresse() == 0 && adr.verify()){
                adr = this.ar.save(adr);
            }
            orga.setAdresse(adr);
            orga = this.orgar.save(orga);
        }
        return orga;
    }

    public LieuStage manageLieuStage(Offre offre){
        int idLieuStage = offre.getLieuStage().getIdLieuStage();
        LieuStage lieuStage = offre.getLieuStage();
        if (idLieuStage != 0 && this.ltr.findById(idLieuStage).isEmpty()){
            return null;
        }
        if (idLieuStage == 0){
            Adresse adr = lieuStage.getAdresse();
            boolean adr_valid = false;
            if (adr.getIdAdresse() != 0 && this.ar.findById(adr.getIdAdresse()).isEmpty()){
                return null;
            }
            if (adr.getIdAdresse() == 0 && adr.verify()){
                adr = this.ar.save(adr);
                adr_valid = true;
            }
            Geo geo = lieuStage.getGeo();
            boolean geo_valid = false;
            if (geo.getIdGeo() != 0 && this.gr.findById(geo.getIdGeo()).isEmpty()){
                return null;
            }
            if (geo.getIdGeo() == 0 && geo.verify()){
                geo = this.gr.save(geo);
                geo_valid = true;
            }
            if (adr_valid && geo_valid){
                lieuStage.setAdresse(adr);
                lieuStage.setGeo(geo);
                lieuStage = this.ltr.save(lieuStage);
            }
        }
        return lieuStage;
    }
    /**
     * PUT offres/id_offre
     */
    @PutMapping(value="/{offreId}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<?> updateOffre(@PathVariable("offreId") Integer id, @RequestBody Offre new_offre) {
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
        return ResponseEntity.ok().build();
    }
}
