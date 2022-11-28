package com.example.travelWebsite.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethods {
    private String cardNumber;
    private String nameOnTheCard;
    private Date expiryDate;
    private String securityCode;
    private boolean isDefault;
}
