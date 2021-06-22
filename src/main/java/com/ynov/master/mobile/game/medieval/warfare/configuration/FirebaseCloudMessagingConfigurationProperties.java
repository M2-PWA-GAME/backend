package com.ynov.master.mobile.game.medieval.warfare.configuration;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.JsonObject;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Data
@ConfigurationProperties(prefix = "firebase-cloud-messaging")
public class FirebaseCloudMessagingConfigurationProperties {

    String type;

    String projectId;

    String clientEmail;

    String clientId;

    String authUri;

    String tokenUri;

    String authProviderX509CertUrl;

    String clientX509CertUrl;

    String privateKeyId;

    String privateKey;

    String timeToLive;


    public InputStream toCredentialStream() throws IOException {

        JsonObject properties = new JsonObject();
        properties.addProperty("type",type);
        properties.addProperty("project_id",projectId);
        properties.addProperty("client_email",clientEmail);
        properties.addProperty("client_id",clientId);
        properties.addProperty("auth_uri",authUri);
        properties.addProperty("token_uri",tokenUri);
        properties.addProperty("auth_provider_x509_cert_url",authProviderX509CertUrl);
        properties.addProperty("client_x509_cert_url",clientX509CertUrl);
        properties.addProperty("private_key_id",privateKeyId);
        properties.addProperty("private_key",privateKey);

        return new ByteArrayInputStream(properties.toString().getBytes());
    }

}
