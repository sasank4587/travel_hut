package com.example.travelWebsite.collections;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "CITIES")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class City implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "CITY_NAME")
    private String cityName;

    @Column(name = "CITY_COUNTRY")
    private String country;
}
