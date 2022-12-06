package com.example.travelWebsite.service;

import com.example.travelWebsite.exception.PaymentMethodAlreadyExists;
import com.example.travelWebsite.exception.UserAlreadyExists;
import com.example.travelWebsite.exception.UserDoesNotExist;
import com.example.travelWebsite.request.AddFeedbackRequest;
import com.example.travelWebsite.request.AuthenticationRequest;
import com.example.travelWebsite.request.PaymentRequest;
import com.example.travelWebsite.request.UserRequest;
import com.example.travelWebsite.response.PaymentMethods;
import com.example.travelWebsite.response.UserProfileResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    UserProfileResponse addUser(UserRequest userRequest) throws UserAlreadyExists;

    List<PaymentMethods> addPaymentMethod(PaymentRequest paymentRequest, Integer userId) throws UserDoesNotExist, PaymentMethodAlreadyExists;

    List<PaymentMethods> makePaymentDefault(Integer paymentId, Integer id) throws PaymentMethodAlreadyExists, UserDoesNotExist;

    UserProfileResponse getProfileDetails(Integer userId) throws UserDoesNotExist;

    List<PaymentMethods> getPaymentDetails(Integer userId) throws UserDoesNotExist;

    void authenticate(AuthenticationRequest authenticationRequest) throws Exception;

    UserProfileResponse findByUsername(String username);

    boolean addFeedback(AddFeedbackRequest addFeedbackRequest);
//    List<UserProfileResponse> getAllUsers();
}
