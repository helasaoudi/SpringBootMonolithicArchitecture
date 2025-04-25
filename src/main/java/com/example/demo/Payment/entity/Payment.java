package com.example.demo.Payment.entity;


import com.example.demo.Booking.entity.Booking;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String paymentMethod;
    private double amount;
    private LocalDateTime paymentTime;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;
}
