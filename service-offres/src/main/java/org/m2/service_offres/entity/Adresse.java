package org.m2.service_offres.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="adresse")
public class Adresse implements Serializable {
    @Id
    private int idAdresse;
    private String adressePays;
    private String adresseVille;
    private int codePostal;
    private String adresseRue;
}
