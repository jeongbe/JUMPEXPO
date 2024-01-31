package com.example.JumpExpo.Controller.join;


import com.example.JumpExpo.DTO.comuser.CompanyForm;
import com.example.JumpExpo.DTO.user.UserForm;
import com.example.JumpExpo.Entity.comuser.Company;
import com.example.JumpExpo.Entity.user.Users;
import com.example.JumpExpo.Repository.comuser.CompanyRepository;
import com.example.JumpExpo.Repository.user.UserReository;
import com.example.JumpExpo.Service.comuser.CompanyService;
import com.example.JumpExpo.Service.user.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;


//회원가입 컨트롤러 2024-01-08 맹성우
@Controller
@Slf4j
public class JoinController {

    //유저 레포지토리 가져오기
    @Autowired
    UserReository userReository;
    //기업 레포지토리 가져오기
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    UserService userService;


    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    CompanyService companyService;


    //회원가입 유형 선택 페이지
    @GetMapping("/JumpExpo/JoinSelect")
    public String JumpSelect(){
        return "join/JoinSelect";
    }

    //개인용 회원가입 페이지로 가는 매핑
    @GetMapping("/JumpExpo/PerJoinPage")
    public String PerJoinPage(){
        return "join/PerJoin";
    }

    //아이디 중복체크 매핑(개인)
    @GetMapping("/JumpExpo/DupCheck")
    @ResponseBody // http 응답 본문이 json이 됨
    public ResponseEntity<?> checkDuplicate(@RequestParam("id") String id) {

        // 여기에서 아이디 중복 체크 로직을 수행하고 결과를 반환합니다.
        Users isDuplicate = userReository.DupCheck(id);
        Map<String, Users> response = new HashMap<>();
        response.put("isDuplicate", isDuplicate);


        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    //개인 회원가입 매핑
    @PostMapping("/JumpExpo/PerJoin")
    public String PerJoin(UserForm form){


        //유저 회원가입 서비스
        userService.addUser(form);

        return "join/Login";
    }

    //기업 회원가입 페이지로 가는 매핑
    @GetMapping("/JumpExpo/ComJoinPage")
    public String ComJoinPage(){

        return "join/ComJoin";
    }

    //아이디 중복체크 매핑(기업)
    @GetMapping("/JumpExpo/ComDupCheck")
    @ResponseBody // http 응답 본문이 json이 됨
    public ResponseEntity<?> checkComDuplicate(@RequestParam("id") String id) {

        // 여기에서 아이디 중복 체크 로직을 수행하고 결과를 반환합니다.
        Company isDuplicate = companyRepository.ComDupCheck(id);
        Map<String, Company> response = new HashMap<>();
        response.put("isDuplicate", isDuplicate);


        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    //기업 회원가입 매핑
    @PostMapping("/JumpExpo/ComJoin")
    public String ComJoin(CompanyForm form, @RequestParam("Comlogo") MultipartFile file){

        //서버 이미지링크 변수
        String link =  "\\\\192.168.2.3\\images\\a";


        try {
            if (file != null && !file.isEmpty()){
                Path filePath = Path.of(link,file.getOriginalFilename());
                Files.copy(file.getInputStream(),filePath, StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e){
            e.printStackTrace();

            return "";
        }

        //기업 회원가입 서비스
        companyService.addCom(form);


        return "join/Login";
    }















}
