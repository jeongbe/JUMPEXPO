package com.example.JumpExpo.Controller.admin;

import com.example.JumpExpo.DTO.admin.notice.NoticeForm;
import com.example.JumpExpo.Entity.admin.Notice;
import com.example.JumpExpo.Repository.admin.NoticeRepository;
import com.example.JumpExpo.Service.admin.NoticeService;
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
import java.util.ArrayList;
//2024-01-08 유수민
//공지사항 등록 컨트롤러
@Slf4j
@Controller
@RequestMapping("/admin")
public class NoticeController {
    @Autowired
    NoticeRepository noticeRepository;

    @Autowired
    NoticeService noticeService;

    //공지사항 등록 페이지
    @GetMapping("/insert/nt")
    public String insertNt(){
        return "admin/notice/Notice_New";
    }

    //공지사항 등록 + 이미지 처리
    @PostMapping("/save/nt")
    public String SaveNt(NoticeForm form, @RequestParam(value = "notFile",required = false) MultipartFile file1){

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

        Notice data = form.toEntity();
        data.setNot_code(0);
        log.info(data.toString());

        Notice save = noticeRepository.save(data);
        log.info(save.toString());

        return "redirect:/admin/show/nt";
    }
    //공지사항 리스트
    @GetMapping("/show/nt")
    public String AdminNtList(Model model)
    {
        //1. 디비에서 notice 테이블에 있는 모든 데이터 가져오기
        ArrayList<Notice> NoticeList = noticeRepository.findAll();

        //2. Notice 묶음을 모델에 등록( Entity > Model )
        model.addAttribute("NoticeList", NoticeList);

        //3. 뷰에 모델 뿌리기
        return  "admin/notice/Notice_List";
    }
    //공지사항 상세 페이지
    @GetMapping("/show/nt/{notCode}")
    public String Show(@PathVariable("notCode") Integer notCode, Model model){
        log.info("notCode = " + notCode);

        Notice board = noticeService.selectNoticeDetail(notCode);

        Notice detail = noticeRepository.findById(notCode).orElse(null);
        model.addAttribute("notice", detail);
        return "admin/notice/Notice_Detail";
    }

    //2024-01-11 유수민
    //공지사항 수정 페이지
    @GetMapping("/show/nt/{notCode}/update")
    public String Edit(@PathVariable("notCode") Integer notCode, Model model)
    {
        //1. 수정할 데이터 가져오기
        Notice detail = noticeRepository.findById(notCode).orElse(null);
        log.info(detail.toString());
        //2. 모델에 데이터 등록
        model.addAttribute("notice", detail);

        return "admin/notice/Notice_Edit";
    }
    //공지사항 수정 컨트롤러
    @PostMapping("/nt/{notCode}/update")
    public String Update(@PathVariable("not_code") Integer notCode)
    {

        Notice detail = noticeRepository.findById(notCode).orElse(null);

        if(detail != null)
        {
            noticeRepository.save(detail);
        }
        return "redirect:/admin/show/nt";
    }

    // 공지사항 삭제하기
    @GetMapping("/show/nt/{notCode}/delete")
    public String Delete(@PathVariable("not_code") Integer notCode){

        Notice DeleteTarget = noticeRepository.findById(notCode).orElse(null);
        noticeRepository.delete(DeleteTarget);
        // 결과화면 리다이렉트
        return "redirect:/show/nt";
    }

}
