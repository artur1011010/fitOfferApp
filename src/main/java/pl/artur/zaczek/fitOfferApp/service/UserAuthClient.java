package pl.artur.zaczek.fitOfferApp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pl.artur.zaczek.fitOfferApp.rest.model.auth.AuthorizationDto;

@Service
@Slf4j
public class UserAuthClient {
    private final WebClient webClient;
    private final String authUrl = "http://localhost:8082/api/v1/auth";
    private final String AUTHORIZATION_HEADER = "Authorization";


    public UserAuthClient(final WebClient.Builder webClientBuilder) {
        log.info("Creating userAuth client with url: {}" , authUrl);
        this.webClient = webClientBuilder.baseUrl(authUrl).build();
    }

    public AuthorizationDto authorize(final String token) {
        return this.webClient.get().uri("/authorize", token)
                .header(AUTHORIZATION_HEADER, token)
                .retrieve().bodyToMono(AuthorizationDto.class)
                .block();
    }
}
