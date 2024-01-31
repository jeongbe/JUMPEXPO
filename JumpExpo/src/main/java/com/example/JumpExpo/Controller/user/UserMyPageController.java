package com.example.JumpExpo.Controller.user;

import com.example.JumpExpo.DTO.Login.ChangePwForm;
import com.example.JumpExpo.DTO.user.UserForm;
import com.example.JumpExpo.DTO.user.UserInterviewForm;
import com.example.JumpExpo.DTO.user.UserReviewForm;
import com.example.JumpExpo.Entity.admin.ScheduleInsert;
import com.example.JumpExpo.Entity.comuser.ApplyEmploy;
import com.example.JumpExpo.Entity.comuser.Company;
import com.example.JumpExpo.Entity.etc.UserEmployApplyList;
import com.example.JumpExpo.Entity.user.PeremApplyUser;
import com.example.JumpExpo.Entity.user.UserInterview;
import com.example.JumpExpo.Entity.user.UserReview;
import com.example.JumpExpo.Entity.user.Users;
import com.example.JumpExpo.Repository.admin.SchInsetExpoRepository;
import com.example.JumpExpo.Repository.comuser.ApplyEmployRepository;
import com.example.JumpExpo.Repository.user.*;
import com.example.JumpExpo.Service.user.expo.ExpoService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    SchInsetExpoRepository schInsetExpoRepository;

    @Autowired
    UserReviewRepository userReviewRepository;

    @Autowired
    UserEmployApplyListRepository userEmployApplyListRepository;

    @Autowired
    PeremApplyRepository peremApplyRepository;

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


//        log.info(UserAppExpoList.getContent().toString());
        model.addAttribute("TotalPage",UserAppExpoList.getTotalPages());

        model.addAttribute("list",UserAppExpoList);

//        Boolean data = userReviewRepository.UserR(users.getUser_code(), UserAppExpoList.ge)


        return "user/Mypage/UserExpoAppList";
    }

    //2024.01.22
    //박람회 리뷰 작성 여부
    @PostMapping("/review/check")
    public ResponseEntity<String> userReviewCheck(@RequestParam("expoCode") int expoCode, @RequestParam("userCode") int userCode) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users users = userReository.finduser(username);

        UserReview data = userReviewRepository.UserR(userCode, expoCode);

        if (data == null) {
            // 리뷰 작성 안했ㅇ르떄
            log.info("Review not written");
            return ResponseEntity.ok("ReviewNotWritten");
        } else {
            // 리뷰 작성했으때
            log.info("Review already written");
            return ResponseEntity.ok("ReviewAlreadyWritten");
        }
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

    //2024.01.22 정정빈
    // 마이페이지 리뷰 작성 페이지
    @GetMapping("/review/{user_code}/{expo_code}")
    public String UserReview(Model model,@PathVariable("user_code") int userCode,@PathVariable("expo_code") int expoCode){
        log.info(String.valueOf(userCode));
        log.info(String.valueOf(expoCode));


        log.info("리뷰 작성 페이지");

        model.addAttribute("userCode",userCode);
        model.addAttribute("ExpoCode",expoCode);

        return "user/Mypage/review/UserInsertReview";
    }

    //2024.01.22 정정빈
    //리뷰 저장
    @PostMapping("/review/save")
    public String UserRSave(Model model,UserReviewForm form){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Users users = userReository.finduser(username);
        model.addAttribute("users", users);

//        log.info(form.toString());
        ScheduleInsert expoCate = schInsetExpoRepository.findById(form.getRExpoCode()).orElse(null);


        form.setUserId(users.getUser_id());
        form.setReDate(new Date());
        form.setExpoCate(expoCate.getExpo_cate());
        form.setRExpoTitle(expoCate.getExpo_title());

        UserReview target = form.toEntity();
        UserReview saved = userReviewRepository.save(target);
//        log.info(saved.toString());

//        log.info(form.toString());

        log.info("리뷰 저장");
        //리뷰 리스트 연결 해줘야함 수정
        return "redirect:/users/mypage/review";
    }

    //2024.01.22 정정빈
    //마이페이지 리뷰 리스트
    @GetMapping("/mypage/review")
    public String UserReviewList(Model model,@RequestParam(value="page", defaultValue="0")int page){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Users users = userReository.finduser(username);
        model.addAttribute("users", users);

        Page<UserReview> userReList = expoService.getUserReList(page, users.getUser_code());
//        log.info(userReList.getContent().toString());

        model.addAttribute("UserReList",userReList);
        model.addAttribute("totalPage",userReList.getTotalPages());

//        ScheduleInsert expoInfo = schInsetExpoRepository.

        return "user/Mypage/review/UserReviewList";
    }

    //2024.01.22 정정빈
    //마이 리뷰 보기 페이지
    @GetMapping("/review/re/{re_num}")
    public String ReviewRead(Model model,@PathVariable("re_num") int reNum){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Users users = userReository.finduser(username);
        model.addAttribute("users", users);
//        log.info(String.valueOf(reNum));

        UserReview userReInfo = userReviewRepository.UserReinfo(users.getUser_code());
//        log.info(userReInfo.toString());

        model.addAttribute("UserReList",userReInfo);

        return "user/Mypage/review/UserReReview";
    }

    //2024.01.22 정정빈
    //마이 리뷰 삭제하기
    @PostMapping("/review/de/{re_num}")
    public String ReviewDelete(Model model, @PathVariable("re_num") int reNum) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Users users = userReository.finduser(username);
        model.addAttribute("users", users);

