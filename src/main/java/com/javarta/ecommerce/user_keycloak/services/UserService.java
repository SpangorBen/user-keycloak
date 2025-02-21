package com.javarta.ecommerce.user_keycloak.services;

import com.javarta.ecommerce.user_keycloak.dtos.PasswordResetDTO;
import com.javarta.ecommerce.user_keycloak.dtos.UserProfileDTO;
import com.javarta.ecommerce.user_keycloak.dtos.UserRegistrationDTO;
import org.keycloak.representations.idm.UserRepresentation;

public interface UserService {
    UserRegistrationDTO registerUser(UserRegistrationDTO registrationDTO);

    UserProfileDTO getUserProfile(String username);

    void updateProfile(String username, UserProfileDTO profileDTO);

    void resetPassword(String username, PasswordResetDTO passwordDTO);

    UserRepresentation getUserById(String userId);
    void deleteUser(String userId);
}
