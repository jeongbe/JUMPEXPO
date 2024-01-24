package com.example.JumpExpo.Controller.user;

import com.example.JumpExpo.Entity.user.UserReview;
import com.example.JumpExpo.Repository.user.UserReviewRepository;
import com.example.JumpExpo.Service.user.expo.ExpoService;
import com.example.JumpExpo.Service.user.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/users")
public class ReviewController {

    @Autowired
    ExpoService expoService;

    @Autowired
    UserReviewRepository userReviewRepository;

    //2024.01.24 정정빈
    //페어 리뷰 리스트

    @GetMapping("/review/fair")
    public String UserReviewFair(Model model,@RequestParam(value="page", defaultValue="0")int page,
                            @RequestParam(value = "serch",required = false)String serch,@RequestParam(value = "target",required = false) String target){
        log.info(serch);
        log.info(target);
        Page<UserReview> list = null;

        //검색 했을때
        if(serch != null){

            //전체
            if("all".equals(target)){
                list = expoService.getUserReSearchList(page,serch,1);

            }else if("hits".equals(target)){ //조회수 많은 순
                list = expoService.getUserReHitList(page,serch,1);
            }

        }else {  //검색어 없을때

            if(target == "all"){
                list = expoService.getAdUserReviewList(page,1);
            }else if(target == "hits"){ //조회수 많은 순
                list = expoService.getUserReHitList2(page,1);
            }else {
                list = expoService.getAdUserReviewList(page,1);

            }

        }
        model.addAttribute("list",list);
        model.addAttribute("TotalPage",list.getTotalPages());

        return "user/review/fairReviewList";
    }

    //2024.01.24 정정빈
    //취업 리뷰 리스트

    @GetMapping("/review/emp")
    public String UserReviewEmp(@RequestParam(value = "serch",required = false)String serch,@RequestParam(value = "target",required = false) String target,Model model,@RequestParam(value="page", defaultValue="0")int page){

        log.info(serch);
        log.info(target);
        Page<UserReview> list = null;

        //검색 했을때
        if(serch != null){

            //전체
            if("all".equals(target)){
                list = expoService.getUserReSearchList(page,serch,0);

            }else if("hits".equals(target)){ //조회수 많은 순
                list = expoService.getUserReHitList(page,serch,0);
            }

        }else {  //검색어 없을때

            if(target == "all"){
                list = expoService.getAdUserReviewList(page,0);
            }else if(target == "hits"){ //조회수 많은 순
                list = expoService.getUserReHitList2(page,0);
            }else {
                list = expoService.getAdUserReviewList(page,0);

            }

        }

        model.addAttribute("list",list);
        model.addAttribute("TotalPage",list.getTotalPages());

        return "user/review/EmpReviewList";
    }

    //2024.01.24 정정빈
    //채용 리뷰 리스트

    @GetMapping("/review/rec")
    public String UserReviewRec(Model model,@RequestParam(value="page", defaultValue="0")int page,
                                @RequestParam(value = "serch",required = false)String serch,@RequestParam(value = "target",required = false) String target){
        log.info(serch);
        log.info(target);
        Page<UserReview> list = null;

        //검색 했을때
        if(serch != null){

            //전체
            if("all".equals(target)){
                list = expoService.getUserReSearchList(page,serch,2);

            }else if("hits".equals(target)){ //조회수 많은 순
                list = expoService.getUserReHitList(page,serch,2);
            }

        }else {  //검색어 없을때

            if(target == "all"){
                list = expoService.getAdUserReviewList(page,2);
            }else if(target == "hits"){ //조회수 많은 순
                list = expoService.getUserReHitList2(page,2);
            }else {
                list = expoService.getAdUserReviewList(page,2);

            }

        }
        model.addAttribute("list",list);
        model.addAttribute("TotalPage",list.getTotalPages());

        return "user/review/RecReviewList";
    }

    //2024.01.24 정정빈
    // 리뷰 디테일
    @GetMapping("/review/read/{re_num}")
    public String ReviewRead(Model model,@PathVariable("re_num") int renum){
//        log.info(String.valueOf(renum));

        UserReview reList = userReviewRepository.findById(renum).orElse(null);

        model.addAttribute("UserReList",reList);

        return "user/review/UserRevewDe";
    }

    @PostMapping("/recount")
    public ResponseEntity<String> ReCount(@RequestParam("reCnt") int reCnt,@RequestParam("ReNum") int renum){
        log.info("Received reCnt: {}", reCnt);
        log.info("Received reCnt: {}", renum);

        UserReview reCount = userReviewRepository.findById(renum).orElse(null);
        reCount.setRe_cnt(reCnt);
        userReviewRepository.save(reCount);
        // 처리 로직
        return ResponseEntity.ok("카운트");
    }

}
