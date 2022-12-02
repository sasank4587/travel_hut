package com.example.travelWebsite.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HotelRoomResponse {
    private Integer hotelRoomId;
    private double price;
    private String amenities;
    private String roomType;
}
