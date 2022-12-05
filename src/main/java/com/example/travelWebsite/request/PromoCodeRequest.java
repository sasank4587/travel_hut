package com.example.travelWebsite.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PromoCodeRequest {

    private String sourceCity;
    private String destinationCity;
    private Date startDate;
    private Date returnDate;
    private double maximumRange;
    private double minimumRange;
}
