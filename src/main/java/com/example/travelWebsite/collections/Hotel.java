package com.example.travelWebsite.collections;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table( name  = "HOTEL")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Hotel implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "HOTEL_NAME")
    private String hotelName;

    @Column(name = "HOTEL_ADDRESS")
    private String hotelAddress;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CITY_ID", nullable = false)
    @JsonIgnore
    private City city;

    @Column(name = "RATING")
    private double rating;

    @Column(name = "HOTEL_TYPE")
    private String hotelType;

    @Column(name = "FRANCHISE_NAME")
    private String franchiseName;
}
