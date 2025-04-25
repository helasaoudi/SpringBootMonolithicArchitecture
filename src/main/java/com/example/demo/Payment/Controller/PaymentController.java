package com.example.demo.Payment.Controller;

import com.example.demo.Payment.DTO.PaymentDto;
import com.example.demo.Payment.Service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;



    @PostMapping
    public ResponseEntity<PaymentDto> pay(@RequestBody PaymentDto dto) {
        return ResponseEntity.ok(paymentService.pay(dto));
    }
}

