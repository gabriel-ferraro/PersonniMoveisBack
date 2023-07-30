package com.br.personniMoveis.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * Serviço para fazer gerenciamento de envio de e-mails. Para ampliar
 * capacidades de transmissão de dados via e-mail, usa MIME (Multipurpose
 * Internet Mail Extensions) do objeto MimeMessage.
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
     *
     * @return Objeto padrão para gerar mensagens com suporte MIME.
     */
    private MimeMessageHelper buildMimeHelper() {
        try {
            // Cria objeto MimeMessage para corpo da mensagem do e-mail.
            MimeMessage message = javaMailSender.createMimeMessage();
            return new MimeMessageHelper(message, true, "UTF-8");
        } catch (MessagingException ex) {
            Logger.getLogger(EmailService.class.getName()).log(Level.SEVERE, "Erro ao criar MimeMessageHelper", ex);
            return null;
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
                path -> String.format("<img src=\"%s\"></br></br>", path)
        ).orElse(null);
        // Se houver mensagem complementar para mostrar, cria parágrado com msg.
        String complementMessagePtag = complementMessage.map(
                msg -> String.format("<p>\"%s\"</p></br></br>", msg)
        ).orElse(null);
        // Retorna HTML com argumentos formatados no corpo do e-mail.
        return """
                <div style="display: flex; justify-content: center;">
                    <h1>%s</h1> <!-- Mesagem de "propósito" do e-mail -->
                    %s <!-- Mesagem complementar (opcional) -->
                    %s <!-- imagem (opcional) -->
                    %s <!-- Botão com link redirect -->
                </div>
               """.formatted(regularMsg, complementMessagePtag, imgTag, generateButton(buttonMessage, redirectLink));
    }

    /**
     * Constroi HTML do botão com mensagem e âncora de redirecionamento.
     *
     * @param buttonMessage Mesagem demonstrada no botão.
     * @param redirectLink Link que redirecionará usuário ao clicar no botão.
     * @return HTML do botão.
     */
    private String generateButton(String buttonMessage, String redirectLink) {
        return """
                <a href="%s" target="_blank">
                    <button
                        style="background-color:#0a0a23; color:#ffffff; padding:20px; margin-left:10px; cursor: pointer; border-radius: 10px; border: none;">
                        %s
                    </button>
                </a>
               """.formatted(redirectLink, buttonMessage);
    }

    /**
     * Envia mensagem indicando se orçamento CMP foi aprovado ou recusado.
     *
     * @param to Endereço de e-mail de destino.
     * @param storeName Nome da loja do e-commerce.
     * @param approved Mensagem indicando se orçamento foi aprovado ou recusado.
     * @param orderUrl URL do orçamento do cliente.
     * @param detailsMessage Detalhes da aprovação / reprovação.
     * @param clientName Nome do cliente recém cadastrado.
     * @throws jakarta.mail.MessagingException Exceção no envio da mensagem.
     */
    public void orderResponseMessage(String to, String storeName, String approved, String detailsMessage, String orderUrl, String clientName) throws MessagingException {
        // sets de propriedades do e-mail.
        MimeMessageHelper helper = buildMimeHelper();
        helper.setTo(to);
        helper.setSubject("Seu orçamento foi avaliado por nossa loja - ".concat(storeName));
        helper.setText(generateDiv(
                "Olá ".concat(clientName).concat(", veja o resultado do seu orçamento:").concat(approved),
                Optional.of(detailsMessage),
                Optional.empty(),
                "Ver na loja",
                orderUrl));
        // Envia e-mail.
        javaMailSender.send(helper.getMimeMessage());
    }

    /**
     * Notifica o cliente de que um produto indisponível de sua lista de espera
     * retornou à loja.
     *
     * @param to Endereço de e-mail de destino.
     * @param storeName Nome da loja do e-commerce.
     * @param product Detalhes do produto a serem exibidos ao cliente.
     * @param clientName Nome do cliente.
     * @param productUrl URL do produto que retornou ao estoque.
     * @throws jakarta.mail.MessagingException Exceção no envio da mensagem.
     */
    public void productArrivedMessage(String to, String storeName, Object product, String clientName, String productUrl) throws MessagingException {
        // sets de propriedades do e-mail.
        MimeMessageHelper helper = buildMimeHelper();
        helper.setTo(to);
        helper.setSubject("Produto que você aguardava acabou de chagar na ".concat(storeName));
        helper.setText(generateDiv(
                "Olá ".concat(clientName).concat(", um produto da sua lista de espera acabou de retornar para nossa loja!"),
                Optional.empty(),
                Optional.empty(),
                "Ver na loja",
                productUrl));
        // Envia e-mail.
        javaMailSender.send(helper.getMimeMessage());
    }

    /**
     * Envia mensagem para usuário validar a conta após cria-la.
     *
     * @param to Endereço de e-mail de destino.
     * @param clientName Nome do cliente recém cadastrado.
     * @param storeName Nome da loja do e-commerce.
     */
    public void validateAccount(String to, String clientName, String storeName) throws MessagingException {
        // sets de propriedades do email.
        MimeMessageHelper helper = buildMimeHelper();
        helper.setTo(to);
        helper.setSubject("Valide sua conta da ".concat(storeName));
        helper.setText(generateDiv(
                "Olá ".concat(clientName).concat(" falta pouco para criar sua conta na ").concat(storeName),
                Optional.of("Acesse sua conta com seu e-mail e senha."),
                Optional.empty(),
                "Valide clicando aqui",
                "")
        );
        javaMailSender.send(helper.getMimeMessage());
    }

    public void changePassword(String to, String clientName, String storeName) throws MessagingException {
        // sets de propriedades do email.
        MimeMessageHelper helper = buildMimeHelper();
        helper.setTo(to);
        helper.setSubject("Mude a senha da sua conta - ".concat(storeName));
        helper.setText(generateDiv(
                "Olá ".concat(clientName).concat(" parece que você quer redefinir sua senha"),
                Optional.of("Ignore esse e-mail caso não queira modificar sua senha."),
                Optional.empty(),
                "Mude a senha clicando aqui",
                "")
        );
        // Envia e-mail.
        javaMailSender.send(helper.getMimeMessage());
    }

    public void test(String to, String storeName, String clientName) throws MessagingException {
        // sets de propriedades do e-mail.
        MimeMessageHelper helper = buildMimeHelper();
        helper.setTo(to);
        helper.setSubject("Olá vindo da " + storeName);
        // Anexa imagem:
        //helper.addInline("imagem_anexada", new ClassPathResource(testImg)); // Adiciona conteúdo como anexo. manter em caso de uso futuro.
        helper.setText(generateDiv(
                "Olá ".concat(clientName).concat(" isso é uma mensagem teste. Imagem teste abaixo:"),
                Optional.empty(),
                Optional.of("https://cloudfront-us-east-1.images.arcpublishing.com/estadao/HJTUCODBWJKFJK4BUEG4ZA5XYU.jpg"),
                "Clique aqui",
                "https://www.youtube.com/watch?v=dQw4w9WgXcQ&ab_channel=RickAstley")
        );
        // Envia e-mail.
        javaMailSender.send(helper.getMimeMessage());
    }
}
