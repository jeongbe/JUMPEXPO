package com.example.JumpExpo.Controller.All;

import com.example.JumpExpo.Entity.comuser.ApplyEmploy;
import com.example.JumpExpo.Repository.comuser.ApplyEmployRepository;
import com.example.JumpExpo.Repository.user.PeremApplyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/JumpExpo")
public class AllEmployListController {

    @Autowired
    ApplyEmployRepository applyEmployRepository;

    @Autowired
    PeremApplyRepository peremApplyRepository;


    //공고 보기 리스트<분야별 리스트>
    @GetMapping("/show/employlist")
    public String empoyList(Model model){

        ArrayList<ApplyEmploy> allList = applyEmployRepository.AllEmployList();
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

        return "user/employ/EmployList";
    }

    // 공고 보기 상세 페이지
    @GetMapping("/show/employlist/{emnot_code}")
    public String employDetail(Model model, @PathVariable(name="emnot_code") int emnotCode){

        ApplyEmploy applyEmploy = applyEmployRepository.findById(emnotCode).orElse(null);

        if (applyEmploy == null) {
            // 데이터가 없을 경우
            return"/JumpExpo/show/employlist";
        }

        model.addAttribute("applyEmploy", applyEmploy);

        return "user/employ/EmployListDetail";
    }
}