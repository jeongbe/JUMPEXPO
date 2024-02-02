package com.example.JumpExpo.Controller.comuser;

import com.example.JumpExpo.DTO.comuser.ExpoAppForm;
import com.example.JumpExpo.Entity.admin.ScheduleInsert;
import com.example.JumpExpo.Entity.comuser.ApplyEmploy;
import com.example.JumpExpo.Entity.comuser.Company;
import com.example.JumpExpo.Entity.comuser.ExpoAppCom;
import com.example.JumpExpo.Entity.user.UserExpoApply;
import com.example.JumpExpo.Entity.user.Users;
import com.example.JumpExpo.Repository.admin.SchInsetExpoRepository;
import com.example.JumpExpo.Repository.comuser.ApplyEmployRepository;
import com.example.JumpExpo.Repository.comuser.CompanyRepository;
import com.example.JumpExpo.Repository.comuser.ExpoAppComRepository;
import com.example.JumpExpo.Repository.user.PeremApplyRepository;
import com.example.JumpExpo.Repository.user.UserExpoApplyRepository;
import com.example.JumpExpo.Repository.user.UserReository;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

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

    @Autowired
    ApplyEmployRepository applyEmployRepository;

    @Autowired
    PeremApplyRepository peremApplyRepository;

    @Autowired
    UserReository userReository;

    @Autowired
    UserExpoApplyRepository userExpoApplyRepository;



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

        ExpoAppCom ComAppCheck = expoAppComRepository.ComAppCheck(expoCode,company.getCom_code());

        if(ComAppCheck != null){
            model.addAttribute("ComAppCheck",ComAppCheck);
        }

        return "comuser/applyemploy/ComExpoApp";
    }

    //기업 박람회 신청
    @PostMapping("/app/insert/{expo_code}/{com_code}")
    public String InsertApp(Model model,ExpoAppForm form,@RequestParam(value = "AppFileName",required = false) MultipartFile file1,
                            @PathVariable("com_code")  int comCode,@PathVariable("expo_code")  String expoCode,
                            @RequestParam(name = "ExpoCate") int expoCate){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Company company = companyRepository.findcom(username);
        model.addAttribute("company", company);

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
        log.info("박람호 구분 코드" + String.valueOf(expoCate));


        ExpoAppCom ComAppCheck = expoAppComRepository.ComAppCheck(Integer.parseInt(expoCode),company.getCom_code());

        if(ComAppCheck != null){
            log.info("이미 박람회 신청함");
            return "redirect:/com/app/" + expoCate + "/" + expoCode + "/" + company.getCom_code();
        }else {
            ExpoAppCom target = form.toEntity();
//        log.info(comCode);
//        log.info(expoCode);
            target.setCom_code(comCode);
            target.setApp_date(new Date());

            log.info(target.toString());
            ExpoAppCom saved = expoAppComRepository.save(target);

            return "redirect:/com/app/list";
        }

    }

    //2024.01.08 정정빈
    //행사일정 - 전체일정
    @GetMapping("/allexpo")
    public String AllExpoList(Model model, @RequestParam(value="page", defaultValue="0")int page,
                              @RequestParam(value = "serch",required = false)String serch,@RequestParam(name = "date_start", defaultValue = "0") String dateStart ,
                              @RequestParam(name = "date_end", defaultValue = "0") String dateEnd){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Company company = companyRepository.findcom(username);
        model.addAttribute("company", company);

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

        model.addAttribute("AllList",AllList);
        log.info(AllList.toString());

        model.addAttribute("TotalPage",AllList.getTotalPages());
        log.info("페이지 수"+AllList.getTotalPages());


        return "comuser/expo/ExpoAllList";
    }

    //2024.01.17 정정빈
    //행사일정 - 취업 박람회 일정

    @GetMapping("/emp/expolist")
    public String EmpExpoList(Model model, @RequestParam(value="page", defaultValue="0")int page,
                              @RequestParam(value = "serch",required = false)String serch,@RequestParam(name = "date_start", defaultValue = "0") String dateStart ,
                              @RequestParam(name = "date_end", defaultValue = "0") String dateEnd){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Company company = companyRepository.findcom(username);
        model.addAttribute("company", company);

        log.info("시작날" + dateStart);
        log.info("끝날" + dateEnd);

        Page<ScheduleInsert> AllList = null;

        //검색어 있을때
        if(serch != null && dateStart != null && dateEnd != null){
            AllList = expoService.getSerchEmpList(page,serch,dateStart,dateEnd);
            log.info(AllList.toString());
        }else {
            AllList = expoService.getUserEmpList(page);
        }

        model.addAttribute("AllList",AllList);
        log.info(AllList.toString());

        model.addAttribute("TotalPage",AllList.getTotalPages());
        log.info("페이지 수"+AllList.getTotalPages());


        return "comuser/expo/ExpoEmpList";
    }

    //2024.01.17 정정빈
    //행사일정 - 채용 박람회 일정

    @GetMapping("/rec/expolist")
    public String RecExpoList(Model model, @RequestParam(value="page", defaultValue="0")int page,
                              @RequestParam(value = "serch",required = false)String serch,@RequestParam(name = "date_start", defaultValue = "0") String dateStart ,
                              @RequestParam(name = "date_end", defaultValue = "0") String dateEnd){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Company company = companyRepository.findcom(username);
        model.addAttribute("company", company);

        log.info("시작날" + dateStart);
        log.info("끝날" + dateEnd);

        Page<ScheduleInsert> AllList = null;

        //검색어 있을때
        if(serch != null && dateStart != null && dateEnd != null){
            AllList = expoService.getSerchRecList(page,serch,dateStart,dateEnd);
            log.info(AllList.toString());
        }else {
            AllList = expoService.getUserRecList(page);
        }

        model.addAttribute("AllList",AllList);
        log.info(AllList.toString());

        model.addAttribute("TotalPage",AllList.getTotalPages());
        log.info("페이지 수"+AllList.getTotalPages());


        return "comuser/expo/ExpoRecList";
    }

    //2024.01.17 정정빈
    //행사일정 - 페어 박람회 일정

    @GetMapping("/fair/expolist")
    public String FairExpoList(Model model, @RequestParam(value="page", defaultValue="0")int page,
                               @RequestParam(value = "serch",required = false)String serch,@RequestParam(name = "date_start", defaultValue = "0") String dateStart ,
                               @RequestParam(name = "date_end", defaultValue = "0") String dateEnd){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Company company = companyRepository.findcom(username);
        model.addAttribute("company", company);

        log.info("시작날" + dateStart);
        log.info("끝날" + dateEnd);

        Page<ScheduleInsert> AllList = null;

        //검색어 있을때
        if(serch != null && dateStart != null && dateEnd != null){
            AllList = expoService.getSerchFairList(page,serch,dateStart,dateEnd);
            log.info(AllList.toString());
        }else {
            AllList = expoService.getUserFairList(page);
        }

        model.addAttribute("AllList",AllList);
        log.info(AllList.toString());

        model.addAttribute("TotalPage",AllList.getTotalPages());
        log.info("페이지 수"+AllList.getTotalPages());


        return "comuser/expo/ExpoFairList";
    }

    //2024.01.10 정정빈
    //박람회 디테일정보 페이지
    @GetMapping("/expo/info/{expo_code}/{expo_cate}")
    public String ExpoInfo(Model model, @PathVariable("expo_code") int expoCode, @PathVariable("expo_cate")  int expoCate){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Company company = companyRepository.findcom(username);
        model.addAttribute("company", company);


        ScheduleInsert data = schInsetExpoRepository.findById(expoCode).orElse(null);
//        log.info(data.toString());

        model.addAttribute("ExpoInfo",data);

        UserExpoApply Check = userExpoApplyRepository.UserAppCheck(expoCode,company.getCom_code());
//        log.info(Check.toString());
        if(Check != null){
            model.addAttribute("Check",Check);
        }

        return "comuser/expo/expoInfo";
    }

