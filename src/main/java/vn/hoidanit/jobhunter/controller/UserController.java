package vn.hoidanit.jobhunter.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
public class UserController {
    
        private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

     @PostMapping("/user/create")
    public User createUser(@RequestBody User Postuser) {
      
      User ericUser =  this.userService.saveUser(Postuser);
        return ericUser;
    }

    @GetMapping("user/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return this.userService.getUserById(id);
    }

    @PutMapping("user")
    public User updateUser(@RequestBody User Postuser) {
        User ericUser = this.userService.handleUpdateUser(Postuser);
        
        return ericUser;
    }
    
}
