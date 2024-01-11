package com.example.JumpExpo.Service.user.user;

import com.example.JumpExpo.DTO.user.UserForm;
import com.example.JumpExpo.Repository.user.UserReository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserReository userReository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder,UserReository userReository){
        this.passwordEncoder = passwordEncoder;
        this.userReository =userReository;
    }

        //유저 회원가입 서비스
    public String addUser(UserForm form){
        return userReository.save(form.toEntity(passwordEncoder)).getUser_name();
    }

}
