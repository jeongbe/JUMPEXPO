package com.example.JumpExpo.Controller.admin;

import com.example.JumpExpo.Entity.admin.ScheduleInsert;
import com.example.JumpExpo.Entity.comuser.ApplyEmploy;
import com.example.JumpExpo.Entity.comuser.Company;
import com.example.JumpExpo.Entity.comuser.ExpoAppCom;
import com.example.JumpExpo.Repository.admin.SchInsetExpoRepository;
import com.example.JumpExpo.Repository.comuser.ApplyEmployRepository;
import com.example.JumpExpo.Repository.comuser.CompanyRepository;
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

    @Autowired
    CompanyRepository companyRepository;

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
    @GetMapping("/show/employapply/{emnot_code}/{com_code}")
    public String employDetail(Model model, @PathVariable(name="emnot_code") int emnotCode
            , @PathVariable(name="com_code") int comCode){

        ApplyEmploy applyEmploy = applyEmployRepository.findById(emnotCode).orElse(null);
        Company company = companyRepository.findById(comCode).orElse(null);

        if (applyEmploy == null) {
            // 데이터가 없을 경우
            return "admin/show/employapply";
        }

        model.addAttribute("applyEmploy", applyEmploy);
        model.addAttribute("company",company);

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
//        log.info(RecList.getContent().toString());
        model.addAttribute("RecList", RecList);
        model.addAttribute("TotalPage",RecList.getTotalPages());

        List<Integer> comCountList = new ArrayList<>();

        for (ScheduleInsert rec : RecList) {
            int comCount = expoAppComRepository.getComCount(rec.getExpo_code());
//            log.info(String.valueOf(comCount));
            comCountList.add(comCount);
        }

        model.addAttribute("comCount", comCountList);

        return "admin/employApply/RecExpoAuditList";
    }
    
    //2024.01.15 정정빈
    //페어 박람회 심사 목록 리스트
    @GetMapping("/employapply/fair/list")
    public String EmployapplyFairList(Model model, @RequestParam(value = "page", defaultValue = "0") int page){
        Page<ScheduleInsert> FairList = expoService.getFairList(page);
        model.addAttribute("FairList",FairList);
        model.addAttribute("TotalPage",FairList.getTotalPages());

        List<Integer> comCountList = new ArrayList<>();

        for (ScheduleInsert fair : FairList) {
            int comCount = expoAppComRepository.getComCount(fair.getExpo_code());
//            log.info(String.valueOf(comCount));
            comCountList.add(comCount);
        }

        model.addAttribute("comCount", comCountList);

        return "admin/employApply/FairExpoAuditList";
    }

    //2024.01.15 정정빈
    //취업 박람회 심사 목록 리스트
    @GetMapping("/employapply/emp/list")
    public String EmployapplyEmpList(Model model, @RequestParam(value = "page", defaultValue = "0") int page){
        Page<ScheduleInsert> EmpList = expoService.getEmpList(page);
        model.addAttribute("EmpList",EmpList);
        model.addAttribute("TotalPage",EmpList.getTotalPages());

        List<Integer> comCountList = new ArrayList<>();

        for (ScheduleInsert emp : EmpList) {
            int comCount = expoAppComRepository.getComCount(emp.getExpo_code());
//            log.info(String.valueOf(comCount));
            comCountList.add(comCount);
        }

        model.addAttribute("comCount", comCountList);

        return "admin/employApply/EmpExpoAuditList";
    }

    //2024.01.15 정정빈
    //박람회 기업 심사 목록 리스트 (심사전)
    @GetMapping("/show/employapply/com/{expo_code}")
    public String ShowEmployApply(@PathVariable("expo_code")  int expoCode,Model model, @RequestParam(value = "page", defaultValue = "0") int page){

    Page<ExpoAppCom> ComList = expoService.getComList(page,expoCode);
//    log.info(ComList.getContent().toString());
    model.addAttribute("ComList",ComList);
    model.addAttribute("ExpoCode",expoCode);


    return "admin/employApply/AdExpoAudit";
    }

    //2024.01.15 정정빈
    //박람회 기업 심사 완료 리스트 (심사후)
    @GetMapping("/show/ok/employapply/com/{expo_code}")
    public String OkComList(Model model,@PathVariable("expo_code") int expoCode,@RequestParam(value = "page", defaultValue = "0") int page){
        Page<ExpoAppCom> ComList = expoService.getComOKList(page,expoCode);
//    log.info(ComList.getContent().toString());
        model.addAttribute("ComList",ComList);
        model.addAttribute("ExpoCode",expoCode);

        return "admin/employApply/AdExpoAuditOk";
    }

    //2024.01.15 정정빈
    //박람회 기업 심사
    @PostMapping("/com/audit/{expo_code}/{capp_num}")
    public String ComAudit(@RequestParam(name = "approval") int approval,@PathVariable("expo_code")  int expoCode,
                           @PathVariable("capp_num")  int cappNum){
        log.info(String.valueOf(approval));
        log.info(String.valueOf(cappNum));

        ExpoAppCom target = expoAppComRepository.findById(cappNum).orElse(null);
        log.info(target.toString());

        target.setRecog_check(1);

        expoAppComRepository.save(target);



        return "redirect:/admin/show/employapply/com/" + expoCode;
    }

    //2024.01.15 정정빈
    //회사 정보 팝업창
    @GetMapping("/cominfo/{com_code}")
    public String ComInco(Model model,@PathVariable("com_code")  int comCode){

        Company cominfo = companyRepository.findById(comCode).orElse(null);
        model.addAttribute("ComInfo",cominfo);
        log.info(cominfo.toString());



        return "/admin/employApply/ComInfo";
    }
}
