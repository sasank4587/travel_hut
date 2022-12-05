package com.example.travelWebsite.impl;

import com.example.travelWebsite.collections.FlightSchedule;
import com.example.travelWebsite.repository.CityRepository;
import com.example.travelWebsite.repository.FlightRepository;
import com.example.travelWebsite.repository.FlightScheduleRepository;
import com.example.travelWebsite.request.FlightCommandRequest;
import com.example.travelWebsite.request.FlightSearchRequest;
import com.example.travelWebsite.response.FlightResponse;
import com.example.travelWebsite.response.FlightSearchResponse;
import com.example.travelWebsite.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FlightServiceImpl implements FlightService {

    @Autowired
    private FlightScheduleRepository flightScheduleRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Override
    public FlightSearchResponse showFlights(FlightSearchRequest flightSearchRequest) {
        FlightCommandRequest flightCommandRequest = convertRequest(flightSearchRequest);
        List<FlightSchedule> flightScheduleList = flightScheduleRepository.findBySourceCityAndDestinationCityAndStartDate(flightCommandRequest.getSourceCity(),flightCommandRequest.getDestinationCity(),flightCommandRequest.getStartDate());
//        List<FlightSchedule> flightScheduleList = flightScheduleRepository.findAll();
        FlightSearchResponse flightSearchResponse = new FlightSearchResponse();
        flightSearchResponse.setFlightList(convertFlightResponse(flightScheduleList));
        if (flightCommandRequest.isReturn()) {
            List<FlightSchedule> returnFlightScheduleList = flightScheduleRepository.findBySourceCityAndDestinationCityAndStartDate(flightCommandRequest.getDestinationCity(),flightCommandRequest.getSourceCity(),flightCommandRequest.getReturnDate());
//            List<FlightSchedule> returnFlightScheduleList = flightScheduleRepository.findAll();
            flightSearchResponse.setReturnFlightList(convertFlightResponse(returnFlightScheduleList));
        }
        return flightSearchResponse;
    }

    @Override
    public FlightResponse getFlightDetails(Integer id) throws Exception {
        Optional<FlightSchedule> optionalFlightSchedule = flightScheduleRepository.findById(id);
        if (optionalFlightSchedule.isPresent()){
            FlightSchedule flightSchedule = optionalFlightSchedule.get();
            FlightResponse flightResponse = new FlightResponse();
            flightResponse.setFlightNumber(flightSchedule.getFlight().getFlightName());
            flightResponse.setPrice(flightSchedule.getPrice());
            flightResponse.setFlightScheduleId(flightSchedule.getId());
            flightResponse.setAirlinesName(flightSchedule.getFlight().getAirline());
            flightResponse.setFlightType(flightSchedule.getFlight().getFlightType());
            flightResponse.setFlightRating(flightSchedule.getFlight().getRating());
            flightResponse.setSourceCity(flightSchedule.getSourceCity().getCityName());
            flightResponse.setDestinationCity(flightSchedule.getDestinationCity().getCityName());
            flightResponse.setStartDate(flightSchedule.getStartDate());
            flightResponse.setEndDate(flightSchedule.getEndDate());
            flightResponse.setStartTime(flightSchedule.getStartTime());
            flightResponse.setEndTime(flightSchedule.getEndTime());
            flightResponse.setLogo(flightSchedule.getFlight().getLogo());
            return flightResponse;
        } else {
            throw new Exception();
        }
    }

    private List<FlightResponse> convertFlightResponse(List<FlightSchedule> flightScheduleList){
        List<FlightResponse> flightResponseList = new ArrayList<>();
        flightResponseList = flightScheduleList.stream().map(flightSchedule -> {
            FlightResponse flightResponse = new FlightResponse();
            flightResponse.setFlightNumber(flightSchedule.getFlight().getFlightName());
            flightResponse.setPrice(flightSchedule.getPrice());
            flightResponse.setFlightScheduleId(flightSchedule.getId());
            flightResponse.setAirlinesName(flightSchedule.getFlight().getAirline());
            flightResponse.setFlightType(flightSchedule.getFlight().getFlightType());
            flightResponse.setFlightRating(flightSchedule.getFlight().getRating());
            flightResponse.setSourceCity(flightSchedule.getSourceCity().getCityName());
            flightResponse.setDestinationCity(flightSchedule.getDestinationCity().getCityName());
            flightResponse.setStartDate(flightSchedule.getStartDate());
            flightResponse.setEndDate(flightSchedule.getEndDate());
            flightResponse.setLogo(flightSchedule.getFlight().getLogo());
            return flightResponse;
        }).collect(Collectors.toList());
        return flightResponseList;
    }

    private FlightCommandRequest convertRequest(FlightSearchRequest flightSearchRequest){
        FlightCommandRequest flightCommandRequest = new FlightCommandRequest();
        flightCommandRequest.setSourceCity(cityRepository.findByCityName(flightSearchRequest.getSourceCity()));
        flightCommandRequest.setDestinationCity(cityRepository.findByCityName(flightSearchRequest.getDestinationCity()));
        flightCommandRequest.setStartDate(flightSearchRequest.getStartDate());
        flightCommandRequest.setReturnDate(flightSearchRequest.getReturnDate());
        flightCommandRequest.setReturn(flightSearchRequest.isReturn());
        return flightCommandRequest;
    }
}
