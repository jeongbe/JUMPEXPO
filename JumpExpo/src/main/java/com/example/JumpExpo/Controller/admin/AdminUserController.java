package com.example.JumpExpo.Controller.admin;

import com.example.JumpExpo.Entity.comuser.Company;
import com.example.JumpExpo.Entity.user.UserReview;
import com.example.JumpExpo.Entity.user.Users;
import com.example.JumpExpo.Repository.comuser.CompanyRepository;
import com.example.JumpExpo.Repository.user.UserReository;
import com.example.JumpExpo.Service.admin.AdminUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Map;


//2024-01-21 맹성우
@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminUserController {


    @Autowired
    UserReository userReository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    AdminUserService adminUserService;


    //유저관리 페이지로 가는 매핑
    //2024-01-21 맹성우
    @GetMapping("/UserManage")
    public String usermanage(Model model,@RequestParam(value="page", defaultValue="0")int page, @RequestParam(value = "eventType", defaultValue="0") int eventType){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Users users = userReository.finduser(username);
        model.addAttribute("users", users);


//        //현재 가입된 유저 정보 가져오기
//        ArrayList<Users> usersM = userReository.userM();
//        model.addAttribute("usersM", usersM);

        Page<Users> usersM = null;

        usersM = adminUserService.getuserList(page,eventType);

        model.addAttribute("usersM",usersM);
        model.addAttribute("TotalPage",usersM.getTotalPages());


        return "admin/AdminUserManage/UserManage";
    }

    //2024-01-22 맹성우
    //유저를 탈퇴시키는 매핑
    @GetMapping("/UserResign/{usercode}")
    public String userResign(@PathVariable("usercode") int usercode, Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Users users = userReository.finduser(username);
        model.addAttribute("users", users);

        //유저 코드 기준으로 유저정보를 가져오기
        Users users1 = userReository.findById(usercode).orElse(null);
        //유저 탈퇴여부를 0으로 만들어 탈퇴로 만들기
        users1.setUser_sec(0);
        //탈퇴된 정보로 다시 저장
        userReository.save(users1);

        //탈퇴성공 메세지
        String success = "탈퇴 시키기 성공";
        model.addAttribute("success",success);


        return "redirect:/admin/UserManage?success=true";

    }

    //2024-01-22 맹성우
    //기업관리 페이지 매핑

    @GetMapping("/ComManage")
    public String commanage(Model model, @RequestParam(value="page", defaultValue="0")int page, @RequestParam(value = "eventType", defaultValue="0") int eventType){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Users users = userReository.finduser(username);
        model.addAttribute("users", users);


//        //현재 가입된 유저 정보 가져오기
//        ArrayList<Company> comM = companyRepository.comM();
//        model.addAttribute("comM", comM);

        Page<Company> comM = null;

        comM = adminUserService.getcomList(page,eventType);

        model.addAttribute("comM",comM);
        model.addAttribute("TotalPage",comM.getTotalPages());


        return "admin/AdminUserManage/ComManage";
    }

    //2024-01-22 맹성우
    //기업을 탈퇴시키는 매핑
    @GetMapping("/ComResign/{comcode}")
    public String comResign(@PathVariable("comcode") int comcode, Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Users users = userReository.finduser(username);
        model.addAttribute("users", users);

        //기업 코드 기준으로 기업정보를 가져오기
        Company company = companyRepository.findById(comcode).orElse(null);
        //기업 탈퇴여부를 0으로 만들어 탈퇴로 만들기
        company.setCom_sec(0);
        //탈퇴된 정보로 다시 저장
        companyRepository.save(company);

        //탈퇴성공 메세지
        String success = "탈퇴 시키기 성공";
        model.addAttribute("success",success);


        return "redirect:/admin/ComManage?success=true";

    }
}
