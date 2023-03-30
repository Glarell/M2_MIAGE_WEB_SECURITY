package org.m2.service_offres.entity;

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
@Table(name = "organisation")
public class Organisation implements Serializable {

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "idAdresse", referencedColumnName = "idAdresse")
    Adresse adresse;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idOrganisation;
    private String nomOrganisation;
    private String email;
    private String telephone;
    private String url;

    public boolean verify() {
        if (this.nomOrganisation.length() == 0) {
            return false;
        }
        if (this.email.length() == 0) {
            return false;
        }
        if (this.telephone.length() == 0) {
            return false;
        }
        return this.url.length() != 0;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this, Organisation.class);
    }
}
