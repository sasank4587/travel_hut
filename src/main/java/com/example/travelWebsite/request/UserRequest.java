package com.example.travelWebsite.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String emailId;
    private String userAddress;
    private String userName;
    private String password;
    private String cardNumber;
    private String nameOnTheCard;
    private Date expiryDate;
    private String securityCode;
    private boolean isDefault;
    private String paymentName;
}
