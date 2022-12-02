package com.example.travelWebsite.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HotelSearchResponse {
    List<HotelResponse> hotelResponseList;
    long numberOfDays;
}
