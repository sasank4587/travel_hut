package com.example.travelWebsite.controller;

import com.example.travelWebsite.request.FlightSearchRequest;
import com.example.travelWebsite.response.ErrorResponse;
import com.example.travelWebsite.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path = "/flights")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @PostMapping(path = "/search")
    private ResponseEntity<?> showFLights(@RequestBody FlightSearchRequest flightSearchRequest){
        try {
            return new ResponseEntity<>(flightService.showFlights(flightSearchRequest), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Application has faced an issue"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{flightId}")
    private ResponseEntity<?> getFlightDetails(@PathVariable(value = "flightId") String id){
        try {
            return new ResponseEntity<>(flightService.getFlightDetails(Integer.valueOf(id)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Application has faced an issue"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/status/{flightId}")
    private ResponseEntity<?> getFlightStatus(@PathVariable(value = "flightId") String id){
        try {
            return new ResponseEntity<>(flightService.getFlightStatus(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Application has faced an issue"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
