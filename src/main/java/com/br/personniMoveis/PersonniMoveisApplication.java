package com.br.personniMoveis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class PersonniMoveisApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonniMoveisApplication.class, args);

//        // Crie um ScheduledExecutorService para agendar a execução da requisição GET
//        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
//
//        // Agende a tarefa para rodar a cada 10 segundos
//        executorService.scheduleAtFixedRate(() -> {
//            // Coloque o código da requisição GET aqui
//            // Você pode usar a mesma lógica de requisição que forneci anteriormente
//            System.out.println("Realizando a requisição GET a cada 10 segundos.");
//        }, 0, 10, TimeUnit.SECONDS);
    }
}
