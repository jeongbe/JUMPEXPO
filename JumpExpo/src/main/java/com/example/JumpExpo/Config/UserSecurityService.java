package com.example.JumpExpo.Config;


import com.example.JumpExpo.DTO.Login.LoginForm;
import com.example.JumpExpo.Entity.comuser.Company;
import com.example.JumpExpo.Entity.user.Users;
import com.example.JumpExpo.Repository.comuser.CompanyRepository;
import com.example.JumpExpo.Repository.user.UserReository;
import com.example.JumpExpo.domain.Role;
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




    @Override
    public UserDetails loadUserByUsername(String user_id) throws UsernameNotFoundException {

        Users users = userReository.username(user_id);

        Company company = companyRepository.comname(user_id);


            //기업 or 개인 아이디가 아무도 없을경우
        if (users == null && company == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + user_id);
        }
        //유저에서 정보를 찾았을 경우
        else if (users != null){
            
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

            return User.builder().username(company.getCom_id()).password(company.getCom_pw()).roles(company.getRole().name()).build();
        }
        else {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + user_id);
        }


    }


//    @Override
//    public UserDetails loadUserByUsername(String user_id) throws UsernameNotFoundException{
//
//        log.info("User ID: {} is attempting to log in.", user_id);
//
////        log.info(passwordEncoder.encode("password"));
//
//        if (user_id == null || user_id.isEmpty()) {
//            throw new IllegalArgumentException("User ID cannot be null or empty");
//        }
//
//        Optional<Users> users = this.userReository.username(user_id);
//
//            //해당 아이디를 디비에서 못찾은 경우
//            if (users.isEmpty()){
//                throw new UsernameNotFoundException("사용자를 찾을 수 없습ㄴ디ㅏ");
//            }else {
//                //해당 아이디를 디비에서 찾은경우
//                Users users1 = users.get(); //해당 유저 엔티티를 optional 객체에서 꺼냄
//                log.info("User Password: {}", users1.getUser_pw());
//
//                List<GrantedAuthority> authorities = new ArrayList<>();
//                if ("ADMIN".equals(users1.getRole())) {
//                    authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getTitle()));
//                } else {
//                    authorities.add(new SimpleGrantedAuthority(Role.USER.getTitle()));
//                }
//
//                    return User.builder()
//                            .username(users1.getUser_id())
//                            .password(users1.getUser_pw())
//                            .roles(String.valueOf(users1.getRole()))
//                            .authorities(authorities)
//                            .build();
//
//                }
//
//            }


    }






