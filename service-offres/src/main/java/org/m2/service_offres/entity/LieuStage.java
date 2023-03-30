package org.m2.service_offres.entity;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

//@Embeddable
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lieustage")
public class LieuStage implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idLieuStage;
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "idAdresse", referencedColumnName = "idAdresse")
    private Adresse adresse;
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "idGeo", referencedColumnName = "idGeo")
    private Geo geo;
    private int telephone;
    private String url;

    public boolean verify() {
        return url.length() != 0;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this, LieuStage.class);
    }
}
