package com.example.JumpExpo.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class testController {

    @GetMapping("/users/JumpExpo/main")
    public String userMainPage(){

        return "/user/userMain";
    }

    @GetMapping("/com/JumpExpo/main")
    public String comMainPage(){

        return "/comuser/comMain";
    }
}
