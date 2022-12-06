package com.example.travelWebsite.collections;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table( name  = "FLIGHT_SCHEDULE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlightSchedule implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FLIGHT_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Flight flight;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "SOURCE_CITY_ID", nullable = false)
    @JsonIgnore
    private City sourceCity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "DESTINATION_CITY_ID", nullable = false)
    @JsonIgnore
    private City destinationCity;

    @Column(name = "PRICE")
    private double price;

    @Column(name = "START_DATE_TIME")
    private Date startDate;

    @Column(name = "START_TIME")
    private Time startTime;

    @Column(name = "END_DATE_TIME")
    private Date endDate;

    @Column(name = "END_TIME")
    private Time endTime;

    @Column(name = "AVAILABILITY")
    private Integer availability;

    @Column(name = "MILEAGE")
    private double mileage;

    @Column(name = "FLIGHT_REFERENCE", unique = true)
    private String flightReference;
}
