package com.example.JumpExpo.Controller.comuser;

import com.example.JumpExpo.DTO.comuser.EmployForm;
import com.example.JumpExpo.Entity.comuser.ApplyEmploy;
import com.example.JumpExpo.Repository.comuser.ApplyEmployRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//2024.01.08 박은채 채용 공고 신청 Controller
//채용 공고 신청 Controller
@Slf4j
@Controller
@RequestMapping("/com")
public class ApplyEmployController {

    @Autowired
    ApplyEmployRepository applyEmployRepository;

    //채용 공고 신청 페이지
    @GetMapping("/insert/employ")
    public String insertEmploy(){

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
}
