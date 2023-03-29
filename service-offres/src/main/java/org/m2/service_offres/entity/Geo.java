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
@Table(name="geo")
public class Geo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idGeo;
    private long latitude;
    private long longitude;

    public boolean verify(){
        return (this.latitude != 0 && this.longitude != 0);
    }
}
