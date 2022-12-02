package com.example.travelWebsite.controller;

import com.example.travelWebsite.request.FlightSearchRequest;
import com.example.travelWebsite.request.TransactionRequest;
import com.example.travelWebsite.response.ErrorResponse;
import com.example.travelWebsite.service.FlightService;
import com.example.travelWebsite.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path = "/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping(path = "/add")
    private ResponseEntity<?> addTransaction(@RequestBody TransactionRequest transactionRequest){
        try {
            return new ResponseEntity<>(transactionService.createTransaction(transactionRequest), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Application has faced an issue"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{userId}")
    private ResponseEntity<?> getAllTransactions(@PathVariable(value = "userId") Integer userId){
        try {
            return new ResponseEntity<>(transactionService.getAllTransactionsForUser(userId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Application has faced an issue"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
