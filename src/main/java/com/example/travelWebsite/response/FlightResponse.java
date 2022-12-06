package com.example.travelWebsite.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlightResponse {
    private Integer flightScheduleId;
    private double price;
    private String flightNumber;
    private String airlinesName;
    private String flightType;
    private double flightRating;
    private String sourceCity;
    private String destinationCity;
    private String logo;
    private Date startDate;
    private Time startTime;
    private Date endDate;
    private Time endTime;
    private double miles;
    private String flightReference;

}
