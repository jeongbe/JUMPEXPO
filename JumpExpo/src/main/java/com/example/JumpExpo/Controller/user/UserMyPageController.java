package com.example.JumpExpo.Controller.user;

import com.example.JumpExpo.DTO.Login.ChangePwForm;
import com.example.JumpExpo.DTO.user.UserForm;
import com.example.JumpExpo.DTO.user.UserInterviewForm;
import com.example.JumpExpo.Entity.admin.ScheduleInsert;
import com.example.JumpExpo.Entity.user.UserInterview;
import com.example.JumpExpo.Entity.user.Users;
import com.example.JumpExpo.Repository.user.UserExpoApplyRepository;
import com.example.JumpExpo.Repository.user.UserInterviewRepository;
import com.example.JumpExpo.Repository.user.UserReository;
import com.example.JumpExpo.Service.user.expo.ExpoService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("/users")
public class UserMyPageController {

    @Autowired
    UserInterviewRepository userInterviewRepository;

    @Autowired
    UserReository userReository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ExpoService expoService;

    @Autowired
    UserExpoApplyRepository userExpoApplyRepository;

    //2024.01.15 정정빈
    //면접 일정 관리
    @GetMapping("/interv/calen")
    public String UserInterview(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Users users = userReository.finduser(username);
        model.addAttribute("users", users);

        model.addAttribute("userCode", users.getUser_code());
        log.info(String.valueOf(users.getUser_code()));


        List<UserInterview> list = userInterviewRepository.getInterviewsList(users.getUser_code());
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
    
    //2024.01.18 정정빈
    //유저 박람회 신청 내역
    @GetMapping("/app/list")
    public String UserExpoAppList(Model model, @RequestParam(value="page", defaultValue="0")int page, @RequestParam(value = "serch",required = false)String serch,@RequestParam(name = "date_start", defaultValue = "0") String dateStart ,
                                    @RequestParam(name = "date_end", defaultValue = "0") String dateEnd){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Users users = userReository.finduser(username);
        model.addAttribute("users", users);

        log.info(dateStart);
        log.info(dateEnd);
        log.info(serch);

        Page<ScheduleInsert> UserAppExpoList = null;

        //검색어 있을때
        if(serch != null && dateStart != null && dateEnd != null){
            UserAppExpoList = expoService.getUserAppExpoListSearch(page,users.getUser_code(),serch,dateStart,dateEnd);
        }else {
            UserAppExpoList = expoService.getUserAppExpoList(page,users.getUser_code());
        }


        log.info(UserAppExpoList.getContent().toString());
        model.addAttribute("TotalPage",UserAppExpoList.getTotalPages());

        model.addAttribute("list",UserAppExpoList);


        return "user/Mypage/UserExpoAppList";
    }

    //2024.01.21 정정빈
    //박람회 취소
    @PostMapping("/cancel/expo/")
    public String CancelExpo(RedirectAttributes redirectAttributes, Model model, @RequestParam("expoCode") int exCode){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Users users = userReository.finduser(username);
        model.addAttribute("users", users);

        log.info("연결");
        log.info(String.valueOf(exCode));


        userExpoApplyRepository.UserCancelExpo(exCode,users.getUser_code());

        return "redirect:/users/app/list";
    }

    //2024-01-18 맹성우
    //마이페이지로 이동하는 매핑
    @GetMapping("/myPage")
    public String mypage(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Users users = userReository.finduser(username);
        model.addAttribute("users", users);


        return "user/Mypage/UserInfo";
    }

    //회원 탈퇴 기능
    @GetMapping("/myPage/resign/{usercode}")
    public String resign(Model model, @PathVariable("usercode") int usercode){

        //유저코드 기준으로 유저 정보 가져오기
        Users users = userReository.findById(usercode).orElse(null);

        //가져온 유저정보에 탈퇴여부를 0으로 만들어주기
        users.setUser_sec(0);

        //탈퇴회원으로 다시 저장
        userReository.save(users);


        //메인 화면으로 전환

        return "user/userMain";
    }



    //내 정보를 변경할수있는 페이지로 감
    @GetMapping("/myPage/ChangeInfo")
    public String Changeinfo(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Users users = userReository.finduser(username);
        model.addAttribute("users", users);

        return "user/Mypage/UserInfoChange";
    }

    //변경했을때의 매핑
    @PostMapping("/myPage/ChangeSubmit")
    public String ChSub(Model model, UserForm form){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Users users = userReository.finduser(username);

        if (!form.getName().isEmpty()){
            users.setUser_name(form.getName());
        }
        if (!form.getBirth().isEmpty()){
            users.setUser_birth(form.getBirth());
        }
        if (!form.getAddr().isEmpty()){
            users.setUser_addr(form.getAddr());
        }
        if (!form.getAddr1().isEmpty()){
            users.setUser_deaddr(form.getAddr1());
        }
        if (!form.getPhone().isEmpty()){
            users.setUser_phone(form.getPhone());
        }
        if (!form.getEmail().isEmpty()){
            users.setUser_email(form.getEmail());
        }

        userReository.save(users);

        model.addAttribute("users", users);

        String success = "정보 변경 완료!!.";
        model.addAttribute("success", success);

        return "redirect:/users/myPage?success=true";
    }

    //비밀번호 변경하는 페이지
    @GetMapping("/myPage/PwChangePage")
    public String pwchangepage(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Users users = userReository.finduser(username);
        model.addAttribute("users", users);

        return "user/Mypage/UserPwChangePage";
    }

    //실제로 변경이 이루어지는 매핑
    @PostMapping("myPage/ChangingPw")
    public String ChangingPw(Model model, ChangePwForm form){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Users users = userReository.finduser(username);

        //입력된 비밀번호를 폼으로 받아와 현재 비밀번호와 일치하는지
        //matches를 이용하여 인코딩된 비밀번호를 비교하여 비밀번호가 맞는지 확인한다.
        if (passwordEncoder.matches(form.getId(),users.getUser_pw())){
            //새로입력한 비밀번호를 인코딩하여 저장해줌
            users.setUser_pw(passwordEncoder.encode(form.getPassword()));
            String successPw = "변경이 완료되었습니다";
            model.addAttribute("successPw", successPw);
            userReository.save(users);
            model.addAttribute("users", users);

            return "redirect:/users/myPage?successPw=true";
        }
        else {

            String error = "비밀번호를 다시 확인하세요!";
            model.addAttribute("error", error);
            model.addAttribute("users", users);

            return "redirect:/users/myPage/PwChangePage?error=true";
        }


    }

    //2024.01.21 정정빈
    // 마이페이지 리뷰 작성 페이지
//    @GetMapping("/review/{user_code}")
//    public String UserReview(@PathVariable("user_code") int userCode,@RequestParam("ExpoCate") int expoCate,@RequestParam("ExpoCode") int expoCode){
//        log.info(String.valueOf(userCode));
//        log.info(String.valueOf(expoCate));
//        log.info(String.valueOf(expoCode));
//        log.info("연결");
//
//
//        return "user/Mypage/review/UserInsertReview";
//    }

    @GetMapping("/review/{user_code}/{expo_code}")
    public String UserReview(@PathVariable("user_code") int userCode,@PathVariable("expo_code") int expoCode){
        log.info(String.valueOf(userCode));
        log.info(String.valueOf(expoCode));
        log.info("연결");


        return "user/Mypage/review/UserInsertReview";
    }


}
