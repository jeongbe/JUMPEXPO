package com.example.JumpExpo.Entity.comuser;

import com.example.JumpExpo.domain.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//2024-01-08 맹성우 기업테이블 생성

@Entity
@Builder
@Data
@Table(name = "company")
@NoArgsConstructor
@AllArgsConstructor
public class Company {
    //기업코드
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer com_code;
    //기업아이디
    @Column
  private  String com_id;
    //기업 비밀번호
    @Column
  private  String com_pw;
    //사업자 번호
    @Column
   private String com_number;
    //회사명
    @Column
   private String com_name;
    //회사주소
    @Column
  private  String com_addr;
    //회사주소 상세
    @Column
   private String com_addr1;
    //회사 홈페이지
    @Column
   private String com_home;
    //담당자
    @Column
   private String com_manager;
    //담당자 번호
    @Column
   private  String manager_phone;
    //회사소개
    @Column
   private  String com_intro;
    //회사메일
    @Column
    private String com_email;
    //회사 이미지
    @Column
    private String com_image;
    //탈퇴여부
    @Column
    private int com_sec;
    //권한부여
    @Column
    private Role role;


}
