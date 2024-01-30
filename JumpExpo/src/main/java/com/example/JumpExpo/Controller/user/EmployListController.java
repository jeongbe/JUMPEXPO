package com.example.JumpExpo.Controller.user;

import com.example.JumpExpo.DTO.user.PeremApplyUserForm;
import com.example.JumpExpo.Entity.comuser.ApplyEmploy;
import com.example.JumpExpo.Entity.comuser.Company;
import com.example.JumpExpo.Entity.user.PeremApplyUser;
import com.example.JumpExpo.Entity.user.Users;
import com.example.JumpExpo.Repository.comuser.ApplyEmployRepository;
import com.example.JumpExpo.Repository.comuser.CompanyRepository;
import com.example.JumpExpo.Repository.user.PeremApplyRepository;
import com.example.JumpExpo.Repository.user.UserReository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

//2024.01.11 박은채 채용 공고 보기 Controller
//채용 공고 보기 Controller
@Slf4j
@Controller
@RequestMapping("/users")
public class EmployListController {

    @Autowired
    ApplyEmployRepository applyEmployRepository;

    @Autowired
    PeremApplyRepository peremApplyRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    UserReository userReository;

    //2024.01.15 박은채
    //공고 보기 리스트<분야별 리스트>
    @GetMapping("/show/employlist")
    public String empoyList(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Users users = userReository.finduser(username);
        model.addAttribute("users", users);
        model.addAttribute("userCode", users.getUser_code());

        ArrayList<ApplyEmploy> allList = applyEmployRepository.AllEmployList();
        ArrayList<ApplyEmploy> DesignList = applyEmployRepository.DesignEmployList();
        ArrayList<ApplyEmploy> FrontList = applyEmployRepository.FrontEmployList();
        ArrayList<ApplyEmploy> BackendList = applyEmployRepository.BackendEmployList();
        ArrayList<ApplyEmploy> EtcList = applyEmployRepository.EtcEmployList();

        model.addAttribute("allList", allList);
        log.info(allList.toString());
        model.addAttribute("designList", DesignList);
        model.addAttribute("frontList", FrontList);
        model.addAttribute("backendList", BackendList);
        model.addAttribute("etcList", EtcList);

        // 각 공고별 지원자 수
        model.addAttribute("applicantCounts", allList.stream()
                .collect(Collectors.toMap(ApplyEmploy::getEmnot_code, e -> peremApplyRepository.countByEmnotCode(e.getEmnot_code()))));

        return "user/employ/EmployList";
    }

    // 공고 보기 상세 페이지
    @GetMapping("/show/employlist/{emnot_code}/{com_code}/{user_code}")
    public String employDetail(Model model, @PathVariable(name="emnot_code") int emnotCode,
                               @PathVariable(name = "com_code") int comCode,
                               @PathVariable("user_code") int userCode){

        model.addAttribute("userCode", userCode);

        ApplyEmploy applyEmploy = applyEmployRepository.findById(emnotCode).orElse(null);
        Company company = companyRepository.findById(comCode).orElse(null);

        if (applyEmploy == null) {
            // 데이터가 없을 경우
            return "users/show/employlist";
        }

        model.addAttribute("applyEmploy", applyEmploy);
        model.addAttribute("company",company);

        return "user/employ/EmployListDetail";
    }

    //이력서 제출 팝업
    @GetMapping("/submit/resume/{emnot_code}/{user_code}")
    public String submitResume(Model model, @PathVariable(name="emnot_code") int emnotCode,
                               @PathVariable(name = "user_code") int userCode){

        model.addAttribute("emnotCode",emnotCode);
        model.addAttribute("userCode",userCode);

        Users user = userReository.findById(userCode).orElse(null);
        if (user != null) {
            model.addAttribute("userName", user.getUser_name());
        } else {
            // 사용자를 찾을 수 없을 때
            model.addAttribute("userName", "사용자 이름 없음");
        }

        ApplyEmploy applyEmploy = applyEmployRepository.findById(emnotCode).orElse(null);

        if (applyEmploy == null) {
            // 데이터가 없을 경우
            return "show/employlist/{emnot_code}";
        }

        model.addAttribute("applyEmploy", applyEmploy);

        return "user/employ/ResumePopup";
    }

