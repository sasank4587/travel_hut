package com.example.travelWebsite.repository;

import com.example.travelWebsite.collections.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Integer> {
}
