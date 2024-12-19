package vn.hoidanit.jobhunter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.service.UserService;

@RestController
public class HelloController {
    private final UserService userService;

    public HelloController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getHelloWorld() {
        return "Hello World (Hỏi Dân IT & Eric)";
    }

    @GetMapping("/user/create")
    public String createUser() {
        User user = new User();
        user.setName("Hỏi Dân IT");
        user.setEmail("devjadoj@ozfud.tp");
        user.setPassword("123456");
        this.userService.saveUser(user);
        return "ok";
    }
}
