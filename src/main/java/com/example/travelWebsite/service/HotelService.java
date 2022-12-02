package com.example.travelWebsite.service;

import com.example.travelWebsite.request.HotelSearchRequest;
import com.example.travelWebsite.response.FlightResponse;
import com.example.travelWebsite.response.FlightSearchResponse;
import com.example.travelWebsite.response.HotelResponse;
import com.example.travelWebsite.response.HotelSearchResponse;
import org.springframework.stereotype.Service;

@Service
public interface HotelService {

    HotelSearchResponse showHotels(HotelSearchRequest hotelSearchRequest);

    HotelResponse getHotelDetails(Integer id) throws Exception;
}
