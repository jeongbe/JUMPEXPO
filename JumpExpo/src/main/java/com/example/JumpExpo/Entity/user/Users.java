package com.example.JumpExpo.Entity.user;


import com.example.JumpExpo.Controller.join.OAuthToken;
import com.example.JumpExpo.domain.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

//2024-01-08 맹성우 유저 테이블 생성
//유저정보 테이블

@Entity
@Builder
@Data
@Table(name = "usertable")
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Users {
    //유저 코드
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer user_code;
    //유저 아이디
    @Column
    private  String user_id;
    //유저 패스워드
    @Column
    private String user_pw;
    //유저 이메일
    @Column
    private String user_email;
    //유저 주소
    @Column
    private String user_addr;
    //상세주소
    @Column
    private String user_deaddr;
    //성별
    @Column
    private String user_sex;
    //생년월일
    @Column
    private String user_birth;
    //유저 이름
    @Column
    private String user_name;
    //유저 전화번호
    @Column
    private String user_phone;
    //인증컬럼
    @Column
    private String oauth2;
    //탈퇴여부
    @Column
    private int user_sec;
    //유저 권한
    @Column
    private Role role;




}
