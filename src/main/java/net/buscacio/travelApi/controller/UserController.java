package net.buscacio.travelApi.controller;

import jakarta.validation.Valid;
import net.buscacio.travelApi.dto.UserRecord;
import net.buscacio.travelApi.dto.UserRequestRecord;
import net.buscacio.travelApi.entity.User;
import net.buscacio.travelApi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserRecord> findUserById(@PathVariable UUID userId) {
        var user = userService.findById(userId);
        return ResponseEntity.ok(new UserRecord(user));
    }

    @PostMapping
    public ResponseEntity<UserRecord> createUser(@RequestBody @Valid UserRequestRecord userRequestRecord, UriComponentsBuilder uriComponentsBuilder) {
        var user = new User(userRequestRecord);
        user.setCreationDate(LocalDateTime.now());
        user.setLastUpdateDate(LocalDateTime.now());
        userService.saveUser(user);
        var uri = uriComponentsBuilder.path("users/{userId}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(new UserRecord(user));
    }

    @GetMapping
    public ResponseEntity<Page<UserRecord>> findAll(@PageableDefault(size=5,sort={"name"})Pageable pageable) {
        Page<UserRecord> page = userService.findAll(pageable).map(UserRecord::new);
        return new ResponseEntity(page, HttpStatus.OK);
    }

}
