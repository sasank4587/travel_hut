package com.example.travelWebsite.repository;

import com.example.travelWebsite.collections.Hotel;
import com.example.travelWebsite.collections.HotelRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRoomRepository extends JpaRepository<HotelRoom, Integer> {

    List<HotelRoom> findByHotel(Hotel hotel);
}
