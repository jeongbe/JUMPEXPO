package com.example.JumpExpo.Controller.comuser;

import com.example.JumpExpo.DTO.Login.ChangePwForm;
import com.example.JumpExpo.DTO.admin.notice.NoticeForm;
import com.example.JumpExpo.DTO.comuser.ComInterviewForm;
import com.example.JumpExpo.DTO.comuser.CompanyForm;
import com.example.JumpExpo.DTO.comuser.EmployForm;
import com.example.JumpExpo.DTO.user.UserForm;
import com.example.JumpExpo.DTO.user.UserInterviewForm;
import com.example.JumpExpo.Entity.admin.Notice;
import com.example.JumpExpo.Entity.admin.ScheduleInsert;
import com.example.JumpExpo.Entity.comuser.ApplyEmploy;
import com.example.JumpExpo.Entity.comuser.ComInterview;
import com.example.JumpExpo.Entity.comuser.Company;
import com.example.JumpExpo.Entity.comuser.ExpoAppCom;
import com.example.JumpExpo.Entity.etc.ComExpoApp;
import com.example.JumpExpo.Entity.user.PeremApplyUser;
import com.example.JumpExpo.Entity.user.UserInterview;

import com.example.JumpExpo.Entity.user.Users;
import com.example.JumpExpo.Repository.comuser.ApplyEmployRepository;
import com.example.JumpExpo.Repository.comuser.ComInterviewRepository;
import com.example.JumpExpo.Repository.comuser.CompanyRepository;
import com.example.JumpExpo.Repository.user.PeremApplyRepository;
import com.example.JumpExpo.Repository.user.UserInterviewRepository;
import com.example.JumpExpo.Service.user.expo.ExpoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/com")
public class ComMyPageController {

    @Autowired
    ComInterviewRepository comInterviewRepository;

    @Autowired
    ApplyEmployRepository applyEmployRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    PeremApplyRepository peremApplyRepository;

    @Autowired
    ExpoService expoService;

