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
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.IOUtils;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

/* Class to demonstrate use of Drive insert file API */
@Service
public class UploadDriveService {

    private static final String APPLICATION_NAME = "PersonniMoveis";
    /**
     * Global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    /**
     * Directory to store authorization tokens for this application.
     */
    private static final String TOKENS_DIRECTORY_PATH = "1//04qy0opY6HpCGCgYIARAAGAQSNwF-L9IrEGMxjOQlrf8YTUxQWnioug1A7OSJRE2LbR8IYvH95JDrFZJ4BPoRFeNqg16lUUg2wE8";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
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
        String folderId = "1sKGJEpo-_sG2ApMaSFyoTeDT9GpG6E9F";

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

        if (folderId != null) {
            fileMetadata.setParents(Collections.singletonList(folderId));
        }

        FileContent mediaContent = new FileContent("image/jpeg", tempFile);

        try {
            File file = service.files().create(fileMetadata, mediaContent)
                    .setFields("id")
                    .execute();
            System.out.println("File ID: " + file.getId());

            String url = "https://docs.google.com/uc?id="+file.getId();
            return url;
        } catch (GoogleJsonResponseException e) {
            System.err.println("Unable to upload file: " + e.getDetails());
            throw e;
        }


    }


    public static String updateDriveFile(String fileId, String imageUrl, String fileName) throws Exception {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        byte[] fileBytes = new byte[0];
        if (isBase64(imageUrl)) {
            fileBytes = convertBase64ToBytes(imageUrl);
        } else if (isUrl(imageUrl)) {
            fileBytes = convertUrlToBytes(imageUrl);
        } else {
            System.out.println("Invalid Image Data");
        }

        java.io.File tempFile = java.io.File.createTempFile("temp-file", ".tmp");
        tempFile.deleteOnExit();

        java.nio.file.Files.write(tempFile.toPath(), fileBytes);

        File fileMetadata = new File();
        fileMetadata.setName(fileName);

        FileContent mediaContent = new FileContent("image/jpeg", tempFile);

        try {
            File file = service.files().create(fileMetadata, mediaContent)
                    .setFields("id")
                    .execute();
            System.out.println("File ID: " + file.getId());

            String updatedUrl = "https://docs.google.com/uc?id=" + file.getId();
            return updatedUrl;
        } catch (GoogleJsonResponseException e) {
            System.err.println("Unable to update file: " + e.getDetails());
            throw e;
        }
    }


    private static boolean isBase64(String input) {
        // Verifica se a entrada começa com "data:image/"
        return input.startsWith("data:image/");
    }

    private static boolean isUrl(String input) {
        // Verifica se a entrada começa com "https" ou "http"
        return input.startsWith("https://") || input.startsWith("http://");
    }

    private static byte[] convertUrlToBytes(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        URLConnection conn = url.openConnection();
        conn.setRequestProperty("User-Agent", "Firefox");

        try (InputStream inputStream = conn.getInputStream()) {
            int n = 0;
            byte[] buffer = new byte[1024];
            while (-1 != (n = inputStream.read(buffer))) {
                output.write(buffer, 0, n);
            }
        }
        byte[] img = output.toByteArray();
        ByteBuffer imageBytes = ByteBuffer.wrap(img);

        return imageBytes.array();
    }

    private static byte[] convertBase64ToBytes(String base64Data) {
        return Base64.getDecoder().decode(base64Data.split(",")[1]);
    }




}
