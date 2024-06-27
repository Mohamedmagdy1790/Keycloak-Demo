package com.client;


import com.client.service.DemoRemoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/api/auth")
@RestController
public class DemoUIController {
    @Autowired
    DemoRemoteService demoRemoteService;


    @GetMapping("/flight")
    public ModelAndView home(
            OAuth2AuthenticationToken token,
            @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient client) {
             OidcUser principal = (OidcUser) token.getPrincipal ();

            ModelAndView model = generateDefaultModel(token);
            model.setViewName("home");
            String flightdata = demoRemoteService.getFlight();
             model.addObject ("flightdata",flightdata);
             return model;

    }

    private ModelAndView generateDefaultModel(OAuth2AuthenticationToken token) {

        OidcUser principal = (OidcUser) token.getPrincipal();

        ModelAndView model = new ModelAndView();
        model.addObject("user", principal);
        return model;
    }






}
