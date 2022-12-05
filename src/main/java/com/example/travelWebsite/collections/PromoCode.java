package com.example.travelWebsite.collections;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table( name  = "PROMO_CODE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PromoCode implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "PROMO_CODE_USAGE")
    private String promoCodeUsage;

    @Column(name = "PROMO_CODE_TYPE")
    private String promoCodeType;

    @Column(name = "PROMO_CODE_TITLE")
    private String promoCodeTitle;

    @Column(name = "PROMO_CODE_NAME")
    private String promoCodeName;

    @Column(name = "PROMO_CODE_VALUE")
    private double promoCodeValue;

    @Column(name = "PROMO_CODE_MAX_VALUE")
    private double promoCodeMaxValue;

    @Column(name = "TERMS_AND_CONDITIONS")
    private String termsAndConditions;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "SOURCE_CITY_ID", nullable = false)
    @JsonIgnore
    private City sourceCity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "DESTINATION_CITY_ID", nullable = false)
    @JsonIgnore
    private City destinationCity;

    @Column(name = "START_DATE_TIME")
    private Date startDate;

    @Column(name = "END_DATE_TIME")
    private Date endDate;

    private double price;

    private double mileage;
}
