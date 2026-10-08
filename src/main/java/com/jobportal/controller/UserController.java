package com.jobportal.controller;

import com.jobportal.model.User;
import com.jobportal.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<User> getUsers(@RequestParam(required = false) String email, @RequestParam(required = false) String role) {
        if (email != null && !email.trim().isEmpty()) {
            String cleanEmail = email.trim();
            Optional<User> userOpt = userRepository.findByEmail(cleanEmail);
            if (!userOpt.isPresent()) {
                userOpt = userRepository.findByEmail(cleanEmail.toLowerCase());
            }
            return userOpt.map(Collections::singletonList).orElse(Collections.emptyList());
        }
        if (role != null && !role.trim().isEmpty()) {
            return userRepository.findByRole(role);
        }
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public User createUser(@RequestBody Map<String, Object> userData) {
        User user = new User();
        user.setId(userData.containsKey("id") && userData.get("id") != null ? userData.get("id").toString() : UUID.randomUUID().toString());
        user.setName(userData.containsKey("name") && userData.get("name") != null ? userData.get("name").toString() : "");
        user.setEmail(userData.containsKey("email") && userData.get("email") != null ? userData.get("email").toString() : "");
        user.setPassword(userData.containsKey("password") && userData.get("password") != null ? userData.get("password").toString() : "");
        user.setRole(userData.containsKey("role") && userData.get("role") != null ? userData.get("role").toString() : "seeker");

        if (userData.containsKey("profile") && userData.get("profile") != null) {
            user.setProfile(toJsonString(userData.get("profile")));
        }
        if (userData.containsKey("company") && userData.get("company") != null) {
            user.setCompany(toJsonString(userData.get("company")));
        }
        if (userData.containsKey("savedJobs") && userData.get("savedJobs") != null) {
            user.setSavedJobs(toJsonString(userData.get("savedJobs")));
        }

        return userRepository.save(user);
    }

    @RequestMapping(value = "/{id}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody Map<String, Object> updates) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = userOpt.get();
        if (updates.containsKey("name")) user.setName(updates.get("name") != null ? updates.get("name").toString() : user.getName());
        if (updates.containsKey("email")) user.setEmail(updates.get("email") != null ? updates.get("email").toString() : user.getEmail());
        if (updates.containsKey("password")) user.setPassword(updates.get("password") != null ? updates.get("password").toString() : user.getPassword());
        if (updates.containsKey("role")) user.setRole(updates.get("role") != null ? updates.get("role").toString() : user.getRole());

        if (updates.containsKey("profile")) {
            user.setProfile(updates.get("profile") != null ? toJsonString(updates.get("profile")) : null);
        }
        if (updates.containsKey("company")) {
            user.setCompany(updates.get("company") != null ? toJsonString(updates.get("company")) : null);
        }
        if (updates.containsKey("savedJobs")) {
            user.setSavedJobs(updates.get("savedJobs") != null ? toJsonString(updates.get("savedJobs")) : "[]");
        }

        return ResponseEntity.ok(userRepository.save(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    private String toJsonString(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        }
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            return obj.toString();
        }
    }
}
