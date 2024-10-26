package andrehsvictor.revise.config;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {

    @Value("${revise.keycloak.server-url}")
    private String serverUrl;

    @Value("${revise.keycloak.realm}")
    private String realm;

    @Value("${revise.keycloak.client-id}")
    private String clientId;

    @Value("${revise.keycloak.client-secret}")
    private String clientSecret;

    @Value("${revise.keycloak.username}")
    private String username;

    @Value("${revise.keycloak.password}")
    private String password;

    
    @Bean
    Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .username(username)
                .password(password)
                .build();
    }

    @Bean
    RealmResource realmResource(Keycloak keycloak) {
        return keycloak.realm(realm);
    }
}
