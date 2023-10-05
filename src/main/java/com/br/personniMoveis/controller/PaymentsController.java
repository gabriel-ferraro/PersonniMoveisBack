package com.br.personniMoveis.controller;

import com.br.personniMoveis.service.payment.PaymentService;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("payments")
@CrossOrigin(origins = "*")
@SecurityRequirement(name = "bearer-key")
public class PaymentsController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentsController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
    @PostMapping
    public ResponseEntity<String> paymentPix(@RequestBody JsonNode payments) throws Exception {
        String result = paymentService.paymentsPix(payments);
        return ResponseEntity.ok(result);
    }


}
