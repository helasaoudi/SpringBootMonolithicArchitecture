package com.example.demo.Payment.Controller;

import com.example.demo.Booking.dto.BookingDto;
import com.example.demo.Payment.DTO.PaymentDto;
import com.example.demo.Payment.Service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;


    @GetMapping
    public ResponseEntity<List<PaymentDto>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @PostMapping
    public ResponseEntity<PaymentDto> pay(@RequestBody PaymentDto dto) {
        return ResponseEntity.ok(paymentService.pay(dto));
    }
}

