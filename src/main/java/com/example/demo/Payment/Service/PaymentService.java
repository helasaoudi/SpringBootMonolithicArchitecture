package com.example.demo.Payment.Service;

import com.example.demo.Booking.dto.BookingDto;
import com.example.demo.Booking.entity.Booking;
import com.example.demo.Booking.repository.BookingRepository;
import com.example.demo.Booking.service.BookingService;
import com.example.demo.Payment.DTO.PaymentDto;
import com.example.demo.Payment.Repository.PaymentRepository;
import com.example.demo.Payment.entity.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final BookingService bookingService;

    public List<PaymentDto> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();
        return payments.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private PaymentDto convertToDto(Payment payment) {
        return PaymentDto.builder()
                .id(payment.getId())
                .paymentMethod(payment.getPaymentMethod())
                .amount(payment.getAmount())
                .bookingId(payment.getBooking().getId())
                .build();
    }

    public PaymentDto pay(PaymentDto dto) {
        Booking booking = bookingRepository.findById(dto.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        //bookingRepository.updateBooking(booking.getId());
        // Appel à la méthode updateBooking du service BookingService
        Booking updatedBooking = bookingService.updateBooking(booking.getId());

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
