package com.example.JumpExpo.Controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserMyPageController {

    //2024.01.15
    //면접 일정 관리
    @GetMapping("/interv/calen/{user_code}")
    public String UserInterview(){

        return "user/Mypage/Interview";
    }

}
