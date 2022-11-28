package com.example.travelWebsite.repository;

import com.example.travelWebsite.collections.PaymentInfo;
import com.example.travelWebsite.collections.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentInfoRepository extends JpaRepository<PaymentInfo, Integer> {

    PaymentInfo findByUserAndIsDefault(User user, boolean value);

    List<PaymentInfo> findByUser(User user);

    PaymentInfo findByUserAndCardNumber(User user, String cardNumber);
}
