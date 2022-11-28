package com.example.travelWebsite.repository;

import com.example.travelWebsite.collections.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
    City findByCityName(String cityName);
}
