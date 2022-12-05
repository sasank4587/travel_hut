package com.example.travelWebsite.impl;

import com.example.travelWebsite.collections.City;
import com.example.travelWebsite.collections.PromoCode;
import com.example.travelWebsite.collections.User;
import com.example.travelWebsite.collections.UserPromoCode;
import com.example.travelWebsite.repository.CityRepository;
import com.example.travelWebsite.repository.PromoCodeRepository;
import com.example.travelWebsite.repository.UserPromoCodeRepository;
import com.example.travelWebsite.repository.UserRepository;
import com.example.travelWebsite.request.AddPromoRequest;
import com.example.travelWebsite.request.ApplyPromoRequest;
import com.example.travelWebsite.request.PromoCodeRequest;
import com.example.travelWebsite.response.AppliedPromoResponse;
import com.example.travelWebsite.response.PromoListResponse;
import com.example.travelWebsite.service.PromoCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PromoCodeServiceImpl implements PromoCodeService {

    @Autowired
    private PromoCodeRepository promoCodeRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPromoCodeRepository userPromoCodeRepository;

    @Override
    public PromoListResponse showPromos(PromoCodeRequest promoCodeRequest) {
        List<PromoCode> promoCodeList = promoCodeRepository.findAll();
        PromoListResponse promoListResponse = new PromoListResponse();
        promoCodeList = promoCodeList.stream().filter(promoCode -> checkEligibility(promoCode, promoCodeRequest)).collect(Collectors.toList());
        promoListResponse.setPromoCodeList(promoCodeList);
        return promoListResponse;
    }

    @Override
    public PromoCode getPromoDetails(Integer id) {
        Optional<PromoCode> promoCodeOptional = promoCodeRepository.findById(id);
        return promoCodeOptional.orElse(null);
    }

    @Override
    public List<PromoCode> addPromoCode(AddPromoRequest addPromoRequest) {
        Optional<PromoCode> promoCodeOptional = promoCodeRepository.findById(Integer.valueOf(addPromoRequest.getPromoId()));
        Optional<User> userOptional = userRepository.findById(Integer.valueOf(addPromoRequest.getUserId()));
        if (userOptional.isPresent() && promoCodeOptional.isPresent()) {
            User user = userOptional.get();
            if (addPromoRequest.getPaymentMethod().equalsIgnoreCase("redeem")){
                user.setTravelMileage(user.getTravelMileage()-promoCodeOptional.get().getMileage());
                user = userRepository.save(user);
            }
            UserPromoCode userPromoCode = new UserPromoCode();
            userPromoCode.setPromoCode(promoCodeOptional.get());
            userPromoCode.setUser(user);
            userPromoCodeRepository.save(userPromoCode);
            List<UserPromoCode> userPromoCodeList = userPromoCodeRepository.findByUser(user);
            return userPromoCodeList.stream().map(UserPromoCode::getPromoCode).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<PromoCode> getAllPromos(Integer id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()){
            List<UserPromoCode> userPromoCodeList = userPromoCodeRepository.findByUser(userOptional.get());
            return userPromoCodeList.stream().map(UserPromoCode::getPromoCode).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public AppliedPromoResponse applyPromoCode(ApplyPromoRequest applyPromoRequest) {
        AppliedPromoResponse appliedPromoResponse = new AppliedPromoResponse();
        Optional<User> userOptional = userRepository.findById(Integer.valueOf(applyPromoRequest.getUserId()));
        if (userOptional.isPresent()){
            List<UserPromoCode> userPromoCodeList = userPromoCodeRepository.findByUser(userOptional.get());
            List<PromoCode> promoCodeList = userPromoCodeList.stream().map(UserPromoCode::getPromoCode).collect(Collectors.toList());
            promoCodeList = promoCodeList.stream().filter(promoCode -> {
                return (promoCode.getPromoCodeName().equalsIgnoreCase(applyPromoRequest.getPromoCode()));
            }).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(promoCodeList)){
                PromoCode promoCode = promoCodeList.get(0);
                appliedPromoResponse.setValid(true);
                appliedPromoResponse.setPromoId(promoCode.getId());
                appliedPromoResponse.setPromoName(promoCode.getPromoCodeTitle());
                if(promoCode.getPromoCodeType().equalsIgnoreCase("voucher")){
                    appliedPromoResponse.setOfferedDiscount(promoCode.getPromoCodeValue());
                } else {
                    double discount = (appliedPromoResponse.getOfferedDiscount() * promoCode.getPromoCodeValue())/100.0;
                    if(discount>promoCode.getPromoCodeMaxValue()){
                        appliedPromoResponse.setOfferedDiscount(promoCode.getPromoCodeMaxValue());
                    } else {
                        appliedPromoResponse.setOfferedDiscount(discount);
                    }
                }

                return appliedPromoResponse;
            }
        }
        appliedPromoResponse.setValid(false);
        return appliedPromoResponse;
    }

    private boolean checkEligibility(PromoCode promoCode, PromoCodeRequest promoCodeRequest) {
        if (promoCode.getStartDate().before(promoCodeRequest.getStartDate()) && promoCode.getEndDate().after(promoCodeRequest.getReturnDate()) && promoCode.getSourceCity().getCityName().equalsIgnoreCase(promoCodeRequest.getSourceCity()) && promoCode.getDestinationCity().getCityName().equalsIgnoreCase(promoCodeRequest.getDestinationCity())) {
            return true;
        }

        return false;
    }
}
