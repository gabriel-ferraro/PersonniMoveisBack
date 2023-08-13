package com.br.personniMoveis.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Serviço para fazer gerenciamento de envio de e-mails. Para ampliar
 * capacidades de transmissão de dados via e-mail, usa MIME (Multipurpose
 * Internet Mail Extensions) do objeto MimeMessage.
 * E-mail Personni Móveis: personnimoveis@gmail.com
 * senha: PersonniMoveis123
 * senha para uso como cliente do g-mail: nzszgltxuasaauok
 */
@Service
public class EmailService {

    /**
     * Injeta objeto para enviar e-mail.
     */
    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /**
     * Configura objeto padrão para gerar mensagens com suporte MIME.
     */
    private void sendEmail(String to, String subject, String text, Optional<String> attachment) {
        try {
            // Cria objeto de mensagem.
            MimeMessage message = javaMailSender.createMimeMessage();
            // Cria objeto MimeMessage para corpo da mensagem do e-mail renderizar conteúdo MIME.
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            // Faz set de configurações do e-mail.
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
            //helper.addAttachment(attachment);
            // Envia e-mail.
            javaMailSender.send(message);

        } catch (MessagingException ex) {
            Logger.getLogger(EmailService.class.getName()).log(Level.SEVERE, "Erro ao criar e enviar mensagem de e-mail.", ex);
        }
    }

    /**
     * Gera a div para organizar conteúdo da mensagem.
     *
     * @return String com HTML da div.
     */
    private String generateDiv(String regularMsg, Optional<String> complementMessage, Optional<String> imgPath,
                               String buttonMessage, String redirectLink) {
        // Se houver imagem para mostrar no e-mail.
        String imgTag = imgPath.map(
                path -> String.format("<img src=\"%s\"><br><br>", path)
        ).orElse("");
        // Se houver mensagem complementar para mostrar, cria parágrado com msg.
        String complementMessagePtag = complementMessage.map(
                msg -> String.format("<p style=\"font-size:16px;\">\"%s\"</p><br><br>", msg)
        ).orElse("");
        // Retorna HTML com argumentos formatados no corpo do e-mail.
        return """
                 <div style="justify-content: center;">
                     <h1>%s</h1>
                     %s
                     %s
                     %s
                 </div>
                """.formatted(regularMsg, complementMessagePtag, imgTag, generateButton(buttonMessage, redirectLink));
    }

    /**
     * Constroi HTML do botão com mensagem e âncora de redirecionamento.
     *
     * @param buttonMessage Mesagem demonstrada no botão.
     * @param redirectLink  Link que redirecionará usuário ao clicar no botão.
     * @return HTML do botão.
     */
    private String generateButton(String buttonMessage, String redirectLink) {
        return """
                 <a href="%s" target="_blank">
                     <button
                         style="background-color:#0a0a23; color:#ffffff; padding:20px; margin-left:10px; cursor: pointer; border-radius: 10px; border: none; width: 50%%">
                         %s
                     </button>
                 </a>
                """.formatted(redirectLink, buttonMessage);
    }

    /**
     * Envia mensagem indicando se orçamento CMP foi aprovado ou recusado.
     *
     * @param to             Endereço de e-mail de destino.
     * @param storeName      Nome da loja do e-commerce.
     * @param approved       Mensagem indicando se orçamento foi aprovado ou recusado.
     * @param orderUrl       URL do orçamento do cliente.
     * @param detailsMessage Detalhes da aprovação / reprovação.
     * @param clientName     Nome do cliente recém cadastrado.
     * @throws jakarta.mail.MessagingException Exceção no envio da mensagem.
     */
    public void orderResponseMessage(String to, String storeName, String approved, String detailsMessage, String orderUrl, String clientName) throws MessagingException {
        // Constrói strings de conteúdo do e-mail.
        String subject = "Seu orçamento foi avaliado por nossa loja - ".concat(storeName);
        String mainContent = generateDiv(
                "Olá ".concat(clientName).concat(", veja o resultado do seu orçamento:").concat(approved),
                Optional.of(detailsMessage),
                Optional.empty(),
                "Ver na loja",
                orderUrl);
        // Envia e-mail.
        sendEmail(to, subject, mainContent, Optional.empty());
    }

    /**
     * Notifica o cliente de que um produto indisponível de sua lista de espera
     * retornou à loja.
     *
     * @param to         Endereço de e-mail de destino.
     * @param storeName  Nome da loja do e-commerce.
     * @param product    Detalhes do produto a serem exibidos ao cliente.
     * @param clientName Nome do cliente.
     * @param productUrl URL do produto que retornou ao estoque.
     * @throws jakarta.mail.MessagingException Exceção no envio da mensagem.
     */
    public void productArrivedMessage(String to, String storeName, Object product, String clientName, String productUrl) throws MessagingException {
        // Constrói strings de conteúdo do e-mail.
        String subject = "Produto que você aguardava acabou de chagar na ".concat(storeName);
        String mainContent = generateDiv(
                "Olá ".concat(clientName).concat(", um produto da sua lista de espera acabou de retornar para nossa loja!"),
                Optional.empty(),
                Optional.empty(),
                "Ver na loja",
                productUrl);
        // Envia e-mail.
        sendEmail(to, subject, mainContent, Optional.empty());
    }

    /**
     * Envia mensagem para usuário validar a conta após cria-la.
     *
     * @param to         Endereço de e-mail de destino.
     * @param clientName Nome do cliente recém cadastrado.
     * @param storeName  Nome da loja do e-commerce.
     * @throws jakarta.mail.MessagingException Exceção no envio da mensagem.
     */
    public void validateAccount(String to, String clientName, String storeName) throws MessagingException {
        // Constrói strings de conteúdo do e-mail.
        String subject = "Valide sua conta da ".concat(storeName);
        String mainContent = generateDiv(
                "Olá ".concat(clientName).concat(" falta pouco para criar sua conta na ").concat(storeName),
                Optional.of("Acesse sua conta com seu e-mail e senha."),
                Optional.empty(),
                "Valide clicando aqui",
                "");
        sendEmail(to, subject, mainContent, Optional.empty());
    }

    public void changePassword(String to, String clientName, String storeName) throws MessagingException {
        // Constrói strings de conteúdo do e-mail.
        String subject = "Mude a senha da sua conta - ".concat(storeName);
        String mainContent = generateDiv(
                "Olá ".concat(clientName).concat(" parece que você quer redefinir sua senha"),
                Optional.of("Ignore esse e-mail caso não queira modificar sua senha."),
                Optional.empty(),
                "Mude a senha clicando aqui",
                "");
        sendEmail(to, subject, mainContent, Optional.empty());
    }

    public void test(String to, String storeName, String clientName) {
        // Constrói strings de conteúdo do e-mail.
        String subject = "Olá vindo da ".concat(storeName);
        // Anexa imagem:
        //helper.addInline("imagem_anexada", new ClassPathResource(testImg)); // Adiciona conteúdo como anexo. manter em caso de uso futuro.
        // gera conteúdo central.
        String mainContent = generateDiv(
                "Olá ".concat(clientName).concat(", isso é uma mensagem teste. Mensagem complementar e imagem teste abaixo:"),
                Optional.of("Mesagem complementar de teste aqui!"),
                Optional.of("https://cloudfront-us-east-1.images.arcpublishing.com/estadao/HJTUCODBWJKFJK4BUEG4ZA5XYU.jpg"),
                "Clique aqui",
                "https://www.youtube.com/watch?v=dQw4w9WgXcQ&ab_channel=RickAstley");
        sendEmail(to, subject, mainContent, Optional.empty());
    }
}
