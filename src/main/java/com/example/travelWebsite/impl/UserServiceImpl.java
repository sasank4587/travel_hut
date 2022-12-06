package com.example.travelWebsite.impl;

import com.example.travelWebsite.collections.Feedback;
import com.example.travelWebsite.collections.PaymentInfo;
import com.example.travelWebsite.collections.User;
import com.example.travelWebsite.exception.PaymentMethodAlreadyExists;
import com.example.travelWebsite.exception.UserAlreadyExists;
import com.example.travelWebsite.exception.UserDoesNotExist;
import com.example.travelWebsite.repository.FeedbackRepository;
import com.example.travelWebsite.repository.PaymentInfoRepository;
import com.example.travelWebsite.repository.UserRepository;
import com.example.travelWebsite.request.AddFeedbackRequest;
import com.example.travelWebsite.request.AuthenticationRequest;
import com.example.travelWebsite.request.PaymentRequest;
import com.example.travelWebsite.request.UserRequest;
import com.example.travelWebsite.response.PaymentMethods;
import com.example.travelWebsite.response.UserProfileResponse;
import com.example.travelWebsite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentInfoRepository paymentInfoRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Override
    public UserProfileResponse addUser(UserRequest userRequest) throws UserAlreadyExists {
        Optional<User> userObject = userRepository.findByUserName(userRequest.getUserName());
        if (!userObject.isPresent()) {
            User user = new User();
            user.setFirstName(userRequest.getFirstName());
            user.setLastName(userRequest.getLastName());
            user.setPhoneNumber(userRequest.getPhoneNumber());
            user.setEmailId(userRequest.getEmailId());
            user.setUserAddress(userRequest.getUserAddress());
            user.setTravelMileage(0);
            user.setUserName(userRequest.getUserName());
            user.setPassword(userRequest.getPassword());
            User userEntity = userRepository.save(user);
            if (Objects.nonNull(userEntity.getId())) {
                addPaymentMethod(userEntity, userRequest.getCardNumber(), userRequest.getNameOnTheCard(), userRequest.getExpiryDate(), userRequest.getSecurityCode(), true, userRequest.getPaymentName());
                return getProfileDetails(userEntity);
            }
        }
        throw new UserAlreadyExists("This username already exists.");
    }

    @Override
    public List<PaymentMethods> addPaymentMethod(PaymentRequest paymentRequest, Integer userId) throws UserDoesNotExist, PaymentMethodAlreadyExists {
        Optional<User> userObject = userRepository.findById(userId);
        if (userObject.isPresent()) {
            User user = userObject.get();
            if (paymentRequest.isDefault()) {
                PaymentInfo paymentInfo = paymentInfoRepository.findByUserAndIsDefault(user, true);
                paymentInfo.setDefault(false);
                paymentInfoRepository.save(paymentInfo);
            }
            PaymentInfo paymentInfo = paymentInfoRepository.findByUserAndCardNumber(user, paymentRequest.getCardNumber());
            if (!Objects.nonNull(paymentInfo) || !Objects.nonNull(paymentInfo.getId())) {
                addPaymentMethod(user, paymentRequest.getCardNumber(), paymentRequest.getNameOnTheCard(), paymentRequest.getExpiryDate(), paymentRequest.getSecurityCode(), paymentRequest.isDefault(), paymentRequest.getPaymentName());
            } else {
                throw new PaymentMethodAlreadyExists("Payment method already exists");
            }
            List<PaymentInfo> paymentInfos = paymentInfoRepository.findByUser(user);

            return convertPayments(paymentInfos);
        } else {
            throw new UserDoesNotExist("This user does not exist.");
        }
    }

    @Override
    public List<PaymentMethods> makePaymentDefault(Integer paymentId, Integer id) throws PaymentMethodAlreadyExists, UserDoesNotExist {
        Optional<User> userObject = userRepository.findById(id);
        if (userObject.isPresent()) {
            Optional<PaymentInfo> defaultOptional = paymentInfoRepository.findById(paymentId);
            if (defaultOptional.isPresent()) {
                User user = userObject.get();
                PaymentInfo paymentInfo = paymentInfoRepository.findByUserAndIsDefault(user, true);
                paymentInfo.setDefault(false);
                paymentInfoRepository.save(paymentInfo);
                PaymentInfo paymentInfo1 = defaultOptional.get();
                paymentInfo1.setDefault(true);
                paymentInfoRepository.save(paymentInfo1);
                List<PaymentInfo> paymentInfos = paymentInfoRepository.findByUser(user);

                return convertPayments(paymentInfos);
            } else {
                throw new PaymentMethodAlreadyExists("Payment method already exists");
            }
        } else {
            throw new UserDoesNotExist("This user does not exist.");
        }
    }

    @Override
    public UserProfileResponse getProfileDetails(Integer userId) throws UserDoesNotExist {
        Optional<User> userObject = userRepository.findById(userId);
        if (userObject.isPresent()) {
            User user = userObject.get();

            return getProfileDetails(user);
        } else {
            throw new UserDoesNotExist("This user does not exist.");
        }
    }

    @Override
    public List<PaymentMethods> getPaymentDetails(Integer userId) throws UserDoesNotExist {
        Optional<User> userObject = userRepository.findById(userId);
        if (userObject.isPresent()) {
            User user = userObject.get();
            List<PaymentInfo> paymentInfos = paymentInfoRepository.findByUser(user);

            return convertPayments(paymentInfos);
        } else {
            throw new UserDoesNotExist("This user does not exist.");
        }
    }

    @Override
    public void authenticate(AuthenticationRequest authenticationRequest) throws Exception {
        Optional<User> foundedUser = userRepository.findByUserName(authenticationRequest.getUsername());
        if (!foundedUser.isPresent()) {
            throw new Exception();
        } else if (!authenticationRequest.getPassword().equalsIgnoreCase(foundedUser.get().getPassword())) {
            throw new Exception();
        }
    }

    @Override
    public UserProfileResponse findByUsername(String username) {
        Optional<User> userObject = userRepository.findByUserName(username);
        if (userObject.isPresent()) {
            User user = userObject.get();
            return getProfileDetails(user);
        }
        return null;
    }

    @Override
    public boolean addFeedback(AddFeedbackRequest addFeedbackRequest) {
        User user = new User();
        if (Objects.nonNull(addFeedbackRequest.getUserId())) {
            Optional<User> userObject = userRepository.findById(Integer.valueOf(addFeedbackRequest.getUserId()));
            if (userObject.isPresent()) {
                user = userObject.get();
            }
        } else {
            user = null;
        }
        Feedback feedback = new Feedback();
        feedback.setUser(user);
        feedback.setOption(addFeedbackRequest.getOption());
        feedback.setComments(addFeedbackRequest.getComments());
        feedbackRepository.save(feedback);
        return true;
    }

