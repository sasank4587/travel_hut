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
import java.sql.Date;

@Entity
@Table( name  = "HOTEL_BOOKING")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HotelBooking implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "HOTEL_ROOM_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private HotelRoom hotelRoom;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TRANSACTIONS_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Transactions transactions;

    @Column(name = "TRAVELLERS")
    private String travellers;

    @Column(name = "START_DATE_TIME")
    private Date startDate;

    @Column(name = "END_DATE_TIME")
    private Date endDate;

    @Column(name = "COUNT")
    private Integer count;

}