//        log.info("공지 번호: " + reNum);

        userReviewRepository.deleteById(reNum);

        return "redirect:/users/mypage/review";
    }

    //2024.01.22 정정빈
    //마이 리뷰 수정하기 페이지
    @GetMapping("/review/up/{re_num}")
    public String UserReviewUpDate(Model model, @PathVariable("re_num") int reNum){



        UserReview reDate = userReviewRepository.findById(reNum).orElse(null);

        //수정 날짜
        reDate.setModi_date(new Date());

        userReviewRepository.save(reDate);
//        log.info(reDate.toString());
        model.addAttribute("reDate",reDate);


        
        return "user/Mypage/review/UserUpdateReview";
    }

    //2024.01.22 정정빈
    //마이 리뷰 수정
    @PostMapping("/re/up")
    public String  UserReviewUp(Model model,UserReviewForm form){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Users users = userReository.finduser(username);
        model.addAttribute("users", users);
//        log.info("수정");
//        log.info(form.toString());

        UserReview reDate = userReviewRepository.findById(form.getReNum()).orElse(null);
//        log.info(reDate.toString());

        reDate.setRe_title(form.getReTitle());
        reDate.setRe_content(form.getReContent());

        userReviewRepository.save(reDate);

        return "redirect:/users/mypage/review";
    }

    //2024.01.24 박은채
    //유저 채용 신청 내역
    @GetMapping("/mypage/employ/apply")
    public String emAccept(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Users users = userReository.finduser(username);
        model.addAttribute("users", users);

        model.addAttribute("userCode", users.getUser_code());

        //applyemploy da
//        ArrayList<ApplyEmploy> applyUser = applyEmployRepository.UserApplyList(users.getUser_code());
//        PeremApplyUser peremApplyUser = peremApplyRepository.findById(users.getUser_code()).orElse(null);
        ArrayList<UserEmployApplyList> applyList = userEmployApplyListRepository.applyList(users.getUser_code());
        log.info(applyList.toString());

        model.addAttribute("apply", applyList);
//        model.addAttribute("perem", peremApplyUser);



        return "user/MyPage/UserEmployAccept";
    }

    //2024.01.24 박은채
    //취소, 재신청 업데이트
    @PostMapping("/updatePemCan/{pem_appnum}/{user_code}/{value}")
    public String updatePemCheck(Model model, @PathVariable(name = "pem_appnum") int pemAppnum,
                                 @PathVariable(name = "user_code") int userCode,
                                 @PathVariable(name = "value") int value) {

        PeremApplyUser peremApplyUser = peremApplyRepository.findById(pemAppnum).orElse(null);

        if (peremApplyUser != null) {
            peremApplyUser.setPem_can(value);
            try {
                peremApplyRepository.save(peremApplyUser);
                return "redirect:/users/mypage/employ/apply";
            } catch (Exception e) {
                // 업데이트 실패한 경우 예외 처리 가능
                return "redirect:/users/mypage/employ/apply";
            }
        }
        return "redirect:/users/mypage/employ/apply";
    }

    //2024.01.25 박은채
    //재신청하기 팝업창
    @GetMapping("/mypage/apply/re/{emnot_code}/{user_code}")
    public String ApplyRe(Model model, @PathVariable(name = "emnot_code") int emnotCode,
                          @PathVariable(name = "user_code") int userCode){

        model.addAttribute("emnotCode",emnotCode);
        model.addAttribute("userCode",userCode);

        PeremApplyUser emnotUser = peremApplyRepository.findByEmnotCodeAndUserCode(emnotCode, userCode);

        model.addAttribute("emnotUser", emnotUser);

        return "/user/employ/GetResumePopup";
    }

    //2024.01.25 박은채
    //이전 신청양식 가져오기
    @PostMapping("/submit/apply/re/{emnot_code}/{user_code}")
    public ResponseEntity<?> submitApply(@PathVariable(name = "emnot_code") int emnotCode,
                              @PathVariable(name = "user_code") int userCode){

        PeremApplyUser existingApplication = peremApplyRepository.findByEmnotCodeAndUserCode(emnotCode, userCode);
        existingApplication.setPem_can(1);

        PeremApplyUser saved = peremApplyRepository.save(existingApplication);

        return ResponseEntity.ok("재신청이 완료되었습니다.");
    }
}
