package com.example.travelWebsite.repository;

import com.example.travelWebsite.collections.FlightBooking;
import com.example.travelWebsite.collections.HotelBooking;
import com.example.travelWebsite.collections.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelBookingRepository extends JpaRepository<HotelBooking, Integer> {

    List<HotelBooking> findByTransactions(Transactions transactions);
}
