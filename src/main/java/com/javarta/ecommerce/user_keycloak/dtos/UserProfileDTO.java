package com.javarta.ecommerce.user_keycloak.dtos;

import lombok.Data;

@Data
public class UserProfileDTO {
    private String firstName;
    private String lastName;
    private String email;
}