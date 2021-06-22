package com.ynov.master.mobile.game.medieval.warfare.configuration;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
        ByteArrayOutputStream propertiesStream = new ByteArrayOutputStream();
        JsonFactory jsonFactory = new JsonFactory();
        JsonGenerator jGenerator = jsonFactory
                .createGenerator(propertiesStream, JsonEncoding.UTF8);

        jGenerator.writeStartObject();
        jGenerator.writeStringField("type",type);
        jGenerator.writeStringField("project_id",projectId);
        jGenerator.writeStringField("client_email",clientEmail);
        jGenerator.writeStringField("client_id",clientId);
        jGenerator.writeStringField("auth_uri",authUri);
        jGenerator.writeStringField("token_uri",tokenUri);
        jGenerator.writeStringField("auth_provider_x509_cert_url",authProviderX509CertUrl);
        jGenerator.writeStringField("client_x509_cert_url",clientX509CertUrl);
        jGenerator.writeStringField("private_key_id",privateKeyId);
        jGenerator.writeStringField("private_key",privateKey);
        jGenerator.writeEndObject();
        jGenerator.close();

        return new ByteArrayInputStream(propertiesStream.toByteArray());
    }

}
