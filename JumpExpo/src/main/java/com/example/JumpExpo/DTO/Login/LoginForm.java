package com.example.JumpExpo.DTO.Login;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

//로그인 데이터를 받기위한 폼
//2024-01-08 맹성우
@Data
public class LoginForm {

    String user_id;

    String password;

    String comper;
}
