package com.lys.schedulemanagement.user;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    public UserController userController(UserService userService){
        this.userService = userService;
    }
}