//    @GetMapping("/show/employlist")
//    public String empoyList(Model model){
//
//
//        ArrayList<ApplyEmploy> allList = applyEmployRepository.AllEmployList();
//        log.info(allList.toString());
//        ArrayList<ApplyEmploy> DesignList = applyEmployRepository.DesignEmployList();
//        ArrayList<ApplyEmploy> FrontList = applyEmployRepository.FrontEmployList();
//        ArrayList<ApplyEmploy> BackendList = applyEmployRepository.BackendEmployList();
//        ArrayList<ApplyEmploy> EtcList = applyEmployRepository.EtcEmployList();
//
//        model.addAttribute("allList", allList);
//        model.addAttribute("designList", DesignList);
//        model.addAttribute("frontList", FrontList);
//        model.addAttribute("backendList", BackendList);
//        model.addAttribute("etcList", EtcList);
//
//        // 각 공고별 지원자 수
//        model.addAttribute("applicantCounts", allList.stream()
//                .collect(Collectors.toMap(ApplyEmploy::getEmnot_code, e -> peremApplyRepository.countByEmnotCode(e.getEmnot_code()))));
//
//        return "comuser/employ/EmployList";
//    }
//
//    //페이지 만들어줘야함
//    @GetMapping("/show/employlist/{emnot_code}/{com_code}")
//    public String employDetail(Model model, @PathVariable(name="emnot_code") int emnotCode,
//                               @PathVariable(name = "com_code") int comCode){
//
//        ApplyEmploy applyEmploy = applyEmployRepository.findById(emnotCode).orElse(null);
//        Company company = companyRepository.findById(comCode).orElse(null);
//
//        if (applyEmploy == null) {
//            // 데이터가 없을 경우
//            return "users/show/employlist";
//        }
//
//        model.addAttribute("applyEmploy", applyEmploy);
//        model.addAttribute("company",company);
//
//        return "user/employ/EmployListDetail";
//    }

}
