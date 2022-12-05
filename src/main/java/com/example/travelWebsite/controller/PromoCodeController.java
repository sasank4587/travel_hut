package com.example.travelWebsite.controller;

import com.example.travelWebsite.request.AddPromoRequest;
import com.example.travelWebsite.request.ApplyPromoRequest;
import com.example.travelWebsite.request.FlightSearchRequest;
import com.example.travelWebsite.request.PromoCodeRequest;
import com.example.travelWebsite.response.ErrorResponse;
import com.example.travelWebsite.service.PromoCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path = "/promo")
public class PromoCodeController {

    @Autowired
    private PromoCodeService promoCodeService;

    @PostMapping(path = "/search")
    private ResponseEntity<?> showPromos(@RequestBody PromoCodeRequest promoCodeRequest){
        try {
            return new ResponseEntity<>(promoCodeService.showPromos(promoCodeRequest), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Application has faced an issue"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{promoId}")
    private ResponseEntity<?> getFlightDetails(@PathVariable(value = "promoId") String id){
        try {
            return new ResponseEntity<>(promoCodeService.getPromoDetails(Integer.valueOf(id)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Application has faced an issue"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/add")
    private ResponseEntity<?> addPromo(@RequestBody AddPromoRequest addPromoRequest){
        try {
            return new ResponseEntity<>(promoCodeService.addPromoCode(addPromoRequest), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Application has faced an issue"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/user/{userId}")
    private ResponseEntity<?> getAllPromos(@PathVariable(value = "userId") String id){
        try {
            return new ResponseEntity<>(promoCodeService.getAllPromos(Integer.valueOf(id)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Application has faced an issue"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/apply")
    private ResponseEntity<?> applyPromo(@RequestBody ApplyPromoRequest applyPromoRequest){
        try {
            return new ResponseEntity<>(promoCodeService.applyPromoCode(applyPromoRequest), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Application has faced an issue"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
