package com.example.JumpExpo.Controller.comuser;

import com.example.JumpExpo.Entity.comuser.Company;
import com.example.JumpExpo.Entity.user.QnA;
import com.example.JumpExpo.Entity.user.Users;
import com.example.JumpExpo.Repository.comuser.CompanyRepository;
import com.example.JumpExpo.Repository.user.QnARepository;
import com.example.JumpExpo.Repository.user.UserReository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("/com")
public class ComQnAController {

    @Autowired
    UserReository userReository;

    @Autowired
    QnARepository qnARepository;

    @Autowired
    CompanyRepository companyRepository;

    //질문 리스트
    //기업용 수정하세요
    @GetMapping("/show/qu")
    public String QuList(Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Company company = companyRepository.findcom(username);
        model.addAttribute("company", company);

        ArrayList<QnA> QnAList = qnARepository.findAll();
        model.addAttribute("QnAList", QnAList);
        return  "comuser/qna/Qna_List";
    }
}
