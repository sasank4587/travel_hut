package com.example.travelWebsite.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileResponse {

    private Integer id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String emailId;
    private String userAddress;
    private double travelMileage;
    private String userName;
}
