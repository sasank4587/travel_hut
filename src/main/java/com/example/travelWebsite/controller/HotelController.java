package com.example.travelWebsite.controller;

import com.example.travelWebsite.request.FlightSearchRequest;
import com.example.travelWebsite.request.HotelSearchRequest;
import com.example.travelWebsite.response.ErrorResponse;
import com.example.travelWebsite.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path = "/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PostMapping(path = "/search")
    private ResponseEntity<?> showHotels(@RequestBody HotelSearchRequest hotelSearchRequest){
        try {
            return new ResponseEntity<>(hotelService.showHotels(hotelSearchRequest), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Application has faced an issue"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{hotelId}")
    private ResponseEntity<?> getHotelDetails(@PathVariable(value = "hotelId") String id){
        try {
            return new ResponseEntity<>(hotelService.getHotelDetails(Integer.valueOf(id)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Application has faced an issue"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
