# Personni Móveis Back-end

Este projeto de software é parte integrante do trabalho de conclusão de curso em Engenharia de Software. O objetivo principal do projeto é desenvolver um sistema de e-commerce com elementos criação e visualização de móveis em passos, entregando ferramentas gerenciáveis para administradores do e-commerce entregarem uma experiência dinâmica e interativa à seus clientes.

## Sumário

  * [Funcionalidades](#Funcionalidades)
  * [Arquitetura](#Arquitetura)
  * [Tecnologias](#Tecnologias)
  * [Setup](#Setup)
  * [Bibliotecas](#Bibliotecas)

## Funcionalidades

<details>
  <summary><b>Clique para expandir</b></summary>

  * A fazer...
</details>

## Arquitetura

  * Diagrama de implantação

  ![Diagrama de implantação](./github%20resources/diagrama%20de%20implantação.png)

## Tecnologias

  * Java 17
  * Spring Boot
  * Docker
  * Gerenciador de dependências: Maven

## Setup

Faça o docker para iniciar o projeto. Use sua IDE para iniciar o projeto manualmente, ou, para rodar com comandos, vá para pasta raiz do projeto e execute:

  * Rodar o projeto: ./mvnw spring-boot:run
  * Atualizar dependências: ./mvnw install - Em caso de poblemas com dependências, tente rodar: ./mvnw clean install

## Bibliotecas

  * Spring boot [Exemplo de uso](https://www.youtube.com/playlist?list=PL62G310vn6nFBIxp6ZwGnm8xMcGE3VA5H) - [Página oficial](https://spring.io/projects/spring-boot)
  * Lombok - Oferece anotações para reduzir o código boilerplate. [Exemplo de uso](https://www.youtube.com/watch?v=L0hTlaIEObM&ab_channel=FelipeP%C3%BAblio) - [Página oficial](https://projectlombok.org/)
  * Mapstruct - Oferece anotações e classes para realizar o mapeamento de DTOs ou entidades, tornando a instanciação de objetos e validação mais simples e menos verbosa. [Exemplo de uso](https://www.youtube.com/watch?v=A9-Inky1Fjo&list=PL62G310vn6nFBIxp6ZwGnm8xMcGE3VA5H&index=16&ab_channel=DevDojo) - [Página oficial](https://mapstruct.org/)
  * Spring Security - biblioteca integrada ao spring-boot para gerenciar autenticação e autorização. [Exemplo de uso](https://www.youtube.com/watch?v=tpGGuCyuSnw&list=PL62G310vn6nFBIxp6ZwGnm8xMcGE3VA5H&index=41) - [Página oficial](https://spring.io/projects/spring-security)
  * Java Mail Sender - Entrega classes facilitadoras para o envio de e-mails. [Exemplo de uso](https://pt.linkedin.com/pulse/envio-de-e-mail-com-spring-boot-tiago-perroni) - [Página oficial](https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/mail.html)

  - <b style="color:red">ATENÇÃO</b>: Este projeto usa Spring-boot v3, algumas bibliotecas podem ser incompátiveis com suas versões do Spring v2, ou podem precisar de outras bibliotecas adicionais para o funcionamento esperado, como é o caso das libs lombok e mastruct que precisam de outra lib adicional para funcionar em conjunto.
