package org.m2.service_offres.boundary;

import org.m2.service_offres.control.OffreAssembler;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @GetMapping
    public ResponseEntity<?> getAllOffres() {
        return ResponseEntity.ok(oa.toCollectionModel(or.findAll()));
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

    /**
     * GET
     * offres/id_offre/users
     */

    /**
     * POST offres/id_offre
     */

    /**
     * PUT
     * offres/id_offre/id_candidature
     */

    /**
     * PUT offres/id_offre
     */

    /**
     * DELETE
     * offres/id_offre
     */
}
