package com.example.travelWebsite.impl;

import com.example.travelWebsite.collections.*;
import com.example.travelWebsite.repository.*;
import com.example.travelWebsite.request.TransactionRequest;
import com.example.travelWebsite.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private FlightScheduleRepository flightScheduleRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FlightBookingRepository flightBookingRepository;

    @Autowired
    private PaymentInfoRepository paymentInfoRepository;

    @Override
    public Transactions createTransaction(TransactionRequest transactionRequest) {
        Transactions transactions = new Transactions();
        Optional<User> userOptional = userRepository.findById(transactionRequest.getUserId());
        Optional<FlightSchedule> flightSchedule = flightScheduleRepository.findById(transactionRequest.getFlightId());
        Optional<FlightSchedule> returnFlightSchedule = flightScheduleRepository.findById(transactionRequest.getReturnFlightId());
        if (Objects.nonNull(transactionRequest.getFlightId()) || Objects.nonNull(transactionRequest.getReturnFlightId())){
            if (userOptional.isPresent()) {
                transactions.setUser(userOptional.get());
                transactions.setPaymentInfo(paymentInfoRepository.findByUserAndIsDefault(userOptional.get(),true));
                transactions.setTax(transactionRequest.getTaxPrice());
                transactions.setTotalCost(transactionRequest.getPrice());
                transactions.setTotalCostPaid(transactionRequest.getPaidPrice());
                transactions = transactionRepository.save(transactions);
            }
        }
        if (Objects.nonNull(transactions.getId())) {
            if (flightSchedule.isPresent()) {
                FlightBooking flightBooking = new FlightBooking();
                flightBooking.setFlightSchedule(flightSchedule.get());
                flightBooking.setCount(transactionRequest.getFlightPassengers());
                flightBooking.setTransactions(transactions);
                flightBookingRepository.save(flightBooking);
            }
            if (returnFlightSchedule.isPresent()) {
                FlightBooking flightBooking = new FlightBooking();
                flightBooking.setFlightSchedule(returnFlightSchedule.get());
                flightBooking.setCount(transactionRequest.getReturnFlightPassengers());
                flightBooking.setTransactions(transactions);
                flightBookingRepository.save(flightBooking);
            }
        }

        return transactions;
    }
}
