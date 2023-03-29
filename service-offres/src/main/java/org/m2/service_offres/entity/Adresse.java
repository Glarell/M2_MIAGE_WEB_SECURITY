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
@Table(name="adresse")
public class Adresse implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idAdresse;
    private String adressePays;
    private String adresseVille;
    private int codePostal;
    private String adresseRue;

    public boolean verify(){
        if (this.adressePays.length() == 0){
            return false;
        }
        if (this.adresseVille.length() == 0){
            return false;
        }
        return this.adresseRue.length() != 0;
    }
}
