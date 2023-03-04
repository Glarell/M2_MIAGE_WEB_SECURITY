package org.m2.services_personnes.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
    private int idPersonne;
    private String nom;
    private String prenom;
    @OneToMany(targetEntity = Candidature.class, mappedBy = "idPersonne")
    private List<Candidature> candidatures = new ArrayList<>();
}
