package com.example.travelWebsite.request;

import com.example.travelWebsite.collections.City;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlightSearchRequest {

    private String sourceCity;
    private String destinationCity;
    private Date startDate;
    private Date returnDate;
//    private Integer numberOfPassengers;
    private boolean isReturn;
}
