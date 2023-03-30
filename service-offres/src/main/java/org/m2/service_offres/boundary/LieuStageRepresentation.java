package org.m2.service_offres.boundary;

import org.m2.service_offres.control.LieuStageAssembler;
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
@RequestMapping(value = "/lieustages", produces = MediaType.APPLICATION_JSON_VALUE)
public class LieuStageRepresentation {

    private final LieuStageRessource lsr;
    private final LieuStageAssembler lsa;

    public LieuStageRepresentation(LieuStageRessource lsr, LieuStageAssembler lsa) {
        this.lsr = lsr;
        this.lsa = lsa;
    }

    /**
     * GET
     * lieustages/
     */
    @GetMapping
    public ResponseEntity<?> getAllLieuStages() {
        return ResponseEntity.ok(lsa.toCollectionModel(lsr.findAll()));
    }

    /**
     * GET
     * lieustages/id_org
     */
    @GetMapping(value = "/{lieuStage_id}/")
    public ResponseEntity<?> getLieuStage(@PathVariable("lieuStage_id") Integer id) {
        return Optional.of(lsr.findById(id))
                .filter(Optional::isPresent)
                .map(i -> ResponseEntity.ok(lsa.toModel(i.get())))
                .orElse(ResponseEntity.notFound().build());
    }
}