    //2024.01.18 정정빈
    //기업 면접 일정 관리
    @GetMapping("/interv/calen")
    public String UserInterview(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Company company = companyRepository.findcom(username);
        model.addAttribute("company", company);

        model.addAttribute("comCode",company.getCom_code());


        List<ComInterview> list = comInterviewRepository.getComIntervList(company.getCom_code());
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

    //2024-01-20 맹성우
    //마이 페이지로 가는 매핑
    @GetMapping("/myPage")
    public String commypage(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Company company = companyRepository.findcom(username);
        model.addAttribute("company", company);


        return "comuser/mypage/ComuserInfopage";
    }


    //회원탈퇴 팝업
    @GetMapping("/myPage/rePoP/{com_code}")
    public String rePoP(Model model,@PathVariable(name = "com_code") int comCode){

        model.addAttribute("comCode",comCode);

        Company company = companyRepository.findById(comCode).orElse(null);

        if (company != null) {
            model.addAttribute("comName", company.getCom_name());
        } else {
            // 사용자를 찾을 수 없을 때
            model.addAttribute("comName", "사용자 이름 없음");
        }

        return "comuser/mypage/ComRePop";
    }



    //2024-01-20 맹성우
    //회원 탈퇴하는 매핑

    //회원 탈퇴 기능
    @PostMapping("/myPage/resign/{comcode}")
    public String resign(Model model, @PathVariable("comcode") int comcode){

        //유저코드 기준으로 유저 정보 가져오기
        Company company = companyRepository.findById(comcode).orElse(null);

        //가져온 유저정보에 탈퇴여부를 0으로 만들어주기
        company.setCom_sec(0);

        //탈퇴회원으로 다시 저장
        companyRepository.save(company);


        //메인 화면으로 전환

        return "user/userMain";
    }

    //내 정보를 변경할수있는 페이지로 감
    @GetMapping("/myPage/ComChangeInfo")
    public String Changeinfo(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Company company = companyRepository.findcom(username);
        model.addAttribute("company", company);

        return "comuser/mypage/ComInfoChange";
    }

    //변경했을때의 매핑
    @PostMapping("/myPage/ChangeSubmit")
    public String ChSub(Model model, CompanyForm form){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Company company = companyRepository.findcom(username);

        if (!form.getComname().isEmpty()){
            company.setCom_name(form.getComname());
        }
        if (!form.getAddr().isEmpty()){
            company.setCom_addr(form.getAddr());
        }
        if (!form.getAddr1().isEmpty()){
            company.setCom_addr1(form.getAddr1());
        }
        if (!form.getHomepage().isEmpty()){
            company.setCom_home(form.getHomepage());
        }
        if (!form.getPhone().isEmpty()){
            company.setManager_phone(form.getPhone());
        }
        if (!form.getEmail().isEmpty()){
            company.setCom_email(form.getEmail());
        }

        companyRepository.save(company);

        model.addAttribute("company", company);

        String success = "정보 변경 완료!!.";
        model.addAttribute("success", success);

        return "redirect:/com/myPage?success=true";
    }

    //비밀번호 변경하는 페이지
    @GetMapping("/myPage/ComPwChangePage")
    public String pwchangepage(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Company company = companyRepository.findcom(username);
        model.addAttribute("company", company);


        return "comuser/mypage/ComuserPwChange";
    }

    @PostMapping("/myPage/ComChangingPw")
    public String ChangingPw(Model model, ChangePwForm form){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Company company = companyRepository.findcom(username);

        //입력된 비밀번호를 폼으로 받아와 현재 비밀번호와 일치하는지
        //matches를 이용하여 인코딩된 비밀번호를 비교하여 비밀번호가 맞는지 확인한다.
        if (passwordEncoder.matches(form.getId(),company.getCom_pw())){
            //새로입력한 비밀번호를 인코딩하여 저장해줌
            company.setCom_pw(passwordEncoder.encode(form.getPassword()));
            String successPw = "변경이 완료되었습니다";
            model.addAttribute("successPw", successPw);
            companyRepository.save(company);
            model.addAttribute("company", company);

            return "redirect:/com/myPage?successPw=true";
        }
        else {

            String error = "비밀번호를 다시 확인하세요!";
            model.addAttribute("error", error);
            model.addAttribute("company", company);

            return "redirect:/com/myPage/ComPwChangePage?error=true";
        }


    }

    //2024.01.22 박은채
    //공고 신청 내역 페이지
    @GetMapping("/mypage/employ/accept/{com_code}")
    public String emAccept(Model model, @PathVariable("com_code") int comCode){

        model.addAttribute("comCode", comCode);

        ArrayList<ApplyEmploy> applyEmploy = applyEmployRepository.AllList(comCode);
        model.addAttribute("applyEmploy", applyEmploy);


        return "comuser/MyPage/EmployAccept";
    }

    //2024.01.22 박은채
    //공고 신청 내역 상세 페이지
    @GetMapping("/mypage/employ/accept/{com_code}/{emnot_code}")
    public String emAcceptDetail(Model model, @PathVariable("com_code") int comCode,
                                 @PathVariable("emnot_code") int emnotCode){

        ApplyEmploy applyEmploy = applyEmployRepository.findById(emnotCode).orElse(null);
        Company company = companyRepository.findById(comCode).orElse(null);

        model.addAttribute("applyEmploy", applyEmploy);
        model.addAttribute("company",company);

        return "comuser/MyPage/EmployAcceptDetail";
    }

    //2024.01.22 박은채
    //공고 상세 수정 페이지
    @GetMapping("/mypage/employ/accept/{com_code}/{emnot_code}/update")
    public String emAcceptDetailEdit(Model model, @PathVariable("com_code") int comCode,
                               @PathVariable("emnot_code") int emnotCode){

        ApplyEmploy applyEmploy = applyEmployRepository.findById(emnotCode).orElse(null);

        model.addAttribute("applyEmploy", applyEmploy);
//        model.addAttribute("comCode", comCode);
//        model.addAttribute("emnotCode", emnotCode);

        return "comuser/MyPage/EmployAcceptDetailUpdate";
    }

    //2024.01.22 박은채
    //공고 상세 수정하기 (Post)
    @PostMapping("/mypage/employ/accept/{com_code}/{emnot_code}/updateCom")
    public String emAcceptDetailUpdate(EmployForm form, @PathVariable("com_code") int comCode,
                                       @PathVariable("emnot_code") int emnotCode){

//        emnotCode 기준으로 데이터 찾기
        ApplyEmploy applyEmploy = applyEmployRepository.findById(emnotCode).orElse(null);

        applyEmploy.updateDataFromForm(form);
        applyEmployRepository.save(applyEmploy);

        return "redirect:/com/mypage/employ/accept/{com_code}/{emnot_code}";
    }

    //2024.01.22 박은채
    //공고 삭제하기
    @GetMapping("/mypage/employ/accept/{com_code}/{emnot_code}/delete")
    public String ExpoInfo(@PathVariable("emnot_code") int emnotCode){

        ApplyEmploy deleteTarget = applyEmployRepository.findById(emnotCode).orElse(null);

        applyEmployRepository.delete(deleteTarget);

        return "redirect:/com/mypage/employ/accept/{com_code}";
    }

    //2024.01.22 박은채
    //채용 공고 신청 현황<분야별 리스트>
    @GetMapping("/mypage/show/employlist")
    public String empoyList(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Company company = companyRepository.findcom(username);
        model.addAttribute("company", company);

        model.addAttribute("comCode", company.getCom_code());

        ArrayList<ApplyEmploy> allList = applyEmployRepository.AllComEmployList(company.getCom_code());
        ArrayList<ApplyEmploy> DesignList = applyEmployRepository.DesignComEmployList(company.getCom_code());
        ArrayList<ApplyEmploy> FrontList = applyEmployRepository.FrontComEmployList(company.getCom_code());
        ArrayList<ApplyEmploy> BackendList = applyEmployRepository.BackendComEmployList(company.getCom_code());
        ArrayList<ApplyEmploy> EtcList = applyEmployRepository.EtcComEmployList(company.getCom_code());

        model.addAttribute("allList", allList);
        model.addAttribute("designList", DesignList);
        model.addAttribute("frontList", FrontList);
        model.addAttribute("backendList", BackendList);
        model.addAttribute("etcList", EtcList);

        // 각 공고별 지원자 수
        model.addAttribute("applicantCounts", allList.stream()
                .collect(Collectors.toMap(ApplyEmploy::getEmnot_code, e -> peremApplyRepository.countByEmnotCode(e.getEmnot_code()))));

        return "comuser/MyPage/ComEmployList";
    }

    //2024.01.24 박은채
    //채용 공고 신청 현황 상세 페이지
    @GetMapping("/mypage/show/employlist/{com_code}/{emnot_code}")
    public String employDetail(Model model, @PathVariable(name="emnot_code") int emnotCode,
                               @PathVariable(name = "com_code") int comCode){

        ArrayList<PeremApplyUser> userList = peremApplyRepository.EmcodeUserList(emnotCode);
        ApplyEmploy emnotList = applyEmployRepository.findById(emnotCode).orElse(null);

        model.addAttribute("userList", userList);
        model.addAttribute("emnotList",emnotList);

        return "comuser/MyPage/ComEmployDetail";
    }

    //2024.01.24 박은채
    //열람, 미열람 업데이트
    @PostMapping("/updatePemCheck/{pem_appnum}/{emnot_code}/{com_code}/{value}")
    public String updatePemCheck(Model model, @PathVariable(name = "pem_appnum") int pemAppnum,
                                   @PathVariable(name = "value") int value, @PathVariable(name="emnot_code") int emnotCode,
                                 @PathVariable(name = "com_code") int comCode) {

        PeremApplyUser peremApplyUser = peremApplyRepository.findById(pemAppnum).orElse(null);

        if (peremApplyUser != null) {
            peremApplyUser.setPem_check(value);
            try {
                peremApplyRepository.save(peremApplyUser);
                return "redirect:/com/mypage/show/employlist/{com_code}/{emnot_code}";
            } catch (Exception e) {
                // 업데이트 실패한 경우 예외 처리 가능
                return "redirect:/com/mypage/show/employlist/{com_code}/{emnot_code}";
            }
        }
        return "redirect:/com/mypage/show/employlist/{com_code}/{emnot_code}";
    }

    //2024.01.27 정정빈
    //기업 박람회 신청 내역
    @GetMapping("/expo/app")
    public String ComExpoApp(Model model,@RequestParam(value="page", defaultValue="0")int page,
                             @RequestParam(value = "serch",required = false)String serch,@RequestParam(name = "date_start", defaultValue = "0") String dateStart ,
                             @RequestParam(name = "date_end", defaultValue = "0") String dateEnd){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Company company = companyRepository.findcom(username);
        model.addAttribute("company", company);

//        log.info("시작날" + dateStart);
//        log.info("끝날" + dateEnd);

        Page<ComExpoApp> getList = null;

        if(serch != null && dateStart != null && dateEnd != null){
            getList = expoService.getSearchList(page,serch,dateStart,dateEnd,company.getCom_code());
//            log.info(getList.getContent().toString());
        }else {
            getList = expoService.getComEAppList(page,company.getCom_code());
        }

        model.addAttribute("getList",getList);
//        log.info(getList.getContent().toString());
        model.addAttribute("TotalPage",getList.getTotalPages());

        return "comuser/mypage/ComExpoAppList";
    }
}
