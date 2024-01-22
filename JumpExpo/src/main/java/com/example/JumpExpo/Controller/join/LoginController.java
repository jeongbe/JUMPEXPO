package com.example.JumpExpo.Controller.join;


import com.example.JumpExpo.Config.UserSecurityService;
import com.example.JumpExpo.DTO.Login.ChangePwForm;
import com.example.JumpExpo.DTO.Login.LoginForm;
import com.example.JumpExpo.DTO.Login.SearchPwForm;
import com.example.JumpExpo.DTO.Login.SearhIdForm;
import com.example.JumpExpo.Entity.comuser.Company;
import com.example.JumpExpo.Entity.user.Users;
import com.example.JumpExpo.Repository.comuser.CompanyRepository;
import com.example.JumpExpo.Repository.user.UserReository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpHeaders;
import java.net.http.HttpResponse;


//로그인 관련 컨트롤러 2024-01-08 맹성우
@Controller
@Slf4j
public class LoginController {

    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    UserReository userReository;

    @Autowired
    UserSecurityService userSecurityService;

    @Autowired
    PasswordEncoder passwordEncoder;


    @GetMapping("/JumpExpo/Login")
    public String login(){

        return "join/Login";
    }
    //로그인 틀렸을시
    @GetMapping("/JumpExpo/Loginfail")
    public String loginfail(Model model){

        String error = "아이디 또는 비밀번호를 확인하세요";
        model.addAttribute("error", error);


        return "join/Login";
    }

    //로그인을 체크하는 매핑
    @PostMapping("/JumpExpo/AppLogin")
    public String JumpLogin(LoginForm form) {

        return "";

    }

    //아이디 찾기
    @GetMapping("/JumpExpo/IdSearch")
    public String IdSearch(){
        return "join/SearchId";
    }

    //아이디 보여주기
    @PostMapping("/JumpExpo/ShowId")
    public String ShowId(SearhIdForm form, Model model){
    //유저인지 기업인지 구분하기 위해 두개를 가져옴
        String userid = userReository.userid(form.getEmail(), form.getName());
        String comid =  companyRepository.userid(form.getEmail(), form.getName());

        if (userid == null && comid == null) {
            return "/join/SearchId";
        }
        else {
            if (userid != null){

                model.addAttribute("userid", userid);

                return "join/ShowId";
            }
            else {
                model.addAttribute("comid",comid);

                return "join/ShowId";
            }
        }

    }

    //비밀번호 찾기
    @GetMapping("/JumpExpo/PwSearch")
    public String PwSearch(){
        return "join/ShowPw";
    }


    //비밀번호 변경 인증
    @PostMapping("/JumpExpo/SearchPw")
    public String SearchPw(SearchPwForm form, Model model){

        //유저인지 기업인지 구분하기 위해 두개를 가져옴

        String findpw = userReository.findpw(form.getId(), form.getName(), form.getEmail());
        String findCompw = companyRepository.findcompw(form.getId(), form.getName(), form.getEmail());

        if (findpw == null && findCompw == null){

            return "join/ShowPW";
        }
        else {
            if (findpw != null){
                model.addAttribute("findpw", findpw);
                return "join/ChangePw";
            }
            else {
                model.addAttribute("findCompw",findCompw);
                return "join/ChangePw";
            }
        }
    }

    @PostMapping("/JumpExpo/ChangePw")
    public String ChangePw(ChangePwForm form){

        //아이디 기준으로 유저 정보 가져옵니다.
        //기업, 유저 따로 조회해서 가져오기
        Users users = userReository.finduser(form.getId());
        Company company =companyRepository.findcom(form.getId());

        if (users != null){
            users.setUser_pw(passwordEncoder.encode(form.getPassword()));
            userReository.save(users);
            return "join/ChangeSuccess";
        }
        else if (company != null){
            company.setCom_pw(passwordEncoder.encode(form.getPassword()));
            companyRepository.save(company);

            return "join/ChangeSuccess";

        }
        else {
            return "join/ChangePw";
        }


    }


    @GetMapping("/JumpExpo/Success")
    public String success(){

        return "join/ChangeSuccess";
    }

    @GetMapping("/login/main")
    public String loginsmain(Model model){


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Users users = userReository.finduser(username);
        model.addAttribute("users", users);

        Company company = companyRepository.findcom(username);
        model.addAttribute("company", company);

        //권한에 따라 리다이렉트 페이지를 부여 할것임.
        if (users != null && users.getUser_code() > 1){
            return "redirect:/users/userMain";
        }
        else if (users != null && users.getUser_code() == 1){
            return "redirect:/admin/employapply/rec/list";
        }
        else if (users == null && company != null){
            return "redirect:/com/JumpExpo/main";
        }
        else {
            return "";
        }

    }

    @GetMapping("/users/userMain")
    public String usersMain(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Users users = userReository.finduser(username);
        model.addAttribute("users", users);


        return "user/userMain";
    }

    @GetMapping("/com/JumpExpo/main")
    public String comMainPage(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Company company = companyRepository.findcom(username);
        model.addAttribute("company", company);

        return "/comuser/comMain";
    }






}
