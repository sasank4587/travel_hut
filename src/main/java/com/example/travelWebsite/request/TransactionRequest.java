package com.example.travelWebsite.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {
    private Integer userId;
    private Integer flightId;
    private Integer flightPassengers;
    private Integer returnFlightId;
    private Integer returnFlightPassengers;
    private double price;
    private double taxPrice;
    private Integer promoCode;
    private double paidPrice;
}
