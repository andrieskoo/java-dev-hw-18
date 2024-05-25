package com.homework18.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class SignupRequest {

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @Size(max = 50)
    private String username;

    private String role;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
}
