package com.example.travelWebsite.collections;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "USER")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "EMAIL_ID", unique = true)
    private String emailId;

    @Column(name = "USER_ADDRESS")
    private String userAddress;

    @Column(name = "TRAVEL_MILEAGE")
    private double travelMileage;

    @Column(name = "USERNAME", unique = true)
    private String userName;

    @Column(name = "USER_PASS")
    private String password;

}
