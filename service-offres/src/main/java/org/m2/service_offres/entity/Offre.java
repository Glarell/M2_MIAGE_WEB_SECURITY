package org.m2.service_offres.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Offre implements Serializable {

    @Id
    private int id;
    private String nomStage;
    private String domaine;
    private String nomOrganisation;
    private String descriptionStage;
    private String datePublicationOffre;
    private int niveauEtudesStage;
    private int experienceRequiseStage;
    private String dateDebutStage;
    private int dureeStage;
    private long salaireStage;
    private long indemnisation;
    private Organisation organisation;
    private LieuStage lieuStage;

    private class Organisation implements Serializable{
        Adresse adresse;
        private String email;
        private int telephone;
        private String url;
    }


    private class Adresse implements Serializable {
        private String adressePays;
        private String adresseVille;
        private int codePostal;
        private String adresseRue;
    }

    private class LieuStage implements Serializable {
        private Adresse adresse;
        private Geo geo;
        private int telephone;
        private String url;

        private class Geo implements Serializable {
            private long latitude;
            private long longitude;
        }
    }
}
