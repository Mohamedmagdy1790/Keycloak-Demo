package org.example.kyclockdemo.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class FlightSearchController {

    @GetMapping("/flight")
    public String getFlights() {

        Authentication auth = SecurityContextHolder.getContext ().getAuthentication ();
        if(auth != null && auth.getPrincipal () instanceof Jwt){
            System.out.println (auth.getPrincipal ().toString ());
            Jwt jwt = (Jwt) auth.getPrincipal ();
            String username = jwt.getClaimAsString ("preferred_username");
            System.out.println ("Username: " + username);

        }else{
            System.out.println ("Authentication object is null or not an instance of Jwt");

        }
        // Call using rest template or delegate responsibility to return data via its controller
        return "dump flight data";
    }

}