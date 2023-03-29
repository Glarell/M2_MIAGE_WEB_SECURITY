package org.m2.services_personnes.entity;

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
@Table(name="personne")
public class Personne implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idPersonne;
    private String nom;
    private String prenom;
    @OneToMany(targetEntity = Candidature.class, mappedBy = "idPersonne")
    private List<Candidature> candidatures = new ArrayList<>();

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this, Personne.class);
    }
}
