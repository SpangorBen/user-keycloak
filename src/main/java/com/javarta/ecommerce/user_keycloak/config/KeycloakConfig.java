package com.javarta.ecommerce.user_keycloak.config;

import org.bouncycastle.jcajce.provider.digest.Keccak;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {

    @Value("${keycloak.auth-server-url}")
    private String serverURL;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.resource}")
    private String clientID;

    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    @Bean
    public Keycloak keycloak(){
        return KeycloakBuilder.builder()
                .serverUrl(serverURL)
                .realm(realm)
                .clientId(clientID)
                .clientSecret(clientSecret)
                .grantType("client_credentials")
                .build();
    }
}
