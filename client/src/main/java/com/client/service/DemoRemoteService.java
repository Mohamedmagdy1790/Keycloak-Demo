package com.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class DemoRemoteService {

    // Root URL for API
    @Value("${bugtracker.api.url}")
    private String API_URL;

    @Autowired
    private OAuth2AuthorizedClientService azdCliService;

    private final RestClient apiClient = RestClient.create();


    public String getFlight() {
        String token = getAccessToken();
        return apiClient
                .get()
                .uri(API_URL+"/api/auth/flight")
                .header("Authorization", "bearer " + token)
                .retrieve()
                .body (String.class)  ;// Retrieve the body as a Mono<String>
                  // Block to get the result synchronously
    }





//   // no acces token required because it is rquire  clint crdianl role not user
//    public BugStatistics getBugStatistics(String token) {
//        return apiClient
//                .get()
//                .uri(API_URL + "/statistics")
//                .header("Authorization", "bearer " + token)
//                .retrieve()
//                .body(BugStatistics.class);
//    }

    private String getAccessToken() {
        var authn = (OAuth2AuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
        String authzdId = authn.getAuthorizedClientRegistrationId();
        String name = authn.getName();

        OAuth2AuthorizedClient authzdCli = azdCliService.loadAuthorizedClient(authzdId, name);
        OAuth2AccessToken token = authzdCli.getAccessToken();

        String tokenValue = token.getTokenValue();
        System.out.println("** TOKEN = " + tokenValue);

        return tokenValue;
    }

}
