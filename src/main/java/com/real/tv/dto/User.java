package com.real.tv.dto;

import com.real.tv.enums.Status;
import lombok.Data;

/**
 * User DTO
 */
@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String role;
    private Status status;
}
