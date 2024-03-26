package ru.netology.diploma.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    @JsonProperty("auth-token")
    private String jwtToken;
}