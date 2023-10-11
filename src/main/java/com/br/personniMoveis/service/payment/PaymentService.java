package com.br.personniMoveis.service.payment;

import com.fasterxml.jackson.databind.JsonNode;
import org.json.JSONArray;
import org.json.JSONObject;
import br.com.gerencianet.gnsdk.Gerencianet;
import br.com.gerencianet.gnsdk.exceptions.GerencianetException;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {
    public String paymentsPix(JsonNode payment) throws Exception {
        String cpf = payment.get("devedor").get("cpf").asText(); // Extrai o CPF
        String nome = payment.get("devedor").get("nome").asText(); // Extrai o nome
        String valor = payment.get("valor").get("original").asText(); // Extrai o valor original
        String existingKey = null;
        int IdQrCode = 0;

        Credentials credentials = new Credentials();
        JSONObject options = new JSONObject();
        options.put("client_id", credentials.getClientId());
        options.put("client_secret", credentials.getClientSecret());
        options.put("certificate", credentials.getCertificate());
        options.put("sandbox", credentials.isSandbox());

        try {
            Gerencianet gn = new Gerencianet(options);
            JSONObject response = gn.call("pixListEvp", new HashMap<String,String>(), new JSONObject());
            System.out.println(response);
            JSONArray chaves = response.getJSONArray("chaves");
            if (chaves.length() > 0) {
                existingKey = chaves.getString(0); // Obtém a primeira chave da lista
            }
        } catch (GerencianetException e) {
            System.out.println(e.getError());
            System.out.println(e.getErrorDescription());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        if (existingKey == null) {
            try {
                Gerencianet gn = new Gerencianet(options);
                JSONObject response = gn.call("pixCreateEvp", new HashMap<String,String>(), new JSONObject());
                System.out.println(response);
                JSONArray chaves = response.getJSONArray("chaves");
                if (chaves.length() > 0) {
                    existingKey = chaves.getString(0); // Obtém a primeira chave da lista
                }
            } catch (GerencianetException e) {
                System.out.println(e.getError());
                System.out.println(e.getErrorDescription());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        JSONObject body = new JSONObject();
        body.put("calendario", new JSONObject().put("expiracao", 3600));
        body.put("devedor", new JSONObject().put("cpf", cpf).put("nome", nome));
        body.put("valor", new JSONObject().put("original", valor));
        body.put("chave", existingKey);
        body.put("solicitacaoPagador", "Serviço realizado.");

        JSONArray infoAdicionais = new JSONArray();
        infoAdicionais.put(new JSONObject().put("nome", "Campo 1").put("valor", "Informação Adicional1 do PSP-Recebedor"));
        infoAdicionais.put(new JSONObject().put("nome", "Campo 2").put("valor", "Informação Adicional2 do PSP-Recebedor"));
        body.put("infoAdicionais", infoAdicionais);

        try {
            Gerencianet gn = new Gerencianet(options);
            JSONObject response = gn.call("pixCreateImmediateCharge", new HashMap<String,String>(), body);
            System.out.println(response);
            IdQrCode = response.getJSONObject("loc").getInt("id");
        } catch (GerencianetException e) {
            System.out.println(e.getError());
            System.out.println(e.getErrorDescription());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        if (IdQrCode > 0) {
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("id", String.valueOf(IdQrCode));

            try {
                Gerencianet gn = new Gerencianet(options);
                Map<String, Object> response = gn.call("pixGenerateQRCode", params, new HashMap<String, Object>());

                String base64Image = (String) response.get("imagemQrcode");

                // Retorne a imagem em base64 para o front-end
                return base64Image;

            } catch (GerencianetException e) {
                System.out.println(e.getError());
                System.out.println(e.getErrorDescription());
                return "Erro ao criar QrCode: " + e.getErrorDescription();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return "Erro ao criar QrCode: " + e.getMessage();
            }
        }

        return null;
    }
}
