package com.example.JumpExpo.Controller.admin;

import com.example.JumpExpo.Entity.admin.ScheduleInsert;
import com.example.JumpExpo.Entity.comuser.ApplyEmploy;
import com.example.JumpExpo.Repository.admin.SchInsetExpoRepository;
import com.example.JumpExpo.Repository.comuser.ApplyEmployRepository;
import com.example.JumpExpo.Repository.comuser.ExpoAppComRepository;
import com.example.JumpExpo.Repository.etc.ExpoCalenderInfoRepository;
import com.example.JumpExpo.Service.user.expo.ExpoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//2024.01.09 박은채 채용 공고 심사 Controller
//채용 공고 심사 Controller
@Slf4j
@Controller
@RequestMapping("/admin")
public class EmployapplyController {

    @Autowired
    ApplyEmployRepository applyEmployRepository;

    @Autowired
    ExpoService expoService;

    @Autowired
    ExpoAppComRepository expoAppComRepository;

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


    //2024.01.12 정정빈
    //채용 박람회 심사 목록 리스트
// 수정된 Java 코드
    @GetMapping("/employapply/rec/list")
    public String EmployApplyRecList(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        Page<ScheduleInsert> RecList = expoService.getRecList(page);
        log.info(RecList.getContent().toString());
        model.addAttribute("RecList", RecList);

        List<Integer> comCountList = new ArrayList<>(); // 변경된 부분

        for (ScheduleInsert rec : RecList) {
            int comCount = expoAppComRepository.getComCount(rec.getExpo_code());
//            log.info(String.valueOf(comCount));
            comCountList.add(comCount); // 변경된 부분
        }

        model.addAttribute("comCount", comCountList); // 변경된 부분

        return "admin/employApply/RecExpoAuditList";
    }

}
