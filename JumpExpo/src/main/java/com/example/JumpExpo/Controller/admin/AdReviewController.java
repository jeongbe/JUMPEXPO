package com.example.JumpExpo.Controller.admin;

import com.example.JumpExpo.Entity.user.UserReview;
import com.example.JumpExpo.Repository.user.UserReviewRepository;
import com.example.JumpExpo.Service.user.expo.ExpoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdReviewController {

    @Autowired
    ExpoService expoService;

    @Autowired
    UserReviewRepository userReviewRepository;

    //리뷰 관리 리스트
    @GetMapping("/review/list")
    public String ReviewList(Model model,@RequestParam(value="page", defaultValue="0")int page, @RequestParam(value = "eventType", defaultValue="0") int eventType){

//        log.info(String.valueOf(eventType));
//
//        log.info("Event Type: " + eventType);

        Page<UserReview> reList = null;

        if (eventType == 0) {
            // 취업 리뷰
            reList = expoService.getAdUserReviewList(page, eventType);
            model.addAttribute("reList", reList);
            model.addAttribute("TotalPage",reList.getTotalPages());
            return "admin/review/AdReviewList0";

        } else if (eventType == 1) {
            // 페어 리뷰
            reList = expoService.getAdUserReviewList(page, eventType);
            model.addAttribute("reList", reList);
            model.addAttribute("TotalPage",reList.getTotalPages());
            return "admin/review/AdReviewList1";
        } else if (eventType == 2) {
            // 채용 리뷰
            reList = expoService.getAdUserReviewList(page, eventType);
            model.addAttribute("reList", reList);
            model.addAttribute("TotalPage",reList.getTotalPages());
            return "admin/review/AdReviewList2";

        } else {
            reList = expoService.getAdUserReviewList(page, 0);
            model.addAttribute("reList", reList);
            model.addAttribute("TotalPage",reList.getTotalPages());
            return "redirect:/admin/review/list";

        }



//        log.info(reList.getContent().toString());



    }

    //2024.01.23 정정빈
    //관리자 리뷰 삭제
    @DeleteMapping("/review/de/{re_num}")
    public ResponseEntity<String> reviewDelete(@PathVariable("re_num") int reNum){
        log.info(String.valueOf(reNum));

        userReviewRepository.deleteById(reNum);


        return ResponseEntity.ok("삭제완료");
    }

    //2024.01.23 정정빈
    //리뷰 디테일
    @GetMapping("/review/read/{re_num}")
    public String ReviewRead(Model model,@PathVariable("re_num") int renum){
        log.info(String.valueOf(renum));

        UserReview reList = userReviewRepository.findById(renum).orElse(null);

        model.addAttribute("UserReList",reList);

        return "admin/review/AdRevewDe";
    }

}
