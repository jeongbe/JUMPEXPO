package com.example.JumpExpo.Controller.admin;

import com.example.JumpExpo.Entity.comuser.ApplyEmploy;
import com.example.JumpExpo.Repository.comuser.ApplyEmployRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

//2024.01.09 박은채 채용 공고 심사 Controller
//채용 공고 심사 Controller
@Slf4j
@Controller
@RequestMapping("/admin")
public class EmployapplyController {

    @Autowired
    ApplyEmployRepository applyEmployRepository;


    // 심사전인 채용 신청 리스트
    @GetMapping("/show/employapply")
    public String recogeEmploy(Model model){

        ArrayList<ApplyEmploy> reqList = applyEmployRepository.AllreqEmployList();

        model.addAttribute("reqList", reqList);

        return "admin/employApply/Employapply";
    }

    // 심사 완료된 채용 신청 리스트
    @GetMapping("/show/disemployapply")
    public String disRecogeEmploy(Model model){

        ArrayList<ApplyEmploy> reqList = applyEmployRepository.AlldisreqEmployList();

        model.addAttribute("reqList", reqList);

        return "admin/employApply/Disemployapply";
    }
    //2024.01.10 박은채
    // 신청 공고 상세 페이지
    @GetMapping("/show/employapply/{emnot_code}")
    public String employDetail(Model model, @PathVariable(name="emnot_code") int emnotCode){

        ApplyEmploy applyEmploy = applyEmployRepository.findById(emnotCode).orElse(null);

        if (applyEmploy == null) {
            // 데이터가 없을 경우
            return "admin/show/employapply";
        }

        model.addAttribute("applyEmploy", applyEmploy);

        int recogCheck = applyEmploy.getRecog_check();

        if (recogCheck == 1 || recogCheck == 2) {
            return "admin/employApply/EmploydetailComplete";
        } else {
            return "admin/employApply/Employdetail";
        }
    }


    // 승인, 비승인 버튼 값 업데이트
    @PostMapping("/updateRecogCheck/{emnot_code}/{value}")
    public String updateRecogCheck(@PathVariable(name = "emnot_code") int emnotCode,
                                   @PathVariable(name = "value") int value) {
        ApplyEmploy applyEmploy = applyEmployRepository.findById(emnotCode).orElse(null);
        if (applyEmploy != null) {
            applyEmploy.setRecog_check(value);
            try {
                applyEmployRepository.save(applyEmploy);
                return "redirect:/admin/show/disemployapply";
            } catch (Exception e) {
                // 업데이트 실패한 경우 예외 처리 가능
                return "redirect:/admin/show/employapply";
            }
        }
        return "redirect:/admin/show/employapply";
    }
}
