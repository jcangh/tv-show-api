package com.real.tv.dto;

import lombok.Data;

/**
 * Java object to request token
 */
@Data
public class TokenRequest {

    private String username;
    private String password;
}
