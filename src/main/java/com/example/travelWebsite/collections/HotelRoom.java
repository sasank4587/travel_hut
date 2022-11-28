package com.example.travelWebsite.collections;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table( name  = "HOTEL_ROOM")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HotelRoom implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "HOTEL_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Hotel hotel;

    @Column(name = "ROOM_TYPE")
    private String roomType;

    @Column(name = "PRICE")
    private double price;

    @Column(name = "AMENITIES")
    private String amenities;

    @Column(name = "ROOM_SIZE")
    private Integer roomSize;

}
