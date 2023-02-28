package org.m2.service_offres.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
//@Embeddable
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="lieustage")
public class LieuStage implements Serializable {
    @Id
    private int idLieuStage;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="idAdresse", referencedColumnName = "idAdresse")
    private Adresse adresse;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="idGeo", referencedColumnName = "idGeo")
    private Geo geo;
    private int telephone;
    private String url;
}
