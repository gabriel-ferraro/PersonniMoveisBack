package com.br.personniMoveis;

import br.com.gerencianet.gnsdk.Gerencianet;
import br.com.gerencianet.gnsdk.exceptions.GerencianetException;
import com.br.personniMoveis.exception.ResourceNotFoundException;
import com.br.personniMoveis.model.user.Order;
import com.br.personniMoveis.repository.OrderRepository;
import com.br.personniMoveis.service.payment.Credentials;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;




@SpringBootApplication
public class PersonniMoveisApplication {
    private static OrderRepository orderRepository = null;
    public PersonniMoveisApplication(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    public static void main(String[] args) {
        SpringApplication.run(PersonniMoveisApplication.class, args);
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        // Agende a tarefa para rodar a cada 10 segundos
        executorService.scheduleAtFixedRate(() -> {
            try {
                // 1. Execute a requisição GET para buscar os pedidos do servidor
                String ordersUrl = "http://127.0.0.1:8081/orders"; // Substitua pela URL real do seu servidor
                HttpURLConnection connection = (HttpURLConnection) new URL(ordersUrl).openConnection();
                connection.setRequestMethod("GET");
                int responseCode = connection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    // 2. Extraia o valor da coluna "txid" da resposta (assumindo que a resposta é um JSON)
                    JSONArray orders = new JSONArray(response.toString());
                    for (int i = 0; i < orders.length(); i++) {
                        JSONObject order = orders.getJSONObject(i);
                        String txid = order.getString("txid");
                        Long id = (long) order.getInt("orderId");

                        // 3. Use o valor de "txid" para buscar detalhes da carga Pix com a biblioteca Gerencianet
                        String status = pixDetailCharge(txid);


                        // 4. Atualize o status do pedido correspondente
                        Order orderWithStatus = findOrderWithTxid(id);
                        if (orderWithStatus != null) {
                            orderWithStatus.setStatus(status);
                            orderRepository.save(orderWithStatus);
                        }
                    }
                } else {
                    System.out.println("Falha na requisição GET. Código de resposta: " + responseCode);
                }
                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 0, 10, TimeUnit.SECONDS);
    }

    private static String pixDetailCharge(String txid) {
        String status = "";
        Credentials credentials = new Credentials();
        JSONObject options = new JSONObject();
        options.put("client_id", credentials.getClientId());
        options.put("client_secret", credentials.getClientSecret());
        options.put("certificate", credentials.getCertificate());
        options.put("sandbox", credentials.isSandbox());

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("txid", txid);

        try {
            Gerencianet gn = new Gerencianet(options);
            JSONObject response = gn.call("pixDetailCharge", params, new JSONObject());
            status = response.getString("status");
            return status;
        } catch (GerencianetException e) {
            System.out.println(e.getError());
            System.out.println(e.getErrorDescription());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return status;
    }

    private static Order findOrderWithTxid(Long id) {
        Order order = findOrderOrThrowBadRequestException(id);
        if(order != null){
          return order;
        }

            return null;
    }

    private static Order findOrderOrThrowBadRequestException(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado."));
    }
}
