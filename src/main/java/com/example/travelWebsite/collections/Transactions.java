package com.example.travelWebsite.collections;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table( name  = "TRANSACTIONS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transactions implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PAYMENT_INFO_ID", nullable = true)
    @JsonIgnore
    private PaymentInfo paymentInfo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    private double totalCost;

    private double totalCostPaid;

    private double tax;

    private LocalDateTime transactionDate;

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "PROMO_CODE_ID", nullable = true)
    @JsonIgnore
    private PromoCode promoCode;
}
