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
public class TransactionRequest {
    private Integer userId;
    private Integer flightId;
    private Integer flightPassengers;
    private Integer returnFlightId;
    private Integer returnFlightPassengers;
    private Integer hotelRoomId;
    private Integer numberOfHotelRooms;
    private Date checkInDate;
    private Date checkOutDate;
    private double price;
    private double taxPrice;
    private Integer promoCode;
    private double paidPrice;
    private double remainingMileage;
    private double discountPrice;
    private double redeemedPrice;
    private double offerPrice;
    private Integer paymentId;
}
