package com.lastcup.api.infrastructure.notification;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
@EnableConfigurationProperties(FirebaseProperties.class)
public class FirebaseConfig {

    private static final Logger log = LoggerFactory.getLogger(FirebaseConfig.class);

    private final FirebaseProperties properties;

    public FirebaseConfig(FirebaseProperties properties) {
        this.properties = properties;
    }

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        if (!FirebaseApp.getApps().isEmpty()) {
            return FirebaseApp.getInstance();
        }
        String credentialsPath = properties.credentialsPath();
        if (credentialsPath == null || credentialsPath.isBlank()) {
            log.error("Firebase credentials path is not configured. Set app.firebase.credentials-path.");
            throw new IllegalStateException("Firebase credentials path is required.");
        }
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(new FileInputStream(credentialsPath)))
                .build();
        return FirebaseApp.initializeApp(options);
    }

    @Bean
    public FirebaseMessaging firebaseMessaging(FirebaseApp firebaseApp) {
        return FirebaseMessaging.getInstance(firebaseApp);
    }
}
