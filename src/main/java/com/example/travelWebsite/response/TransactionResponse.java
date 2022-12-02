package com.example.travelWebsite.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {
    private Integer transactionId;
    private LocalDateTime transactionDate;
    private UserProfileResponse userProfileResponse;
    private double totalCost;
    private double tax;
    private Integer numberOfPassengers;
    private Integer returnPassengers;
    private Integer hotelRooms;
    private Date hotelCheckInDate;
    private Date hotelCheckOutDate;
    private long hotelNumberOfDays;
    private double totalCostPaid;
    private boolean isFlight;
    private boolean isReturnFlight;
    private boolean isHotel;
    private FlightResponse flightResponse;
    private FlightResponse returnFlightResponse;
    private HotelResponse hotelResponse;
}
