package com.javarta.ecommerce.user_keycloak.controllers;

import com.javarta.ecommerce.user_keycloak.dtos.PasswordResetDTO;
import com.javarta.ecommerce.user_keycloak.dtos.UserProfileDTO;
import com.javarta.ecommerce.user_keycloak.dtos.UserRegistrationDTO;
import com.javarta.ecommerce.user_keycloak.services.UserService;
import lombok.AllArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    @GetMapping("/{userId}")
    public ResponseEntity<UserRepresentation> getUserById(@PathVariable String userId) {
        UserRepresentation user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("profile/{userId}")
    public ResponseEntity<UserProfileDTO> getUserProfile(@PathVariable String userId) {
        UserProfileDTO userProfileDTO = userService.getUserProfile(userId);
        return new ResponseEntity<>(userProfileDTO, HttpStatus.OK);
    }


    @PutMapping("profile/{userId}")
    public ResponseEntity<String> updateUserProfile(@PathVariable String userId, @RequestBody UserProfileDTO userProfileDTO) {
        userService.updateProfile(userId, userProfileDTO);
        return ResponseEntity.ok("Profile updated successfully");
    }

    @PutMapping("/reset-password/{userId}")
    public ResponseEntity<String> resetPassword(@PathVariable String userId, @RequestBody PasswordResetDTO passwordDTO) {
        userService.resetPassword(userId, passwordDTO);
        return ResponseEntity.ok("Password reset successfully");
    }
}
