package org.m2.service_offres.entity;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "offre")
public class Offre implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int idOffre;
    private String nomStage;
    private String domaine;
    private String descriptionStage;
    private String datePublicationOffre;
    private int niveauEtudesStage;
    private int experienceRequiseStage;
    private String dateDebutStage;
    private int dureeStage;
    private long salaireStage;
    private long indemnisation;
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name="idOrganisation", referencedColumnName = "idorganisation")
    private Organisation organisation;
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name="idLieuStage", referencedColumnName = "idlieustage")
    private LieuStage lieuStage;

    private boolean isActive;

    /*@OneToMany(targetEntity = Candidature.class, mappedBy = "idOffre", cascade = CascadeType.REMOVE)
    private List<Candidature> candidatures = new ArrayList<>();*/

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this, Offre.class);
    }
}
