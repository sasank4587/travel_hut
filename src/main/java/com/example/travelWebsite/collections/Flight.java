package com.example.travelWebsite.collections;

import com.example.travelWebsite.collections.enums.FlightType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table( name  = "FLIGHT")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Flight implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "FLIGHT_NUMBER", unique = true)
    private String flightName;

    @Column(name = "LOGO")
    private String logo;

    @Column(name = "AIRLINE_NAME")
    private String airline;

    @Column(name = "FLIGHT_TYPE")
    private String flightType;

    @Column(name = "RATING")
    private double rating;

    @Column(name = "MAX_CAPACITY")
    private Integer maximumCapacity;
}
