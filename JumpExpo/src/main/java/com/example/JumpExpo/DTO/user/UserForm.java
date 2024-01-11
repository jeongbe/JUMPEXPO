package com.example.JumpExpo.DTO.user;

import com.example.JumpExpo.Entity.user.Users;

import com.example.JumpExpo.domain.Role;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

//유저 회원가입 dto 2024-01-08 맹성우
@Getter
@Setter
@NoArgsConstructor
public class UserForm {

    String userid;

    String password;

    String email;

    String addr;

    String addr1;

    String sex;

    String birth;

    String name;

    String phone;

    String oauth2 = "not yet";

    int usersec = 1;


  @Builder
    public UserForm(String userid, String password, String email, String addr, String addr1, String sex, String birth, String name, String phone, String oauth2, int usersec){
      this.userid = userid;
      this.password = password;
      this.email = email;
      this.addr = addr;
      this.addr1 = addr1;
      this.sex = sex;
      this.birth = birth;
      this.name = name;
      this.phone = phone;
      this.oauth2 =oauth2;
      this.usersec =usersec;

  }

  public Users toEntity(PasswordEncoder passwordEncoder){
      return Users.builder()
              .user_id(userid)
              .user_pw(passwordEncoder.encode(password))
              .user_email(email)
              .user_addr(addr)
              .user_deaddr(addr1)
              .user_sex(sex)
              .user_birth(birth)
              .user_name(name)
              .user_phone(phone)
              .oauth2(oauth2)
              .user_sec(usersec)
              .role(Role.USER)  //개인 권한 부여
              .build();
  }

//  public  Users toEntity(){
//      return Users(null, userid, password, email, addr, addr1, sex, birth, name, phone, oauth2, usersec, userid);
//  }





}
