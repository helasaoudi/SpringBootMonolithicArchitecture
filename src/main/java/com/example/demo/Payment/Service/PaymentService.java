package com.example.demo.Payment.Service;

import com.example.demo.Booking.entity.Booking;
import com.example.demo.Booking.repository.BookingRepository;
import com.example.demo.Payment.DTO.PaymentDto;
import com.example.demo.Payment.Repository.PaymentRepository;
import com.example.demo.Payment.entity.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;


    public PaymentDto pay(PaymentDto dto) {
        Booking booking = bookingRepository.findById(dto.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        Payment payment = Payment.builder()
                .paymentMethod(dto.getPaymentMethod())
                .amount(dto.getAmount())
                .paymentTime(LocalDateTime.now())
                .booking(booking)
                .build();

        Payment saved = paymentRepository.save(payment);

        return PaymentDto.builder()
                .id(saved.getId())
                .paymentMethod(saved.getPaymentMethod())
                .amount(saved.getAmount())
                .bookingId(booking.getId())
                .build();
    }
}
