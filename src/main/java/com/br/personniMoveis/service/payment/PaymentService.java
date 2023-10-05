package com.br.personniMoveis.service.payment;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class PaymentService {
    public String paymentsPix(JsonNode payment) throws Exception {
        String token = "851FDF4120B84870869B2C310A220C2F"; // Substitua pelo seu token

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://sandbox.api.pagseguro.com/orders"))
                .header("accept", "application/json")
                .header("Authorization", token)
                .header("content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(String.valueOf(payment)))
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 201) {
            return response.body();
        } else {
            throw new Exception("Erro ao enviar pagamento via Pix: " + response.statusCode());
        }
    }
}
