package com.example.JumpExpo.Service.comuser;

import com.example.JumpExpo.DTO.Login.LoginForm;
import com.example.JumpExpo.DTO.comuser.CompanyForm;
import com.example.JumpExpo.DTO.user.UserForm;
import com.example.JumpExpo.Repository.comuser.CompanyRepository;
import com.example.JumpExpo.Repository.user.UserReository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CompanyService( PasswordEncoder passwordEncoder, CompanyRepository companyRepository){
        this.passwordEncoder = passwordEncoder;

        this.companyRepository =companyRepository;
    }

    //기업 회원가입 서비스

    public String addCom(CompanyForm form){
        return companyRepository.save(form.toEntity(passwordEncoder)).getCom_name();
    }

    //기업 로그인 서비스


}
