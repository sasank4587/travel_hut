package com.example.travelWebsite.repository;

import com.example.travelWebsite.collections.Transactions;
import com.example.travelWebsite.collections.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transactions, Integer> {

    List<Transactions> findByUser(User user);
}
