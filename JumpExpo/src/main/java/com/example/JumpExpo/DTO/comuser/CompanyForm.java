package com.example.JumpExpo.DTO.comuser;

import com.example.JumpExpo.Entity.comuser.Company;
import com.example.JumpExpo.domain.Role;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

//2024-01-08 기업 회원가입 폼 맹성우
@Data
@NoArgsConstructor
public class CompanyForm {

    String id;

    String password;

    String BRnumber;

    String Comname;

    String addr;

    String addr1;

    String homepage;

    String name;

    String phone;

    String Cominfo;

    String email;

    String Comlogo;

    int comsec = 1;


  @Builder
  public CompanyForm(String id, String password, String BRnumber, String comname, String addr, String addr1, String homepage, String name, String phone
  , String cominfo, String email, String comlogo, int comsec)  {

      this.id = id;
      this.password = password;
      this.BRnumber = BRnumber;
      this.Comname = comname;
      this.addr = addr;
      this.addr1 = addr1;
      this.homepage = homepage;
      this.name = name;
      this.phone = phone;
      this.Cominfo = cominfo;
      this.email = email;
      this.Comlogo = comlogo;
      this.comsec = comsec;
  }

  public Company toEntity(PasswordEncoder passwordEncoder){
      return Company.builder()
              .com_id(id)
              .com_pw(passwordEncoder.encode(password))
              .com_number(BRnumber)
              .com_name(Comname)
              .com_addr(addr)
              .com_addr1(addr1)
              .com_home(homepage)
              .com_manager(name)
              .manager_phone(phone)
              .com_intro(Cominfo)
              .com_email(email)
              .com_image(Comlogo)
              .com_sec(comsec)
              .role(Role.COM) //기업 권한 부여
              .build();
  }




//    public Company toEntity(){
//        return new Company(null,id, password,BRnumber,Comname,addr,addr1,homepage,name,phone,Cominfo,email,Comlogo,comsec);
//    }



}
