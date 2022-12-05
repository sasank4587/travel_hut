package com.example.travelWebsite.service;

import com.example.travelWebsite.collections.PromoCode;
import com.example.travelWebsite.request.AddPromoRequest;
import com.example.travelWebsite.request.ApplyPromoRequest;
import com.example.travelWebsite.request.PromoCodeRequest;
import com.example.travelWebsite.response.AppliedPromoResponse;
import com.example.travelWebsite.response.PromoListResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PromoCodeService {

    PromoListResponse showPromos(PromoCodeRequest promoCodeRequest);

    PromoCode getPromoDetails(Integer id);

    List<PromoCode> addPromoCode(AddPromoRequest addPromoRequest);

    List<PromoCode> getAllPromos(Integer id);

    AppliedPromoResponse applyPromoCode(ApplyPromoRequest applyPromoRequest);
}
