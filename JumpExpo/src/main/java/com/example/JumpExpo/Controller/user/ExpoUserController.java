package com.example.JumpExpo.Controller.user;

import com.example.JumpExpo.Entity.admin.ScheduleInsert;
import com.example.JumpExpo.Repository.admin.SchInsetExpoRepository;
import com.example.JumpExpo.Service.user.expo.ExpoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/users")
public class ExpoUserController {

    @Autowired
    SchInsetExpoRepository schInsetExpoRepository;

    @Autowired
    ExpoService expoService;

    //2024.01.08 정정빈
    //행사일정 - 전체일정
    @GetMapping("/allexpo")
    public String AllExpoList(Model model, @RequestParam(value="page", defaultValue="0")int page,
                              @RequestParam(value = "serch",required = false)String serch,@RequestParam(name = "date_start", defaultValue = "0") String dateStart ,
                              @RequestParam(name = "date_end", defaultValue = "0") String dateEnd){
        log.info("시작날" + dateStart);
        log.info("끝날" + dateEnd);

        Page<ScheduleInsert> AllList = null;

        //검색어 있을때
        if(serch != null && dateStart != null && dateEnd != null){
            AllList = expoService.getSerchList(page,serch,dateStart,dateEnd);
            log.info(AllList.toString());
        }else {
            AllList = expoService.getAllList(page);

        }




//        log.info(AllList.toString());
        model.addAttribute("AllList",AllList);
        log.info(AllList.toString());

        model.addAttribute("TotalPage",AllList.getTotalPages());
        log.info("페이지 수"+AllList.getTotalPages());


        return "user/expo/ExpoAllList";
    }

    //2024.01.10 정정빈
    //박람회 디테일정보 페이지
    @GetMapping("/expo/info/{expo_code}/{expo_cate}")
    public String ExpoInfo(Model model, @PathVariable("expo_code")  int expoCode, @PathVariable("expo_cate")  int expoCate){

        ScheduleInsert data = schInsetExpoRepository.findById(expoCode).orElse(null);
//        log.info(data.toString());

        model.addAttribute("ExpoInfo",data);


        return "user/expo/expoInfo";
    }
}
