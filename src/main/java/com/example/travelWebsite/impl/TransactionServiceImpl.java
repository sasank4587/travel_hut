package com.example.travelWebsite.impl;

import com.example.travelWebsite.collections.*;
import com.example.travelWebsite.repository.*;
import com.example.travelWebsite.request.TransactionRequest;
import com.example.travelWebsite.response.*;
import com.example.travelWebsite.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private FlightScheduleRepository flightScheduleRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FlightBookingRepository flightBookingRepository;

    @Autowired
    private PaymentInfoRepository paymentInfoRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HotelRoomRepository hotelRoomRepository;

    @Autowired
    private HotelBookingRepository hotelBookingRepository;

    @Autowired
    private PromoCodeRepository promoCodeRepository;

    @Override
    public Transactions createTransaction(TransactionRequest transactionRequest) {
        Transactions transactions = new Transactions();
        Optional<User> userOptional = userRepository.findById(transactionRequest.getUserId());
        User user = new User();
        if (Objects.nonNull(transactionRequest.getFlightId()) || Objects.nonNull(transactionRequest.getReturnFlightId()) || Objects.nonNull(transactionRequest.getHotelRoomId())){
            if (userOptional.isPresent()) {
                if(Objects.nonNull(transactionRequest.getPromoCode())){
                    Optional<PromoCode> promoCodeOptional = promoCodeRepository.findById(transactionRequest.getPromoCode());
                    if (promoCodeOptional.isPresent()) {
                        transactions.setPromoCode(promoCodeOptional.get());
                    }
                }
                Optional<PaymentInfo> paymentInfoOptional = paymentInfoRepository.findById(transactionRequest.getPaymentId());
                if (paymentInfoOptional.isPresent()) {
                    user = userOptional.get();
                    user.setTravelMileage(transactionRequest.getRemainingMileage());
                    user = userRepository.save(user);
                    transactions.setUser(user);
                    transactions.setPaymentInfo(paymentInfoOptional.get());
                    transactions.setTax(transactionRequest.getTaxPrice());
                    transactions.setDiscountPrice(transactionRequest.getDiscountPrice());
                    transactions.setRedeemedPrice(transactionRequest.getRedeemedPrice());
                    transactions.setOfferPrice(transactionRequest.getOfferPrice());
                    transactions.setTotalCost(transactionRequest.getPrice());
                    transactions.setTotalCostPaid(transactionRequest.getPaidPrice());
                    transactions.setTransactionDate(LocalDateTime.now());
                    transactions = transactionRepository.save(transactions);
                }
            }
        }
        if (Objects.nonNull(transactions.getId())) {
            double mileage = user.getTravelMileage();
            if (Objects.nonNull(transactionRequest.getFlightId())) {
                Optional<FlightSchedule> flightSchedule = flightScheduleRepository.findById(transactionRequest.getFlightId());
                if (flightSchedule.isPresent()) {
                    mileage += flightSchedule.get().getMileage();
                    FlightBooking flightBooking = new FlightBooking();
                    flightBooking.setFlightSchedule(flightSchedule.get());
                    flightBooking.setCount(transactionRequest.getFlightPassengers());
                    flightBooking.setTransactions(transactions);
                    flightBookingRepository.save(flightBooking);
                }
            }
            if (Objects.nonNull(transactionRequest.getReturnFlightId())) {
                Optional<FlightSchedule> returnFlightSchedule = flightScheduleRepository.findById(transactionRequest.getReturnFlightId());
                if (returnFlightSchedule.isPresent()) {
                    mileage += returnFlightSchedule.get().getMileage();
                    FlightBooking flightBooking = new FlightBooking();
                    flightBooking.setFlightSchedule(returnFlightSchedule.get());
                    flightBooking.setCount(transactionRequest.getReturnFlightPassengers());
                    flightBooking.setTransactions(transactions);
                    flightBookingRepository.save(flightBooking);
                }
            }
            if (Objects.nonNull(transactionRequest.getHotelRoomId())) {
                Optional<HotelRoom> optionalHotelRoom = hotelRoomRepository.findById(transactionRequest.getHotelRoomId());
                if (optionalHotelRoom.isPresent()) {
                    HotelBooking hotelBooking = new HotelBooking();
                    hotelBooking.setHotelRoom(optionalHotelRoom.get());
                    hotelBooking.setStartDate(transactionRequest.getCheckInDate());
                    hotelBooking.setEndDate(transactionRequest.getCheckOutDate());
                    hotelBooking.setCount(transactionRequest.getNumberOfHotelRooms());
                    hotelBooking.setTransactions(transactions);
                    hotelBookingRepository.save(hotelBooking);
                }
            }

            user.setTravelMileage(mileage);
            userRepository.save(user);
        }

        return transactions;
    }

    @Override
    public TransactionList getAllTransactionsForUser(Integer id) {
        Optional<User> userOptional = userRepository.findById(id);
        TransactionList transactionListResponse = new TransactionList();
        List<TransactionResponse> transactionResponseList = new ArrayList<>();
        if(userOptional.isPresent()){
            User user = userOptional.get();
            List<Transactions> transactionsList = transactionRepository.findByUser(user);
            transactionResponseList = transactionsList.stream().map(this::convertTransaction).collect(Collectors.toList());
            transactionListResponse.setTransactionResponses(transactionResponseList);
        }
        return transactionListResponse;
    }

    private TransactionResponse convertTransaction(Transactions transaction){
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setTransactionId(transaction.getId());
        transactionResponse.setTax(transaction.getTax());
        transactionResponse.setTransactionDate(transaction.getTransactionDate());
        transactionResponse.setTotalCost(transaction.getTotalCost());
        transactionResponse.setTotalCostPaid(transaction.getTotalCostPaid());
        FlightResponse flightResponse = new FlightResponse();
        FlightResponse returnFlightResponse = new FlightResponse();
        List<FlightBooking> flightBookingList = flightBookingRepository.findByTransactions(transaction);
        List<HotelBooking> hotelBookingList = hotelBookingRepository.findByTransactions(transaction);
        if(!CollectionUtils.isEmpty(flightBookingList)){
            if (flightBookingList.size() == 1){
                transactionResponse.setFlight(true);
                flightResponse = convertFlightResponse(flightBookingList.get(0));
                transactionResponse.setNumberOfPassengers(flightBookingList.get(0).getCount());
                transactionResponse.setFlightResponse(flightResponse);
                transactionResponse.setReturnFlight(false);
                returnFlightResponse = null;
                transactionResponse.setReturnFlightResponse(returnFlightResponse);
            }
            if (flightBookingList.size() == 2){
                transactionResponse.setFlight(true);
                flightResponse = convertFlightResponse(flightBookingList.get(0));
                transactionResponse.setNumberOfPassengers(flightBookingList.get(0).getCount());
                transactionResponse.setFlightResponse(flightResponse);
                transactionResponse.setReturnFlight(true);
                returnFlightResponse = convertFlightResponse(flightBookingList.get(1));
                transactionResponse.setReturnPassengers(flightBookingList.get(1).getCount());
                transactionResponse.setReturnFlightResponse(returnFlightResponse);
            }
        }
        if (!CollectionUtils.isEmpty(hotelBookingList)){
            if (hotelBookingList.size() == 1){
                transactionResponse.setHotel(true);
                HotelResponse hotelResponse = new HotelResponse();
                HotelRoom hotelRoom = hotelBookingList.get(0).getHotelRoom();
                transactionResponse.setHotelRooms(hotelBookingList.get(0).getCount());
                transactionResponse.setHotelCheckInDate(hotelBookingList.get(0).getStartDate());
                transactionResponse.setHotelCheckOutDate(hotelBookingList.get(0).getEndDate());
                transactionResponse.setHotelNumberOfDays(ChronoUnit.DAYS.between(hotelBookingList.get(0).getStartDate().toLocalDate(), hotelBookingList.get(0).getEndDate().toLocalDate()));
                HotelRoomResponse hotelRoomResponse = convertHotelRoomResponse(hotelRoom);
                Hotel hotel = hotelRoom.getHotel();
                hotelResponse.setHotelId(hotel.getId());
                hotelResponse.setFranchiseName(hotel.getFranchiseName());
                hotelResponse.setHotelAddress(hotel.getHotelAddress());
                hotelResponse.setHotelName(hotel.getHotelName());
                hotelResponse.setHotelType(hotel.getHotelType());
                hotelResponse.setRating(hotel.getRating());
                hotelResponse.setLocation(hotel.getCity().getCityName()+" , "+hotel.getCity().getCountry());
                hotelResponse.setHotelRoomsList(new ArrayList<HotelRoomResponse>(Arrays.asList(hotelRoomResponse)));
                transactionResponse.setHotelResponse(hotelResponse);
            }
        }
        return transactionResponse;
    }

    private FlightResponse convertFlightResponse(FlightBooking flightBooking){
        FlightResponse flightResponse = new FlightResponse();
        FlightSchedule flightSchedule = flightScheduleRepository.findById(flightBooking.getFlightSchedule().getId()).get();
        flightResponse.setFlightNumber(flightSchedule.getFlight().getFlightName());
        flightResponse.setPrice(flightSchedule.getPrice());
        flightResponse.setFlightScheduleId(flightSchedule.getId());
        flightResponse.setAirlinesName(flightSchedule.getFlight().getAirline());
        flightResponse.setFlightType(flightSchedule.getFlight().getFlightType());
        flightResponse.setFlightRating(flightSchedule.getFlight().getRating());
        flightResponse.setSourceCity(flightSchedule.getSourceCity().getCityName());
        flightResponse.setDestinationCity(flightSchedule.getDestinationCity().getCityName());
        flightResponse.setStartDate(flightSchedule.getStartDate());
        flightResponse.setEndDate(flightSchedule.getEndDate());
        flightResponse.setLogo(flightSchedule.getFlight().getLogo());
        flightResponse.setMiles(flightSchedule.getMileage());
        flightResponse.setFlightReference(flightSchedule.getFlightReference());
        return flightResponse;
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
