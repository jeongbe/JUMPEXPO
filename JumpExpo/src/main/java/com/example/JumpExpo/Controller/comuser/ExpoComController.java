package com.example.JumpExpo.Controller.comuser;

import com.example.JumpExpo.DTO.comuser.ExpoAppForm;
import com.example.JumpExpo.Entity.admin.ScheduleInsert;
import com.example.JumpExpo.Entity.comuser.Company;
import com.example.JumpExpo.Entity.comuser.ExpoAppCom;
import com.example.JumpExpo.Repository.admin.SchInsetExpoRepository;
import com.example.JumpExpo.Repository.comuser.CompanyRepository;
import com.example.JumpExpo.Repository.comuser.ExpoAppComRepository;
import com.example.JumpExpo.Service.user.expo.ExpoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

@Slf4j
@Controller
@RequestMapping("/com")
public class ExpoComController {

    @Autowired
    SchInsetExpoRepository schInsetExpoRepository;

    @Autowired
    ExpoService expoService;

    @Autowired
    ExpoAppComRepository expoAppComRepository;

    @Autowired
    CompanyRepository companyRepository;





    //2024.01.10 정정빈
    //전체일정 기간 선택

//    @GetMapping("/expo/period")
//    public String ExpoPeriod(Model model,@RequestParam(value="page", defaultValue="0")int page,@RequestParam(name = "duration") int duration){
//        log.info("DSffffffff" + duration);
//
//        Page<ScheduleInsert> AllList = null;
//
//        if(duration == 1){
//            AllList = expoService.get1Period(page);
//        }
//        model.addAttribute("AllList",AllList);
//        log.info(AllList.toString());
//
//        model.addAttribute("TotalPage",AllList.getTotalPages());
//
//
//        return "redirect:/com/allexpo";
//    }



    //2024.01.11 정정빈
    //기업 박람회 신청 리스트 페이지
    @GetMapping("/app/list")
    public String ComExpoAppList(Model model, @RequestParam(value="page", defaultValue="0")int page){


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Company company = companyRepository.findcom(username);
        model.addAttribute("company", company);

        Page<ScheduleInsert> AppList = expoService.getAllList(page);
//        log.info(AppList.getContent().toString());
        model.addAttribute("AppList",AppList);
        model.addAttribute("TotalPage",AppList.getTotalPages());



        return "comuser/applyemploy/ComExpoAppList";
    }


    //기업 박람회 신청 페이지
    @GetMapping("/app/{expo_cate}/{expo_code}/{com_code}")
    public String ComExpoApp(Model model, @PathVariable("expo_cate")  int expoCate,@PathVariable("expo_code")  int expoCode){
        model.addAttribute("expoCate",expoCate);
        model.addAttribute("expoCode",expoCode);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Company company = companyRepository.findcom(username);

        log.info(username);
        log.info(company.toString());

        model.addAttribute("company", company);



        return "comuser/applyemploy/ComExpoApp";
    }

    //기업 박람회 신청
    @PostMapping("/app/insert/{expo_code}/{com_code}")
    public String InsertApp(ExpoAppForm form,@RequestParam(value = "AppFileName",required = false) MultipartFile file1,
                            @PathVariable("com_code")  int comCode,@PathVariable("expo_code")  String expoCode){


        String link = "\\\\192.168.2.3\\images\\a";

        try {
            if(file1 != null && !file1.isEmpty()){
                String vio1 = link + File.separator + file1.getOriginalFilename();
                Path filePath = Paths.get(vio1);
                Files.copy(file1.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            }
        }catch (IOException e) {
            log.error("Error occurred while copying the file: {}", e.getMessage());
            e.printStackTrace();
            return "";
        }

        log.info(form.toString());

        ExpoAppCom target = form.toEntity();
//        log.info(comCode);
        log.info(expoCode);
        target.setCom_code(comCode);
        target.setApp_date(new Date());

        log.info(target.toString());

        ExpoAppCom saved = expoAppComRepository.save(target);


        return "redirect:/com/app/list";
    }

}
