package com.example.travelWebsite.controller;

import com.example.travelWebsite.collections.User;
import com.example.travelWebsite.exception.PaymentMethodAlreadyExists;
import com.example.travelWebsite.exception.UserAlreadyExists;
import com.example.travelWebsite.exception.UserDoesNotExist;
import com.example.travelWebsite.request.AddFeedbackRequest;
import com.example.travelWebsite.request.AuthenticationRequest;
import com.example.travelWebsite.request.PaymentRequest;
import com.example.travelWebsite.request.UserRequest;
import com.example.travelWebsite.response.ErrorResponse;
import com.example.travelWebsite.service.UserService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    private ResponseEntity<?> addUser(@RequestBody UserRequest userRequest){
        try {
            return new ResponseEntity<>(userService.addUser(userRequest), HttpStatus.OK);
        } catch (UserAlreadyExists userAlreadyExists){
            return new ResponseEntity<>(new ErrorResponse("Given username already exists"),
                    HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Application has faced an issue"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/{userId}/addPayment")
    private ResponseEntity<?> addPaymentMethod(@RequestBody PaymentRequest paymentRequest, @PathVariable(value = "userId")Integer id){
        try {
            return new ResponseEntity<>(userService.addPaymentMethod(paymentRequest, id), HttpStatus.OK);
        } catch (UserDoesNotExist userDoesNotExist){
            return new ResponseEntity<>(new ErrorResponse("Given username does not exist."),
                    HttpStatus.NOT_FOUND);
        } catch (PaymentMethodAlreadyExists paymentMethodAlreadyExists){
            return new ResponseEntity<>(new ErrorResponse("Given Payment method already exists"),
                    HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Application has faced an issue."),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{userId}/{paymentId}")
    private ResponseEntity<?> makeDefault(@PathVariable(value = "paymentId")Integer paymentId, @PathVariable(value = "userId")Integer id){
        try {
            return new ResponseEntity<>(userService.makePaymentDefault(paymentId, id), HttpStatus.OK);
        } catch (UserDoesNotExist userDoesNotExist){
            return new ResponseEntity<>(new ErrorResponse("Given username does not exist."),
                    HttpStatus.NOT_FOUND);
        } catch (PaymentMethodAlreadyExists paymentMethodAlreadyExists){
            return new ResponseEntity<>(new ErrorResponse("Given Payment method already exists"),
                    HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Application has faced an issue."),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{userId}/profile")
    private ResponseEntity<?> getUserProfileDetails(@PathVariable Integer userId){
        try {
            return new ResponseEntity<>(userService.getProfileDetails(userId), HttpStatus.OK);
        } catch (UserDoesNotExist userDoesNotExist){
            return new ResponseEntity<>(new ErrorResponse("Given username does not exist."),
                    HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Application has faced an issue."),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{userId}/payments")
    private ResponseEntity<?> getUserPaymentDetails(@PathVariable Integer userId){
        try {
            return new ResponseEntity<>(userService.getPaymentDetails(userId), HttpStatus.OK);
        } catch (UserDoesNotExist userDoesNotExist){
            return new ResponseEntity<>(new ErrorResponse("Given username does not exist."),
                    HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Application has faced an issue."),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateClient(@RequestBody AuthenticationRequest authenticationRequest){
        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();
        try {
            userService.authenticate(authenticationRequest);
        }catch(Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Bad Credentials "+username),HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(userService.findByUsername(username),HttpStatus.OK);
    }

    @PostMapping("/feedback")
    public ResponseEntity<?> addFeedback(@RequestBody AddFeedbackRequest addFeedbackRequest){
        try {
            return new ResponseEntity<>(userService.addFeedback(addFeedbackRequest),HttpStatus.OK);
        }catch(Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Application has faced an issue."),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping
//    private List<User> getAllUsers(){
//        return null;
//    }
}
