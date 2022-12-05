package com.example.travelWebsite.repository;

import com.example.travelWebsite.collections.User;
import com.example.travelWebsite.collections.UserPromoCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPromoCodeRepository extends JpaRepository<UserPromoCode, Integer> {

    List<UserPromoCode> findByUser(User user);
}
