package com.real.tv.dto;

import lombok.Data;

/**
 * Token generation object response
 */
@Data
public class TokenResponse {

    private String token;

    public TokenResponse(String token){
        this.token = token;
    }
}
