package org.m2.service_offres.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="candidature")
public class Candidature implements Serializable {
    @Id
    private int idCandidature;
    //@OneToOne(cascade = CascadeType.ALL)
    //@JoinColumn(name="idPersonne", referencedColumnName = "idPersonne")
    private int idPersonne;
    //@OneToOne(cascade = CascadeType.ALL)
    //@JoinColumn(name="idOffre", referencedColumnName = "idOffre")
    private int idOffre;
    private String dateCandidature;

    private boolean isActive;
}
