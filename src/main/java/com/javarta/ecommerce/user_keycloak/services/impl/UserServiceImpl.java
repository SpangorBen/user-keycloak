package com.javarta.ecommerce.user_keycloak.services.impl;

import com.javarta.ecommerce.user_keycloak.dtos.PasswordResetDTO;
import com.javarta.ecommerce.user_keycloak.dtos.UserProfileDTO;
import com.javarta.ecommerce.user_keycloak.dtos.UserRegistrationDTO;
import com.javarta.ecommerce.user_keycloak.services.UserService;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Value("${keycloak.realm}")
    private String realm;

    private final Keycloak keycloak;

    public UserServiceImpl(Keycloak keycloak) {
        this.keycloak = keycloak;
    }


    @Override
    public UserRegistrationDTO registerUser(UserRegistrationDTO userRegistrationDTO){

        System.out.println("start of the method");
        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(userRegistrationDTO.getUsername());
        user.setEmail(userRegistrationDTO.getEmail());
        user.setFirstName(userRegistrationDTO.getFirstName());
        user.setLastName(userRegistrationDTO.getLastName());
        user.setEmailVerified(true);

        System.out.println("user enabled");

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setValue(userRegistrationDTO.getPassword());
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setTemporary(false);
        user.setCredentials(Collections.singletonList(credential));

        System.out.println("credential set");

        List<CredentialRepresentation> list = new ArrayList<>();
        list.add(credential);
        user.setCredentials(list);

        UsersResource users = getUsersResource();
        System.out.println("response: " + users);

        Response response = users.create(user);

        if (Objects.equals(response.getStatus(), 201)) {
            return userRegistrationDTO;
        } else {
            System.out.println("Error: " + response.getStatus());
        }

        return null;

    }

    private UsersResource getUsersResource() {
        RealmResource realm1 = keycloak.realm(realm);
        return realm1.users();
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
    public UserProfileDTO getUserProfile(String username) {
        return null;
    }

    @Override
    public void updateProfile(String username, UserProfileDTO profileDTO) {

    }

    @Override
    public void resetPassword(String username, PasswordResetDTO passwordDTO) {

    }




}
