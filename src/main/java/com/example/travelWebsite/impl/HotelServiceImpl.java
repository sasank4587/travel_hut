package com.example.travelWebsite.impl;

import com.example.travelWebsite.collections.City;
import com.example.travelWebsite.collections.Hotel;
import com.example.travelWebsite.collections.HotelRoom;
import com.example.travelWebsite.repository.CityRepository;
import com.example.travelWebsite.repository.HotelRepository;
import com.example.travelWebsite.repository.HotelRoomRepository;
import com.example.travelWebsite.request.HotelSearchRequest;
import com.example.travelWebsite.response.*;
import com.example.travelWebsite.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HotelRoomRepository hotelRoomRepository;

    @Override
    public HotelSearchResponse showHotels(HotelSearchRequest hotelSearchRequest) {
        long number  = ChronoUnit.DAYS.between(hotelSearchRequest.getCheckInDate().toLocalDate(), hotelSearchRequest.getCheckOutDate().toLocalDate());
        HotelSearchResponse hotelSearchResponse = new HotelSearchResponse();
        City city = cityRepository.findByCityName(hotelSearchRequest.getSourceCity());
        List<Hotel> hotels = hotelRepository.findByCity(city);
        List<HotelResponse> hotelResponses = hotels.stream().map(this::convertHotelResponse).collect(Collectors.toList());
        hotelSearchResponse.setHotelResponseList(hotelResponses);
        hotelSearchResponse.setNumberOfDays(number);
        return hotelSearchResponse;
    }

    @Override
    public HotelResponse getHotelDetails(Integer id) throws Exception {
        Optional<HotelRoom> optionalHotelRoom = hotelRoomRepository.findById(id);
        if(optionalHotelRoom.isPresent()){
            HotelRoom hotelRoom = optionalHotelRoom.get();
            HotelRoomResponse hotelRoomResponse = convertHotelRoomResponse(hotelRoom);
            HotelResponse hotelResponse = new HotelResponse();
            Hotel hotel = hotelRoom.getHotel();
            hotelResponse.setHotelId(hotel.getId());
            hotelResponse.setFranchiseName(hotel.getFranchiseName());
            hotelResponse.setHotelAddress(hotel.getHotelAddress());
            hotelResponse.setHotelName(hotel.getHotelName());
            hotelResponse.setHotelType(hotel.getHotelType());
            hotelResponse.setRating(hotel.getRating());
            hotelResponse.setLocation(hotel.getCity().getCityName()+" , "+hotel.getCity().getCountry());
            hotelResponse.setHotelRoomsList(new ArrayList<HotelRoomResponse>(Arrays.asList(hotelRoomResponse)));
            return hotelResponse;
        }
        throw new Exception();
    }

    private HotelResponse convertHotelResponse(Hotel hotel) {
        HotelResponse hotelResponse = new HotelResponse();
        List<HotelRoom> hotelRooms = hotelRoomRepository.findByHotel(hotel);
        List<HotelRoomResponse> hotelRoomResponses = hotelRooms.stream().map(this::convertHotelRoomResponse).collect(Collectors.toList());
        hotelResponse.setHotelId(hotel.getId());
        hotelResponse.setFranchiseName(hotel.getFranchiseName());
        hotelResponse.setHotelAddress(hotel.getHotelAddress());
        hotelResponse.setHotelName(hotel.getHotelName());
        hotelResponse.setHotelType(hotel.getHotelType());
        hotelResponse.setRating(hotel.getRating());
        hotelResponse.setLocation(hotel.getCity().getCityName()+" , "+hotel.getCity().getCountry());
        hotelResponse.setHotelRoomsList(hotelRoomResponses);
        return hotelResponse;
    }

    private HotelRoomResponse convertHotelRoomResponse(HotelRoom hotelRoom) {
        HotelRoomResponse hotelRoomResponse = new HotelRoomResponse();
        hotelRoomResponse.setHotelRoomId(hotelRoom.getId());
        hotelRoomResponse.setRoomType(hotelRoom.getRoomType());
        hotelRoomResponse.setAmenities(hotelRoom.getAmenities());
        hotelRoomResponse.setPrice(hotelRoom.getPrice());
        return hotelRoomResponse;
    }
}
