package com.example.travelWebsite.response;

import com.example.travelWebsite.collections.PromoCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromoListResponse {
    private List<PromoCode> promoCodeList;
}
