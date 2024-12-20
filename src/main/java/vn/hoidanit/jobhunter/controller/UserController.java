package vn.hoidanit.jobhunter.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.service.UserService;
import vn.hoidanit.jobhunter.service.error.IdInValidException;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User Postuser) {

        User ericUser = this.userService.saveUser(Postuser);
        return ResponseEntity.ok(ericUser);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> list = this.userService.getAllUsers();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User ericUser = this.userService.getUserById(id);

        return ResponseEntity.ok(ericUser);
    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User Postuser) {
        User ericUser = this.userService.handleUpdateUser(Postuser);

        return ResponseEntity.ok(ericUser);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) throws IdInValidException {
        if (id >= 1500) {
            throw new IdInValidException("Id is invalid (<1500)");
        }
        this.userService.deleteUser(id);
        
        return ResponseEntity.ok("User deleted successfully");
    }

}
