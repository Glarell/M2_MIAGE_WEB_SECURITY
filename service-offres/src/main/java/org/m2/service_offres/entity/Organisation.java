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
@Table(name="organisation")
public class Organisation implements Serializable {
    @Id
    private int idOrganisation;
    private String nomOrganisation;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="idAdresse",referencedColumnName = "idAdresse")
    Adresse adresse;
    private String email;
    private int telephone;
    private String url;
}
