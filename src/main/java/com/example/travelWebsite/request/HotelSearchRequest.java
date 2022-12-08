package com.example.travelWebsite.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HotelSearchRequest {

    private String sourceCity;
    private Date checkInDate;
    private Date checkOutDate;
    private String franchiseName;
}
