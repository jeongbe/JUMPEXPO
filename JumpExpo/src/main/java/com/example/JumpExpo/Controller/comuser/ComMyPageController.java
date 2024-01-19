package com.example.JumpExpo.Controller.comuser;

import com.example.JumpExpo.DTO.comuser.ComInterviewForm;
import com.example.JumpExpo.DTO.user.UserInterviewForm;
import com.example.JumpExpo.Entity.comuser.ApplyEmploy;
import com.example.JumpExpo.Entity.comuser.ComInterview;
import com.example.JumpExpo.Entity.user.UserInterview;

import com.example.JumpExpo.Repository.comuser.ApplyEmployRepository;
import com.example.JumpExpo.Repository.comuser.ComInterviewRepository;
import com.example.JumpExpo.Repository.user.UserInterviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/com")
public class ComMyPageController {

    @Autowired
    ComInterviewRepository comInterviewRepository;

    @Autowired
    ApplyEmployRepository applyEmployRepository;

    //2024.01.18 정정빈
    //기업 면접 일정 관리
    @GetMapping("/interv/calen/{com_code}")
    public String UserInterview(Model model, @PathVariable("com_code") int comCode){
        model.addAttribute("comCode", comCode);


        List<ComInterview> list = comInterviewRepository.getComIntervList(comCode);
        model.addAttribute("list",list);
//        log.info(list.toString());

        return "comuser/mypage/Interview";
    }

    //2024.01.18 정정빈
    //기업 면접 일정 등록 페이지
    @GetMapping("/save/inter/{com_code}")
    public String SaveInterview(Model model, @PathVariable("com_code") int comCode){

//        log.info(String.valueOf(comCode));
        model.addAttribute("comCode", comCode);


        return "comuser/mypage/InterviewInsert";
    }

    //2024.01.18 정정빈
    //유저 면접 일정 등록
    @PostMapping("/save/sc")
    public String UserSaveSc(ComInterviewForm form){

//        log.info(form.toString());

        ComInterview target = form.toEntity();
        ComInterview save = comInterviewRepository.save(target);
//        log.info(save.toString());

        return "redirect:/comuser/save/inter/" + form.getComCode();
    }

    //2024.01.18 정정빈
    //기업 면접 일정 수정,삭제 페이지
    @GetMapping("/update/sc/{sc_num}")
    public String UserScUpDePaga(@PathVariable("sc_num") int scNum,Model model){

        ComInterview getData = comInterviewRepository.findById(scNum).orElse(null);
//        log.info(getData.toString());
        model.addAttribute("getScData",getData);
        model.addAttribute("comCode",getData.getCom_code());


        return "comuser/mypage/ComInterviewUpdate";
    }

    //2024.01.18 정정빈
    //기업 면접 일정 수정,
    @PostMapping("/sc/update/{sc_num}")
    public String UserScUpdate(ComInterviewForm form,@PathVariable("sc_num") int scNum){

        log.info(form.toString());

        ComInterview getD = comInterviewRepository.findById(scNum).orElse(null);
        log.info(getD.toString());

        getD.setCom_code(form.getComCode());
        getD.setC_sc_title(form.getCScTitle());
        getD.setC_sc_start(form.getCScStart());
        getD.setC_sc_end(form.getCScEnd());
        getD.setC_start_time(form.getCStartTime());
        getD.setC_end_time(form.getCEndTime());
        getD.setC_sc_not(form.getCScNot());


        ComInterview save = comInterviewRepository.save(getD);

        return "redirect:/users/save/inter/" + form.getComCode();
    }

    //2024.01.18 정정빈
    //기업 면접 일정 삭제
    @DeleteMapping("/sc/delete/{sc_num}")
    public String ScDelete(ComInterviewForm form,@PathVariable("sc_num") int scNum){

        comInterviewRepository.deleteById(scNum);

        return "redirect:/users/save/inter/" + form.getComCode();
    }

    //2024.01.19 박은채
    //공고 신청 내역 페이지
    @GetMapping("/mypage/employ/accept")
    public String emAccept(){

//        ApplyEmploy applyEmploy = applyEmployRepository.findById(comCode).orElse(null);


        return "comuser/MyPage/EmployAccept";
    }

}
