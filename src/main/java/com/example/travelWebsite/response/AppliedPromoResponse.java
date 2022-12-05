package com.example.travelWebsite.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppliedPromoResponse {
    private boolean isValid;
    private double offeredDiscount;
    private String promoName;
    private Integer promoId;
}
