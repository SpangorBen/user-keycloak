package com.javarta.ecommerce.user_keycloak.services.impl;

import com.javarta.ecommerce.user_keycloak.dtos.PasswordResetDTO;
import com.javarta.ecommerce.user_keycloak.dtos.UserProfileDTO;
import com.javarta.ecommerce.user_keycloak.dtos.UserRegistrationDTO;
import com.javarta.ecommerce.user_keycloak.services.UserService;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {

    @Value("${keycloak.realm}")
    private String realm;

    @Autowired
    private Keycloak keycloak;


    @Override
    public UserRegistrationDTO registerUser(UserRegistrationDTO userRegistrationDTO) {
        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(userRegistrationDTO.getUsername());
        user.setEmail(userRegistrationDTO.getEmail());
        user.setFirstName(userRegistrationDTO.getFirstName());
        user.setLastName(userRegistrationDTO.getLastName());
        user.setEmailVerified(true);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setValue(userRegistrationDTO.getPassword());
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setTemporary(false);
        user.setCredentials(Collections.singletonList(credential));

        Response response = getUsersResource().create(user);

        if (response.getStatus() == 201) {
            return userRegistrationDTO;
        } else {
            System.out.println("Error: " + response.getStatus());
            return null;
        }
    }

    @Override
    public UserRepresentation getUserById(String userId) {
        return getUsersResource().get(userId).toRepresentation();
    }

    @Override
    public void deleteUser(String userId) {
        getUsersResource().delete(userId);
    }

    @Override
    public UserProfileDTO getUserProfile(String userId) {
        UserResource userResource = getUsersResource().get(userId);
        UserRepresentation userRepresentation = userResource.toRepresentation();

        UserProfileDTO profile = new UserProfileDTO();
        profile.setFirstName(userRepresentation.getFirstName());
        profile.setLastName(userRepresentation.getLastName());
        profile.setEmail(userRepresentation.getEmail());
        return profile;
    }

    @Override
    public void updateProfile(String userId, UserProfileDTO profileDTO) {
        UserResource userResource = getUsersResource().get(userId);
        UserRepresentation user = userResource.toRepresentation();
        user.setFirstName(profileDTO.getFirstName());
        user.setLastName(profileDTO.getLastName());
        user.setEmail(profileDTO.getEmail());
        getUsersResource().get(userId).update(user);
    }

    @Override
    public void resetPassword(String userId, PasswordResetDTO passwordDTO) {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(false);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(passwordDTO.getNewPassword());
        UserResource userResource = getUsersResource().get(userId);
        userResource.resetPassword(credential);
    }

    private UsersResource getUsersResource() {
        RealmResource realm1 = keycloak.realm(realm);
        return realm1.users();
    }

}