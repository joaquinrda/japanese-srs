package es.jrdarcos.japanesesrs.controller;

import es.jrdarcos.japanesesrs.entity.User;
import es.jrdarcos.japanesesrs.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"http://localhost:4200"})
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@Valid @RequestBody User user) {
        userService.createUser(user);

        return ResponseEntity.noContent().build();
    }
}
