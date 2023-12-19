package com.br.personniMoveis.service;

import com.br.personniMoveis.DriveQuickstart;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

@Service
public class UploadDriveService {

    private static final String APPLICATION_NAME = "PersonniMoveisWeb";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = ""; // Removido para disponibilização do repositório de forma aberta.
    private static final List<String> SCOPES = Arrays.asList(DriveScopes.DRIVE_FILE, DriveScopes.DRIVE_APPDATA, DriveScopes.DRIVE_METADATA);
    private static final String CREDENTIALS_FILE_PATH = "/credentialsDrive.json";

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
            throws IOException {
        // Load client secrets.
        InputStream in = DriveQuickstart.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
        //returns an authorized Credential object.
        return credential;
    }

    public static String uploadBase64File(String base64String, String fileName) throws IOException, GeneralSecurityException {
        String folderId = ""; // Removido para disponibilização do repositório de forma aberta.

        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        String[] bytes = base64String.split(",");
        byte[] fileBytes = Base64.getDecoder().decode(bytes[1]);

        java.io.File tempFile = java.io.File.createTempFile("temp-file", ".tmp");
        tempFile.deleteOnExit();

        java.nio.file.Files.write(tempFile.toPath(), fileBytes);

        File fileMetadata = new File();
        fileMetadata.setName(fileName);

        fileMetadata.setParents(Collections.singletonList(folderId));

        FileContent mediaContent = new FileContent("image/jpeg", tempFile);

        try {
            File file = service.files().create(fileMetadata, mediaContent)
                    .setFields("id")
                    .execute();
            System.out.println("File ID: " + file.getId());

            return "https://docs.google.com/uc?id="+file.getId();
        } catch (GoogleJsonResponseException e) {
            System.err.println("Unable to upload file: " + e.getDetails());
            throw e;
        }


    }

    public static String updateDriveFile(String imageUrl, String fileName) throws Exception {
        try {

            byte[] fileBytes;

            if (isBase64(imageUrl)) {
                fileBytes = convertBase64ToBytes(imageUrl);
            } else if (isUrl(imageUrl)) {
                return imageUrl;
            } else {
                System.out.println("Invalid Image Data");
                return null;
            }

            String folderId = ""; // Removido para disponibilização do repositório de forma aberta.
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                    .setApplicationName(APPLICATION_NAME)
                    .build();



            java.io.File tempFile = java.io.File.createTempFile("temp-file", ".tmp");
            tempFile.deleteOnExit();

            Files.write(tempFile.toPath(), fileBytes);

            File fileMetadata = new File();
            fileMetadata.setName(fileName);
            fileMetadata.setParents(Collections.singletonList(folderId));

            FileContent mediaContent = new FileContent("image/jpeg", tempFile);



            // Cria um novo arquivo na pasta desejada
            File file = service.files().create(fileMetadata, mediaContent)
                    .setFields("id")
                    .execute();

            System.out.println("New File ID: " + file.getId());

            return "https://docs.google.com/uc?id=" + file.getId();
        } catch (IOException e) {
            System.err.println("Erro ao tentar atualizar o arquivo: " + e.getMessage());
            throw e;
        }
    }

    private static boolean isBase64(String input) {
        // Verifica se a entrada começa com "data:image/"
        return input != null && input.startsWith("data:image/");
    }

    private static boolean isUrl(String input) {
        // Verifica se a entrada começa com "https" ou "http"
        return input != null && (input.startsWith("https://") || input.startsWith("http://"));
    }

    private static byte[] convertBase64ToBytes(String base64Data) {
        return Base64.getDecoder().decode(base64Data.split(",")[1]);
    }

}
