package ru.netology.diploma.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.netology.diploma.request.AuthRequest;
import ru.netology.diploma.response.AuthResponse;
import ru.netology.diploma.security.JWTUtil;


@RestController
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final JWTUtil jwtTokenUtil;

    @PostMapping(value = "/login")
    @ResponseStatus(value = HttpStatus.OK)
    public Object createAuthenticationToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication;
        authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest
                        .getLogin(), authRequest.getPassword()));

        if (authentication == null) {
            throw new BadCredentialsException("Bad credentials");
        }
        String jwt = jwtTokenUtil.generateToken((UserDetails) authentication.getPrincipal());
        return new AuthResponse(jwt);
    }

    @PostMapping("/logout")
    public ResponseEntity<HttpStatus> exit() {
        return ResponseEntity.ok(HttpStatus.OK);
    }
}