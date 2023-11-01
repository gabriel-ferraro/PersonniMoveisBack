package com.br.personniMoveis.service.payment;

import br.com.gerencianet.gnsdk.Gerencianet;
import br.com.gerencianet.gnsdk.exceptions.GerencianetException;
import com.br.personniMoveis.model.user.UserEntity;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.catalina.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {
    public String paymentsPix(UserEntity user, Double total) {
        String txid = "";
        Credentials credentials = new Credentials();
        JSONObject options = createOptions(credentials);
        String existingKey = getOrCreatePixKey(options);

        if (existingKey == null) {
            existingKey = createPixKey(options);
        }

        int IdQrCode = createPix(options, existingKey, user, total);
        String base64Image = generateQRCode(options, IdQrCode);

        return base64Image;
    }

    private JSONObject createOptions(Credentials credentials) {
        JSONObject options = new JSONObject();
        options.put("client_id", credentials.getClientId());
        options.put("client_secret", credentials.getClientSecret());
        options.put("certificate", credentials.getCertificate());
        options.put("sandbox", credentials.isSandbox());
        return options;
    }

    private String getOrCreatePixKey(JSONObject options) {
        try {
            Gerencianet gn = new Gerencianet(options);
            JSONObject response = gn.call("pixListEvp", new HashMap<String, String>(), new JSONObject());
            JSONArray chaves = response.getJSONArray("chaves");
            if (chaves.length() > 0) {
                return chaves.getString(0);
            }
        } catch (GerencianetException e) {
            System.out.println(e.getError());
            System.out.println(e.getErrorDescription());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private String createPixKey(JSONObject options) {
        try {
            Gerencianet gn = new Gerencianet(options);
            JSONObject response = gn.call("pixCreateEvp", new HashMap<String, String>(), new JSONObject());
            JSONArray chaves = response.getJSONArray("chaves");
            if (chaves.length() > 0) {
                return chaves.getString(0);
            }
        } catch (GerencianetException e) {
            System.out.println(e.getError());
            System.out.println(e.getErrorDescription());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private int createPix(JSONObject options, String existingKey, UserEntity user, Double valor) {
        int IdQrCode = 0;
        JSONObject body = new JSONObject();
        body.put("calendario", new JSONObject().put("expiracao", 3600));
        body.put("devedor", new JSONObject().put("cpf", user.getCpf()).put("nome", user.getName()));
        body.put("valor", new JSONObject().put("original", String.format("%.2f", valor)));
        body.put("chave", existingKey);
        body.put("solicitacaoPagador", "Serviço realizado.");

        JSONArray infoAdicionais = new JSONArray();
        infoAdicionais.put(new JSONObject().put("nome", "Campo 1").put("valor", "Informação Adicional1 do PSP-Recebedor"));
        body.put("infoAdicionais", infoAdicionais);

        try {
            Gerencianet gn = new Gerencianet(options);
            JSONObject response = gn.call("pixCreateImmediateCharge", new HashMap<String, String>(), body);
            IdQrCode = response.getJSONObject("loc").getInt("id");

        } catch (GerencianetException e) {
            System.out.println(e.getError());
            System.out.println(e.getErrorDescription());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return IdQrCode;
    }

    private String generateQRCode(JSONObject options, int IdQrCode) {
        if (IdQrCode > 0) {
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("id", String.valueOf(IdQrCode));

            try {
                Gerencianet gn = new Gerencianet(options);
                Map<String, Object> response = gn.call("pixGenerateQRCode", params, new HashMap<String, Object>());

                String base64Image = (String) response.get("imagemQrcode");

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
