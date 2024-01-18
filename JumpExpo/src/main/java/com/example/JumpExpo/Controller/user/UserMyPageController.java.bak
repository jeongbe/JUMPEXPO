package com.example.JumpExpo.Controller.user;

import com.example.JumpExpo.DTO.user.UserInterviewForm;
import com.example.JumpExpo.Entity.user.UserInterview;
import com.example.JumpExpo.Repository.user.UserInterviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/users")
public class UserMyPageController {

    @Autowired
    UserInterviewRepository userInterviewRepository;

    //2024.01.15 정정빈
    //면접 일정 관리
    @GetMapping("/interv/calen/{user_code}")
    public String UserInterview(Model model, @PathVariable("user_code") int userCode){
        model.addAttribute("userCode", userCode);


        List<UserInterview> list = userInterviewRepository.getInterviewsList(userCode);
        log.info(list.toString());
        model.addAttribute("list",list);

        return "user/Mypage/Interview";
    }

    //2024.01.18 정정빈
    //유저 면접 일정 등록 페이지
    @GetMapping("/save/inter/{user_code}")
    public String SaveInterview(Model model, @PathVariable("user_code") int userCode){

        log.info(String.valueOf(userCode));
        model.addAttribute("userCode", userCode);


        return "user/Mypage/InterviewInsert";
    }

    //2024.01.18 정정빈
    //유저 면접 일정 등록
    @PostMapping("/save/sc")
    public String UserSaveSc(UserInterviewForm form){

//        log.info(form.toString());

        UserInterview target = form.toEntity();
        UserInterview save = userInterviewRepository.save(target);
//        log.info(save.toString());

        return "redirect:/users/save/inter/" + form.getUserCode();
    }

    //2024.01.18 정정빈
    //유저 면접 일정 수정,삭제 페이지
    @GetMapping("/update/sc/{sc_num}")
    public String UserScUpDePaga(@PathVariable("sc_num") int scNum,Model model){

        UserInterview getData = userInterviewRepository.findById(scNum).orElse(null);
//        log.info(getData.toString());
        model.addAttribute("getScData",getData);
        model.addAttribute("userCode",getData.getUser_code());

//        log.info(form.toString());


        return "user/Mypage/InterviewUpdate";
    }

    //2024.01.18 정정빈
    //유저 면접 일정 수정,
    @PostMapping("/sc/update/{sc_num}")
    public String UserScUpdate(UserInterviewForm form,@PathVariable("sc_num") int scNum){

        log.info(form.toString());

        UserInterview getD = userInterviewRepository.findById(scNum).orElse(null);
        log.info(getD.toString());

        getD.setUser_code(form.getUserCode());
        getD.setSc_title(form.getScTitle());
        getD.setSc_start(form.getScStart());
        getD.setSc_end(form.getScEnd());
        getD.setStart_time(form.getStartTime());
        getD.setEnd_time(form.getEndTime());
        getD.setSc_not(form.getScNot());


        UserInterview save = userInterviewRepository.save(getD);

        return "redirect:/users/save/inter/" + form.getUserCode();
    }

    //2024.01.18 정정빈
    //유저 면접 일정 삭제
    @DeleteMapping("/sc/delete/{sc_num}")
    public String ScDelete(UserInterviewForm form,@PathVariable("sc_num") int scNum){

        userInterviewRepository.deleteById(scNum);

        return "redirect:/users/save/inter/" + form.getUserCode();
    }

}