    //2024.01.15 박은채, 2024.01.19 박은채 수정
    //이력서 제출&중복확인
    @PostMapping("/submit/resume/{emnot_code}/{user_code}")
    public ResponseEntity<?> submitResumeData(PeremApplyUserForm form,
                                              @RequestParam(value = "PemFile", required = false) MultipartFile file1,
                                              Model model, @PathVariable(name="emnot_code") int emnotCode,
                                              @PathVariable(name = "user_code") int userCode) {

        PeremApplyUser existingApplication = peremApplyRepository.findByEmnotCodeAndUserCode(emnotCode, userCode);

        if (existingApplication != null) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("이미 이력서를 제출한 회사입니다.");
        }

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
//            return "";
        }

        log.info(form.toString());

//        Users users = userReository.findById(userCode).orElse(null);
//        model.addAttribute("users",users);

        PeremApplyUser peremApplyUser = form.toEntity();
        //취소여부 디폴트1
        peremApplyUser.setPem_can(1);
        //열람여부 디폴트0
        peremApplyUser.setPem_check(0);
        peremApplyUser.setPem_date(new Date());

        log.info(peremApplyUser.toString());

        PeremApplyUser saved = peremApplyRepository.save(peremApplyUser);

        return ResponseEntity.ok("성공적으로 지원되었습니다.");

//        return "redirect:/users/show/employlist";
    }

    //2024.01.25 박은채
    //이력서 다시 제출 팝업
    @GetMapping("/submit/resume/re/{emnot_code}/{user_code}")
    public String submitResume2(Model model, @PathVariable(name="emnot_code") int emnotCode,
                               @PathVariable(name = "user_code") int userCode){

        model.addAttribute("emnotCode",emnotCode);
        model.addAttribute("userCode",userCode);

        Users user = userReository.findById(userCode).orElse(null);
        if (user != null) {
            model.addAttribute("userName", user.getUser_name());
        } else {
            // 사용자를 찾을 수 없을 때
            model.addAttribute("userName", "사용자 이름 없음");
        }

        ApplyEmploy applyEmploy = applyEmployRepository.findById(emnotCode).orElse(null);

        if (applyEmploy == null) {
            // 데이터가 없을 경우
            return "show/employlist/{emnot_code}";
        }

        model.addAttribute("applyEmploy", applyEmploy);

        return "user/employ/ResumePopupRe";
    }

    //2024.01.25 박은채
    //이력서 다시 제출
    @PostMapping("/submit/resume/re/{emnot_code}/{user_code}")
    public ResponseEntity<?> submitResumeDataRe(PeremApplyUserForm form,
                                              @RequestParam(value = "PemFile", required = false) MultipartFile file1,
                                              Model model, @PathVariable(name="emnot_code") int emnotCode,
                                              @PathVariable(name = "user_code") int userCode) {

        PeremApplyUser existingApplication = peremApplyRepository.findByEmnotCodeAndUserCode(emnotCode, userCode);

        String link = "\\\\192.168.2.3\\images\\a";

        try {
            if(file1 != null && !file1.isEmpty()){
                String vio1 = link + File.separator + file1.getOriginalFilename();
                Path filePath = Paths.get(vio1);
                Files.copy(file1.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                existingApplication.setPem_file(file1.getOriginalFilename());
            }
        }catch (IOException e) {
            log.error("Error occurred while copying the file: {}", e.getMessage());
            e.printStackTrace();
//            return "";
        }

        log.info(form.toString());

//        PeremApplyUser peremApplyUser = form.toEntity();
        //취소여부 디폴트1
        existingApplication.setPem_can(1);
        //열람여부 디폴트0
        existingApplication.setPem_check(0);
        existingApplication.setPem_date(new Date());

        log.info(existingApplication.toString());

        PeremApplyUser saved = peremApplyRepository.save(existingApplication);

        return ResponseEntity.ok("재신청이 완료되었습니다.");

//        return "redirect:/users/show/employlist";
    }

}
