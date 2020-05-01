package com.real.tv.rest;
import com.real.tv.dto.TokenRequest;
import com.real.tv.dto.TokenResponse;
import com.real.tv.dto.User;
import com.real.tv.service.TokenService;
import com.real.tv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * User REST controller
 */
@RestController
@RequestMapping("v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity create(@RequestBody User user){
        Integer userId = userService.create(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(userId).toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping(path = "/{user-id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<User> findById(@PathVariable("user-id") Integer userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PatchMapping(path = "/{user-id}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity update(@PathVariable("user-id") Integer userId,@RequestBody User user) {
        userService.update(userId,user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{user-id}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity delete(@PathVariable("user-id") Integer userId) {
        userService.delete(userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/authenticate", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TokenResponse> authenticate(@RequestBody TokenRequest tokenRequest) {
        return ResponseEntity.ok(tokenService.generateToken(tokenRequest));
    }
}
