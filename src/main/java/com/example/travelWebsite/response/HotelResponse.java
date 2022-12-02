package com.example.travelWebsite.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HotelResponse {
    private Integer hotelId;
    private String franchiseName;
    private String hotelAddress;
    private String hotelName;
    private String hotelType;
    private double rating;
    private String location;
    private String logo;
    private List<HotelRoomResponse> hotelRoomsList;
}
