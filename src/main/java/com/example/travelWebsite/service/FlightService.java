package com.example.travelWebsite.service;

import com.example.travelWebsite.collections.Flight;
import com.example.travelWebsite.request.FlightSearchRequest;
import com.example.travelWebsite.response.FlightResponse;
import com.example.travelWebsite.response.FlightSearchResponse;
import com.example.travelWebsite.response.FlightStatusResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FlightService {

    FlightSearchResponse showFlights(FlightSearchRequest flightSearchRequest);

    FlightResponse getFlightDetails(Integer id) throws Exception;

    FlightStatusResponse getFlightStatus(String flightNumber);
}
