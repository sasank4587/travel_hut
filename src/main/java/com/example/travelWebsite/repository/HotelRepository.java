package com.example.travelWebsite.repository;

import com.example.travelWebsite.collections.City;
import com.example.travelWebsite.collections.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {

    List<Hotel> findByCity(City city);
}
