package com.br.personniMoveis.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
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
     * Envia mensagem indicando se orçamento CMP foi aprovado ou recusado.
     *
     * @param to Endereço de e-mail de destino.
     * @param storeName Nome da loja do e-commerce.
     * @param approved Mensagem indicando se orçamento foi aprovado ou recusado.
     * @param orderUrl URL do orçamento do cliente.
     * @param detailsMessage Detalhes da aprovação / reprovação.
     * @param clientName Nome do cliente recém cadastrado.
     */
    public void orderResponseMessage(String to, String storeName, String approved, String detailsMessage, String orderUrl, String clientName) {
        // Cria objeto para corpo da mesagem do e-mail.
        SimpleMailMessage message = new SimpleMailMessage();
        // sets de propriedades do email.
        message.setTo(to);
        message.setSubject("Seu orçamento foi avaliado por nossa loja - ".concat(storeName));
        message.setText(
                """
                        Olá %s, veja baixo o resultado do seu orçamento</br></br>
                        Nossa resposta para seu orçamento:</br>
                        %s </br>
                        <!-- Detalhes do orçamento -->
                        Mensagem de resposta: %s
                        </br></br>
                        
                        <button 
                            onClick="window.open('%s');"
                            style="background-color:#0a0a23; color:#ffffff; padding:20px; margin-left:10px">
                            Ver na loja
                        </button>
                    """.formatted(clientName, approved, detailsMessage, orderUrl));
        // Envia e-mail.
        javaMailSender.send(message);
    }

    /**
     * Notifica o cliente de que um produto indisponível de sua lista de espera
     * retornou à loja.
     *
     * @param to Endereço de e-mail de destino.
     * @param storeName Nome da loja do e-commerce.
     * @param product Detalhes do produto a serem exibidos ao cliente.
     * @param clientName Nome do cliente.
     */
    public void productArrivedMessage(String to, String storeName, Object product, String clientName) {
        // Cria objeto para corpo da mesagem do e-mail.
        SimpleMailMessage message = new SimpleMailMessage();
        // sets de propriedades do email.
        message.setTo(to);
        message.setSubject("Produto que você aguardava acabou de chagar na ".concat(storeName));
        message.setText(
                """
                        Olá %s, um produto da sua lista de espera acabou de retornar para nossa loja!</br></br>
                        <!-- Imagem principal e nome do produto -->
                        </br>
                        <button
                            onClick="window.open('#');"
                            style="background-color:#0a0a23; color:#ffffff; padding:20px; margin-left:10px">
                            Ver na loja
                        </button>
                    """.formatted(clientName));
        // Envia e-mail.
        javaMailSender.send(message);
    }

    /**
     * Envia mensagem para usuário validar a conta após cria-la.
     *
     * @param to Endereço de e-mail de destino.
     * @param clientName Nome do cliente recém cadastrado.
     * @param storeName Nome da loja do e-commerce.
     */
    public void validateAccount(String to, String clientName, String storeName) {
        // Cria objeto para corpo da mesagem do e-mail.
        SimpleMailMessage message = new SimpleMailMessage();
        // sets de propriedades do email.
        message.setTo(to);
        message.setSubject("Valide sua conta da ".concat(storeName));
        message.setText(
                """
                        Olá %s, você acaba de criar sua conta na %s! seja bem vindo!</br></br>
                        Acesse sua conta com seu e-mail e senha.</br>
                        Validar sua conta - 
                        <button 
                            onClick="window.open('#');"
                            style="background-color:#0a0a23; color:#ffffff; padding:20px; margin-left:10px">
                            Clique aqui
                        </button>
                    """.formatted(clientName, storeName));
        // Envia e-mail.
        javaMailSender.send(message);
    }

    public void changePassword(String to, String clientName, String storeName) {
        // Cria objeto para corpo da mesagem do e-mail.
        SimpleMailMessage message = new SimpleMailMessage();
        // sets de propriedades do email.
        message.setTo(to);
        message.setSubject("Mude sua senha da ".concat(storeName));
        message.setText(
                """
                        Olá %s, parece que você quer redefinir sua senha</br></br>
                        Mudar sua senha - 
                        <button 
                            onClick="window.open('#');"
                            style="background-color:#0a0a23; color:#ffffff; padding:20px; margin-left:10px">
                            Clique aqui
                        </button>
                        </br></br>Se você não pediu para recuperar sua senha, ignore esse e-mail.
                    """.formatted(clientName));
        // Envia e-mail.
        javaMailSender.send(message);
    }

    public void test(String to, String storeName, String clientName) {
        try {
            // sets de propriedades do e-mail.
            MimeMessageHelper helper = buildMimeHelper();
            helper.setTo(to);
            helper.setSubject("Olá vindo da " + storeName);
            helper.setText(
                    """
                            Olá %s, isso é uma mensagem teste enviada para todos que se cadastram na loja. Imagem teste abaixo:</br>
                            <!-- <img src='cid:imagem_anexada'> -->
                            <img src='https://cloudfront-us-east-1.images.arcpublishing.com/estadao/HJTUCODBWJKFJK4BUEG4ZA5XYU.jpg'>
                            <button
                                onClick="window.open('https://www.youtube.com/watch?v=dQw4w9WgXcQ&ab_channel=RickAstley');"
                                style="background-color:#0a0a23; color:#ffffff; padding:20px; margin-left:10px; cursor: pointer; border-radius: 10px;">
                                Clique aqui
                            </button>
                        """.formatted(clientName), true);
            // Anexa imagem:
            //helper.addInline("imagem_anexada", new ClassPathResource(testImg));

            // Envia e-mail.
            javaMailSender.send(helper.getMimeMessage());
        } catch (MessagingException ex) {
            Logger.getLogger(EmailService.class.getName()).log(Level.SEVERE, "Erro ao criar mensagem", ex);
        }
    }
}
