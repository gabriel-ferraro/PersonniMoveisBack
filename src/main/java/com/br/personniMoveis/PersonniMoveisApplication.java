package com.br.personniMoveis;

import br.com.gerencianet.gnsdk.Gerencianet;
import br.com.gerencianet.gnsdk.exceptions.GerencianetException;
import com.br.personniMoveis.exception.ResourceNotFoundException;
import com.br.personniMoveis.model.user.Order;
import com.br.personniMoveis.model.user.OrderCmp;
import com.br.personniMoveis.repository.OrderCmpRepository;
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
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class PersonniMoveisApplication {
    private static OrderRepository orderRepository;

    private static OrderCmpRepository orderCmpRepository;


    public PersonniMoveisApplication(OrderRepository orderRepository, OrderCmpRepository orderCmpRepository) {
        this.orderRepository = orderRepository;
        this.orderCmpRepository = orderCmpRepository;
    }

    public static void main(String[] args) throws GeneralSecurityException, IOException {
        SpringApplication.run(PersonniMoveisApplication.class, args);

        // Executa update para checar status de pedidos.
        executeStatusPayments();

    }

    private static void executeStatusPayments() {
        // Executa update para checar status de pedidos.
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        // URL para a primeira lista de pedidos
        String ordersUrl = "http://127.0.0.1:8081/orders";

        // URL para a segunda lista de pedidos
        String ordersCmpUrl = "http://127.0.0.1:8081/orders/cmp";

        // Agende a tarefa para rodar a cada 10 segundos
        executorService.scheduleAtFixedRate(() -> {
            processOrders(ordersUrl);
            processOrders(ordersCmpUrl);
        }, 0, 10, TimeUnit.SECONDS);
    }

    private static void processOrders(String ordersUrl) {
        try {
            Long id = null;
            Long idCmp = null;
            // 1. Execute a requisição GET para buscar os pedidos do servidor
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
                    JSONArray dateArray = order.getJSONArray("date"); // Obtém o array JSON da data

                    // Converte o array JSON da data para uma string no formato "yyyy-MM-dd HH:mm:ss.SSS"
                    String date = String.format(
                            "%04d-%02d-%02d %02d:%02d:%02d.%03d",
                            dateArray.getInt(0), dateArray.getInt(1), dateArray.getInt(2),
                            dateArray.getInt(3), dateArray.getInt(4), dateArray.getInt(5),
                            dateArray.getInt(6)
                    );
                    if (order.has("orderId")) {
                        id = (long) order.getInt("orderId");
                    } else if (order.has("orderCmpId")) {
                        idCmp = (long) order.getInt("orderCmpId");
                    }
                    if (txid != null) {
                        // 3. Use o valor de "txid" para buscar detalhes da carga Pix com a biblioteca Gerencianet
                        String status = pixDetailCharge(txid);
                        if ("CONCLUIDA".equals(status)) {
                            if (id != null) {
                                Order orderWithStatus = findOrderWithTxid(id);
                                if (orderWithStatus != null) {
                                    orderWithStatus.setStatus(status);
                                    orderRepository.save(orderWithStatus);
                                }
                            } else if (idCmp != null) {
                                OrderCmp orderCmpWithStatus = findOrderCmpWithTxid(idCmp);
                                orderCmpWithStatus.setStatus(status);
                                orderCmpRepository.save(orderCmpWithStatus);
                            }
                        }
                        if ("ATIVA".equals(status)) {
                            // 4. Verifique a data de criação
                            if (isOrderCreatedMoreThan5MinutesAgo(date)) {
                                status = "CANCELADO";
                            }
                            if (id != null) {
                                Order orderWithStatus = findOrderWithTxid(id);
                                if (orderWithStatus != null) {
                                    orderWithStatus.setStatus(status);
                                    orderRepository.save(orderWithStatus);
                                }
                            } else if (idCmp != null) {
                                OrderCmp orderCmpWithStatus = findOrderCmpWithTxid(idCmp);
                                orderCmpWithStatus.setStatus(status);
                                orderCmpRepository.save(orderCmpWithStatus);
                            }
                        }
                    }
                }
            } else {
                System.out.println("Falha na requisição GET. Código de resposta: " + responseCode);
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        if (order != null) {
            return order;
        }
        return null;
    }

    private static OrderCmp findOrderCmpWithTxid(Long id) {
        OrderCmp orderCmp = findOrderCmpOrThrowBadRequestException(id);
        if (orderCmp != null) {
            return orderCmp;
        }
        return null;
    }

    private static Order findOrderOrThrowBadRequestException(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado."));
    }

    private static OrderCmp findOrderCmpOrThrowBadRequestException(Long ordercmpId) {
        return orderCmpRepository.findById(ordercmpId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido Cmp não encontrado."));
    }

    private static boolean isOrderCreatedMoreThan5MinutesAgo(String creationDateStr) {
        try {
            String[] dates = creationDateStr.split("\\.");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(dates[0], formatter);
            long differenceInMillis = ChronoUnit.MINUTES.between(dateTime, LocalDateTime.now());
            return differenceInMillis > 5;
        } catch (Exception ex) {
            System.out.println(ex);

        }
        return false;
    }


}