//    @Override
//    public List<UserProfileResponse> getAllUsers() {
//        return null;
//    }

    private UserProfileResponse getProfileDetails(User user) {
        UserProfileResponse profileResponse = new UserProfileResponse();
        profileResponse.setId(user.getId());
        profileResponse.setFirstName(user.getFirstName());
        profileResponse.setLastName(user.getLastName());
        profileResponse.setPhoneNumber(user.getPhoneNumber());
        profileResponse.setEmailId(user.getEmailId());
        profileResponse.setUserAddress(user.getUserAddress());
        profileResponse.setTravelMileage(user.getTravelMileage());
        profileResponse.setUserName(user.getUserName());

        return profileResponse;
    }

    private void addPaymentMethod(User user, String cardNumber, String name, Date expiryDate, String securityCode, boolean isDefault, String paymentName) {
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setUser(user);
        paymentInfo.setDefault(isDefault);
        paymentInfo.setNameOnTheCard(name);
        paymentInfo.setCardNumber(cardNumber);
        paymentInfo.setCardExpiryDate(expiryDate);
        paymentInfo.setSecurityCode(securityCode);
        paymentInfo.setPaymentName(paymentName);
        paymentInfoRepository.save(paymentInfo);
    }

    private List<PaymentMethods> convertPayments(List<PaymentInfo> paymentInfos) {

        return paymentInfos.stream().map(paymentInfo -> {
            return new PaymentMethods(paymentInfo.getId(), paymentInfo.getCardNumber(), paymentInfo.getNameOnTheCard(), paymentInfo.getCardExpiryDate().toString(), paymentInfo.getSecurityCode(), paymentInfo.isDefault(), paymentInfo.getPaymentName());
        }).collect(Collectors.toList());
    }

}
