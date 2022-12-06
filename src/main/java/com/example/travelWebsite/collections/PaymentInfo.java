package com.example.travelWebsite.collections;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "PAYMENT_INFO")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    @Column(name = "NAME_ON_CARD")
    private String nameOnTheCard;

    @Column(name = "CARD_NUMBER", unique = true)
    private String cardNumber;

    @Column(name = "CARD_EXPIRY_DATE")
    private Date cardExpiryDate;

    @Column(name = "SECURITY_CODE")
    private String securityCode;

    @Column(name = "IS_DEFAULT")
    private boolean isDefault;

    private String paymentName;
}
