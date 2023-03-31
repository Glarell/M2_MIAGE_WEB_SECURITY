package org.m2.services_personnes.entity;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "candidature")
public class Candidature implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idCandidature;
    //@OneToOne(cascade = CascadeType.ALL)
    //@JoinColumn(name="idPersonne", referencedColumnName = "idPersonne")
    private int idPersonne;
    //@OneToOne(cascade = CascadeType.ALL)
    //@JoinColumn(name="idOffre", referencedColumnName = "idOffre")
    private int idOffre;
    private String dateCandidature;
    private String state;
    private boolean isActive;


    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this, Candidature.class);
    }

    public boolean verifyState() {
        return this.state.equals("en attente") || this.state.equals("en cours") || this.state.equals("acceptee") || this.state.equals("refusee");
    }
}
