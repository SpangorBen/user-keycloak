package com.javarta.ecommerce.user_keycloak.controllers;

import com.javarta.ecommerce.user_keycloak.dtos.UserRegistrationDTO;
import com.javarta.ecommerce.user_keycloak.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/key-users")
//@AllArgsConstructor
public class KeycloakUserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public UserRegistrationDTO createUser(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        return userService.registerUser(userRegistrationDTO);
    }

//    @PostMapping
//    public void createUser(@RequestBody UserRegistrationDTO userRegistrationDTO) {
//        System.out.println("User created successfully");
//    }

}
