package com.example.JumpExpo.Config;


import com.example.JumpExpo.Controller.join.KakaoController;
import com.example.JumpExpo.DTO.Login.LoginForm;
import com.example.JumpExpo.Entity.comuser.Company;
import com.example.JumpExpo.Entity.user.Users;
import com.example.JumpExpo.Repository.comuser.CompanyRepository;
import com.example.JumpExpo.Repository.user.UserReository;
import com.example.JumpExpo.domain.Role;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.awt.datatransfer.DataFlavor;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserSecurityService implements UserDetailsService {

    private final UserReository userReository;

    private final  CompanyRepository companyRepository;



    //카카오 로그인시
    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException{

        Users users1 = userReository.kakao2(email);
        return User.builder().username(users1.getUser_id()).password(users1.getUser_pw()).roles(users1.getRole().name()).build();

    }


    @Override
    public UserDetails loadUserByUsername(String user_id) throws UsernameNotFoundException {

        //로그인시 입력된 id를 기준으로 유저정보가 있는지 가져오기
        Users users = userReository.username(user_id);

        Company company = companyRepository.comname(user_id);


            //기업 or 개인 아이디가 아무도 없을경우
        if (users == null && company == null){

            Users users1 = userReository.kakao2(user_id);
            return User.builder().username(users1.getUser_id()).password(users1.getUser_name()).roles(users.getRole().name()).build();
        }
        //유저에서 정보를 찾았을 경우
        else if (users != null){

            if (users.getUser_sec() == 0){
                throw new UsernameNotFoundException("탈퇴된 회원 입니다. " + user_id);
            }

            //유저코드가 1이라면 관리자 권한부여
            if (users.getUser_code() == 1){
                return User.builder().username(users.getUser_id()).password(users.getUser_pw()).roles("ADMIN").build();
            }
            //나머지는 일반 유저 권한 부여
            else {
                return User.builder().username(users.getUser_id()).password(users.getUser_pw()).roles(users.getRole().name()).build();

            }
            //기업에서 정보를 찾았을 경우
        }
        else if (company != null){

            if (company.getCom_sec() == 0){
                throw new UsernameNotFoundException("탈퇴된 회원 입니다. " + user_id);
            }

            return User.builder().username(company.getCom_id()).password(company.getCom_pw()).roles(company.getRole().name()).build();
        }
        else {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + user_id);
        }


    }


}






