package com.example.JumpExpo.Controller.admin;

import com.example.JumpExpo.Entity.admin.ScheduleInsert;
import com.example.JumpExpo.Repository.admin.SchInsetExpoRepository;
import com.example.JumpExpo.Service.user.expo.ExpoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/com")
public class ExpoController {

    @Autowired
    SchInsetExpoRepository schInsetExpoRepository;

    @Autowired
    ExpoService expoService;

    //2024.01.08 정정빈
    //행사일정 - 전체일정 
    @GetMapping("/allexpo")
    public String AllExpoList(Model model, @RequestParam(value="page", defaultValue="0")int page,
                              @RequestParam(value = "serch",required = false)String serch,@RequestParam(name = "duration", defaultValue = "0") int duration ){
        log.info("dsf" + duration);
        //박람회 전체 일정
//        Page<ScheduleInsert> AllList = expoService.getAllList(page);
        if (serch != null && serch.isEmpty()){
            log.info(serch);

        }
        Page<ScheduleInsert> AllList = null;

        if(serch != null){
            AllList = expoService.getSerchList(page,serch);
            log.info(AllList.toString());
        }
        else {
            AllList = expoService.getAllList(page);
        }

//        log.info(AllList.toString());
        model.addAttribute("AllList",AllList);

        model.addAttribute("TotalPage",AllList.getTotalPages());
        log.info("페이지 수"+AllList.getTotalPages());
        

        return "comuser/expo/ExpoAllList";
    }

    //2024.01.10 정정빈
    //전체일정 기간 선택

    @GetMapping("/expo/period")
    public String ExpoPeriod(Model model,@RequestParam(value="page", defaultValue="0")int page,@RequestParam(name = "duration") int duration){
        log.info("DSffffffff" + duration);

        Page<ScheduleInsert> AllList = null;

        if(duration == 1){
            AllList = expoService.get1Period(page);
        }
        model.addAttribute("AllList",AllList);
        log.info(AllList.toString());

        model.addAttribute("TotalPage",AllList.getTotalPages());


        return "redirect:/com/allexpo";
    }

    //2024.01.10 정정빈
    //박람회 디테일정보 페이지
    @GetMapping("/expo/info/{expo_code}/{expo_cate}")
    public String ExpoInfo(@PathVariable("expo_code")  int expoCode,@PathVariable("expo_cate")  int expoCate){


        return "comuser/expo/expoInfo";
    }

}
