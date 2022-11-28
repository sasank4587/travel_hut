package com.example.travelWebsite.request;

import com.example.travelWebsite.collections.City;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlightCommandRequest {

    private City sourceCity;
    private City destinationCity;
    private Date startDate;
    private Date returnDate;
    //private Integer numberOfPassengers;
    private boolean isReturn;
}
