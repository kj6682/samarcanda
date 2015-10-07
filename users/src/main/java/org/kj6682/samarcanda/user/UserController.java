package org.kj6682.samarcanda.user;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.inject.Inject;
import java.net.URI;

/**
 * Created by luigi on 30/09/15.
 */

@RestController
public class UserController {

    @Inject
    private UserRepository userRepository;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<Iterable<User>> getAllUsers() {

        Iterable<User> users = userRepository.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody User user) {

        user = userRepository.save(user);

        // Set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        responseHeaders.setLocation(newUserUri);

        return new ResponseEntity<>(user, responseHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable Long userId) {
        User p = userRepository.findOne(userId);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @RequestMapping(value="/users/{userId}", method=RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable Long userId) {
        // Save the entity
        User newUser = userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value="/users/{userId}", method=RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        userRepository.delete(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}//:)
