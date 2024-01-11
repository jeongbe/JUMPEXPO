package com.example.JumpExpo.Controller.admin;

import com.example.JumpExpo.DTO.admin.schedule.EmpForm;
import com.example.JumpExpo.DTO.admin.schedule.FairForm;
import com.example.JumpExpo.DTO.admin.schedule.RecForm;
import com.example.JumpExpo.Entity.admin.ScheduleInsert;
import com.example.JumpExpo.Entity.etc.ExpoCalenderInfo;
import com.example.JumpExpo.Repository.admin.SchInsetExpoRepository;
import com.example.JumpExpo.Repository.etc.ExpoCalenderInfoRepository;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin")
public class ScheduleController {

    //2024.01.10 정정빈
    //관리자 일정관리 컨트롤러

    @Autowired
    ExpoCalenderInfoRepository expoCalenderInfoRepository;

    @Autowired
    SchInsetExpoRepository schInsetExpoRepository;

    //2024.01.10 정정빈
    //취업 박람회 일정 등록 페이지
    @GetMapping("/insert/emp")
    public String insertEmp(){

        return "admin/schedule/ScheduleEmp";
    }

    //2024.01.10 정정빈
    //취업 등록 정보 가져옴
    @PostMapping("/save/emp")
    public String SaveEmp(EmpForm form,@RequestParam(value = "ExpoImage",required = false) MultipartFile file1){

//        log.info(form.toString());


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

        ScheduleInsert data = form.toEntity();

        data.setExpo_cate(0);
        ScheduleInsert save = schInsetExpoRepository.save(data);
//            log.info(save.toString());



        return "redirect:/admin/Calender";
    }

    //2024.01.10 정정빈
    //페이 박람회 일정 등록 페이지
    @GetMapping("/insert/Fair")
    public String insertFair(){

        return "admin/schedule/ScheduleFair";
    }

    //2024.01.10 정정빈
    //페어 박람회 정보 등록
    @PostMapping("/save/fair")
    public String SaveFair(FairForm form,@RequestParam(value = "FExpoImage",required = false) MultipartFile file1){
        log.info(form.toString());

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

        ScheduleInsert data = form.toEntity();
        data.setExpo_cate(1);
        ScheduleInsert save = schInsetExpoRepository.save(data);


        return "redirect:/admin/Calender";
    }

    // 채용 박람회 일정 등록 페이지
    @GetMapping("/insert/Rec")
    public String insertRec(){



        return "admin/schedule/ScheduleRec";
    }

    //2024.01.10 정정빈
    //채용 박람회 정보 등록
    @PostMapping("/save/Rec")
    public String SaveRec(RecForm form, @RequestParam(value = "RExpoImage",required = false) MultipartFile file1){
        log.info(form.toString());

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

        ScheduleInsert data = form.toEntity();
        data.setExpo_cate(2);
        ScheduleInsert save = schInsetExpoRepository.save(data);
//        log.info(save.toString());


        return "redirect:/admin/Calender";
    }

    //2024.01.10 정정빈
    //일정 관리 (달력)
    @GetMapping("/Calender")
    public String Calender(Model model){

        List t1 = schInsetExpoRepository.startDay();
//        log.info(t1.toString());

        List ExpoName = schInsetExpoRepository.ExpoName();
//        log.info(ExpoName.toString());

        //날짜
        model.addAttribute("ExpoStartDay",t1);
        
        //박람회 이름
        model.addAttribute("ExpoNames",ExpoName);

        List<ExpoCalenderInfo> info = expoCalenderInfoRepository.getInfo();
//        log.info(info.toString());
        model.addAttribute("EInfo",info);

        return "admin/schedule/ScheduleCalender";
    }


    //2024.01.10 정정빈
    //등록한 박람회 정보 보기
    @GetMapping("/expo/info/{expo_code}")
    public String ExpoInfo(@PathVariable("expo_code")  int expoCode, Model model){

//        log.info(String.valueOf(expoCode));

        ScheduleInsert ExpoInfo = schInsetExpoRepository.findById(expoCode).orElse(null);
//        log.info(ExpoInfo.toString());
        model.addAttribute("ExpoInfo",ExpoInfo);

        return "admin/schedule/AdExpoInfo";
    }

    //2024.01.10 정정빈
    //박람회 정보 수정 페이지
    @GetMapping("/expo/update/{expo_code}/{expo_cate}")
    public String ExpoUpdate(Model model,@PathVariable("expo_code")  int expoCode,@PathVariable("expo_cate")  int expoCate){

        if(expoCate == 0){
            //취업
            ScheduleInsert data = schInsetExpoRepository.findById(expoCode).orElse(null);
            log.info(data.toString());
            model.addAttribute("Data",data);

            return "admin/schedule/ScheduleEmpUpdate";
        }else if(expoCate == 1){
            //페어
            ScheduleInsert data = schInsetExpoRepository.findById(expoCode).orElse(null);
            log.info(data.toString());

            model.addAttribute("Data",data);



            return "admin/schedule/ScheduleFairUpdate";
        }else if(expoCate ==2){
            //채용
            ScheduleInsert data = schInsetExpoRepository.findById(expoCode).orElse(null);
            log.info(data.toString());
            model.addAttribute("Data",data);

            return "admin/schedule/ScheduleRecUpdate";
        }

        return null;
    }

    //2024.01.10 정정빈
    // 박람회 정보 수정 컨트롤러
    @PostMapping("/update/{expo_code}/{expo_cate}")
    public String ExpoUpdate(@PathVariable("expo_code")  int expoCode,FairForm Fform,EmpForm Eform,RecForm Rform ,@PathVariable("expo_cate")  int expoCate){

//        log.info(form.toString());


        // 수정할 박람회의 코드를 가져옴
        ScheduleInsert existingData = schInsetExpoRepository.findById(expoCode).orElse(null);

        if (existingData != null && expoCate == 1) {
            //페어
            // 기존 데이터가 존재하면 수정
            existingData.updateDataFromForm(Fform);
            existingData.setExpo_cate(1);  // 카테고리 설정 (페어 박람회)
            schInsetExpoRepository.save(existingData);
        }else if(existingData != null && expoCate == 0){
            //취업
            existingData.updateDataFromForm(Eform);
            existingData.setExpo_cate(0);  // 카테고리 설정 (취업 박람회)
            schInsetExpoRepository.save(existingData);
        }else if(existingData != null && expoCate == 2){
            //채용
            existingData.updateDataFromForm(Rform);
            existingData.setExpo_cate(2);  // 카테고리 설정 (채용 박람회)
            schInsetExpoRepository.save(existingData);
        }

        return "redirect:/admin/Calender";
    }

    //2024.01.10 정정빈
    //박람회 삭제하기
    @GetMapping("/expo/delete/{expo_code}")
    public String ExpoInfo(@PathVariable("expo_code")  int expoCode){

        ScheduleInsert D_target = schInsetExpoRepository.findById(expoCode).orElse(null);

        schInsetExpoRepository.delete(D_target);

        return "redirect:/admin/Calender";
    }
}
