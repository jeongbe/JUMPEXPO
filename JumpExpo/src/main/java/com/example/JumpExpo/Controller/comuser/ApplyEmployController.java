package com.example.JumpExpo.Controller.comuser;

import com.example.JumpExpo.DTO.comuser.EmployForm;
import com.example.JumpExpo.Entity.comuser.ApplyEmploy;
import com.example.JumpExpo.Entity.comuser.Company;
import com.example.JumpExpo.Entity.user.Users;
import com.example.JumpExpo.Repository.comuser.ApplyEmployRepository;
import com.example.JumpExpo.Repository.comuser.CompanyRepository;
import com.example.JumpExpo.Repository.user.PeremApplyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

//2024.01.08 박은채 채용 공고 신청 Controller
//채용 공고 신청 Controller
@Slf4j
@Controller
@RequestMapping("/com")
public class ApplyEmployController {

    @Autowired
    ApplyEmployRepository applyEmployRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    PeremApplyRepository peremApplyRepository;

    //채용 공고 신청 페이지
    @GetMapping("/insert/employ")
    public String insertEmploy(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Company company = companyRepository.findcom(username);
        model.addAttribute("company", company);


        model.addAttribute("comCode", company.getCom_code());

        return "comuser/applyemploy/ApplyEmploy";
    }

    @PostMapping("/save/employ")
    public String saveEmploy(EmployForm form){

        log.info(form.toString());

        ApplyEmploy data = form.toEnttiy();
        // 디폴트 값 0 고정 > 비승인시 1, 승인시 1로 변경예정
        data.setRecog_check(0);
        ApplyEmploy save = applyEmployRepository.save(data);
        log.info(save.toString());

        return "";
    }

    @GetMapping("/show/employlist")
    public String empoyList(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Company company = companyRepository.findcom(username);
        model.addAttribute("company", company);


        ArrayList<ApplyEmploy> allList = applyEmployRepository.AllEmployList();
        log.info(allList.toString());
        ArrayList<ApplyEmploy> DesignList = applyEmployRepository.DesignEmployList();
        ArrayList<ApplyEmploy> FrontList = applyEmployRepository.FrontEmployList();
        ArrayList<ApplyEmploy> BackendList = applyEmployRepository.BackendEmployList();
        ArrayList<ApplyEmploy> EtcList = applyEmployRepository.EtcEmployList();

        model.addAttribute("allList", allList);
        model.addAttribute("designList", DesignList);
        model.addAttribute("frontList", FrontList);
        model.addAttribute("backendList", BackendList);
        model.addAttribute("etcList", EtcList);

        // 각 공고별 지원자 수
        model.addAttribute("applicantCounts", allList.stream()
                .collect(Collectors.toMap(ApplyEmploy::getEmnot_code, e -> peremApplyRepository.countByEmnotCode(e.getEmnot_code()))));

        return "comuser/employ/EmployList";
    }

    @GetMapping("/show/employlist/{emnot_code}/{com_code}")
    public String employDetail(Model model, @PathVariable(name="emnot_code") int emnotCode,
                               @PathVariable(name = "com_code") int comCode){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Company company = companyRepository.findcom(username);
        model.addAttribute("company", company);

        ApplyEmploy applyEmploy = applyEmployRepository.findById(emnotCode).orElse(null);
        Company cpy = companyRepository.findById(comCode).orElse(null);

        if (applyEmploy == null) {
            // 데이터가 없을 경우
            return "com/show/employlist";
        }

        model.addAttribute("applyEmploy", applyEmploy);
        model.addAttribute("cpy",cpy);

        return "comuser/employ/EmployListDetail";
    }
}
