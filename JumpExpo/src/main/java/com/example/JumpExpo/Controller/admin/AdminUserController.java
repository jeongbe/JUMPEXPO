package com.example.JumpExpo.Controller.admin;

import com.example.JumpExpo.Entity.comuser.Company;
import com.example.JumpExpo.Entity.user.Users;
import com.example.JumpExpo.Repository.comuser.CompanyRepository;
import com.example.JumpExpo.Repository.user.UserReository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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


    //유저관리 페이지로 가는 매핑
    //2024-01-21 맹성우
    @GetMapping("/UserManage")
    public String usermanage(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Users users = userReository.finduser(username);
        model.addAttribute("users", users);



        //현재 가입된 유저 정보 가져오기
        ArrayList<Users> usersM = userReository.userM();
        model.addAttribute("usersM", usersM);


        return "admin/AdminUserManage/UserManage";
    }

    //2024-01-22 맹성우
    //기업관리 페이지 매핑

    @GetMapping("/ComManage")
    public String commanage(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Users users = userReository.finduser(username);
        model.addAttribute("users", users);



        //현재 가입된 유저 정보 가져오기
        ArrayList<Company> comM = companyRepository.comM();
        model.addAttribute("comM", comM);


        return "admin/AdminUserManage/ComManage";
    }
}
