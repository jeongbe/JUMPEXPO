package com.example.JumpExpo.Controller.user;

import com.example.JumpExpo.DTO.user.UserExpoApplyForm;
import com.example.JumpExpo.Entity.admin.ScheduleInsert;
import com.example.JumpExpo.Entity.user.UserExpoApply;
import com.example.JumpExpo.Entity.user.Users;
import com.example.JumpExpo.Repository.admin.SchInsetExpoRepository;
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

@Slf4j
@Controller
@RequestMapping("/users")
public class ExpoUserController {

    @Autowired
    SchInsetExpoRepository schInsetExpoRepository;

    @Autowired
    ExpoService expoService;

    @Autowired
    UserReository userReository;

    @Autowired
    UserExpoApplyRepository userExpoApplyRepository;

    //2024.01.08 정정빈
    //행사일정 - 전체일정
    @GetMapping("/allexpo")
    public String AllExpoList(Model model, @RequestParam(value="page", defaultValue="0")int page,
                              @RequestParam(value = "serch",required = false)String serch,@RequestParam(name = "date_start", defaultValue = "0") String dateStart ,
                              @RequestParam(name = "date_end", defaultValue = "0") String dateEnd){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Users users = userReository.finduser(username);
        model.addAttribute("users", users);

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


        return "user/expo/ExpoAllList";
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
        Users users = userReository.finduser(username);
        model.addAttribute("users", users);

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


        return "user/expo/ExpoFairList";
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
        Users users = userReository.finduser(username);
        model.addAttribute("users", users);

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


        return "user/expo/ExpoEmpList";
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
        Users users = userReository.finduser(username);
        model.addAttribute("users", users);

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


        return "user/expo/ExpoRecList";
    }

    //2024.01.10 정정빈
    //박람회 디테일정보 페이지
    @GetMapping("/expo/info/{expo_code}/{expo_cate}")
    public String ExpoInfo(Model model, @PathVariable("expo_code")  int expoCode, @PathVariable("expo_cate")  int expoCate){


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Users users = userReository.finduser(username);
        model.addAttribute("users", users);
        log.info(users.toString());


        ScheduleInsert data = schInsetExpoRepository.findById(expoCode).orElse(null);
//        log.info(data.toString());

        model.addAttribute("ExpoInfo",data);

        UserExpoApply Check = userExpoApplyRepository.UserAppCheck(expoCode, users.getUser_code());
//        log.info(Check.toString());
        if(Check != null){
            model.addAttribute("Check",Check);
        }


        return "user/expo/expoInfo";
    }

    //2024.01.17 정정빈
    //유저 박람회 신청
    @PostMapping("/apply/expo")
    public String UserApplyExpo(UserExpoApplyForm form,Model model){
        log.info(form.toString());

        UserExpoApply target = form.toEntity();

        UserExpoApply Check = userExpoApplyRepository.UserAppCheck(form.getExpoCode(), form.getUserCode());
        model.addAttribute("Check",Check);

        if(Check != null){
            log.info("이미 신청한 박람회");
            return "redirect:/users/expo/info/" + form.getExpoCode() + "/" + form.getExpoCate();
        }else {
            UserExpoApply save = userExpoApplyRepository.save(target);
            log.info(save.toString());
            //나중에 신청 내역으로 이동
            return "redirect:/users/app/list";
        }

    }
}
