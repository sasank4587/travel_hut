package com.example.travelWebsite.repository;

import com.example.travelWebsite.collections.City;
import com.example.travelWebsite.collections.FlightSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface FlightScheduleRepository extends JpaRepository<FlightSchedule, Integer> {

    List<FlightSchedule> findBySourceCityAndDestinationCityAndStartDate(City sourceCity, City destinationCity, Date startDate);

    List<FlightSchedule> findBySourceCity(City sourceCity);

    List<FlightSchedule> findBySourceCityAndDestinationCity(City sourceCity, City destinationCity);

    FlightSchedule findByFlightReference(String reference);
}
