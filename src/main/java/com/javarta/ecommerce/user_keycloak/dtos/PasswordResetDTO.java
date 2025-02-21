package com.javarta.ecommerce.user_keycloak.dtos;

import lombok.Data;

@Data
public class PasswordResetDTO {
    private String oldPassword;
    private String newPassword;
}