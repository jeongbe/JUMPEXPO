package com.example.JumpExpo.Controller.admin;

import com.example.JumpExpo.DTO.admin.NoticeForm;
import com.example.JumpExpo.DTO.admin.schedule.EmpForm;
import com.example.JumpExpo.Entity.admin.Notice;
import com.example.JumpExpo.Entity.admin.ScheduleInsert;
import com.example.JumpExpo.Repository.admin.NoticeRepository;
import com.example.JumpExpo.Repository.admin.SchInsetExpoRepository;
import com.example.JumpExpo.Service.admin.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        //수정 자꾸 0으로 받아서 주석함
        data.setNot_code(0);
        log.info(data.toString());

        Notice save = noticeRepository.save(data);
        log.info(save.toString());

        return "redirect:/admin/show/nt";
    }
    //공지사항 리스트
    @GetMapping("/show/nt")
    public String adminNtList(Model model)
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
    public String show(@PathVariable Integer notCode, Model model){
        log.info("notCode = " + notCode);

        Notice board = noticeService.selectNoticeDetail(notCode);

        Notice detail = noticeRepository.findById(notCode).orElse(null);
        model.addAttribute("notice", detail);
        return "admin/notice/Notice_Detail";
    }
    //공지사항 수정하기
    @GetMapping("/show/nt/{notCode}/edit")
    public String edit(@PathVariable Integer notCode, Model model)
    {
        //1. 수정할 데이터 가져오기
        Notice detail = noticeRepository.findById(notCode).orElse(null);
        //2. 모델에 데이터 등록
        model.addAttribute("notice", detail);

        return "admin/notice/Notice_Edit";
    }

    @PostMapping("/show/nt/update")
    public String update(NoticeForm form)
    {
        log.info(form.toString());
        //1. DTO > 엔티티
        Notice detail = form.toEntity();
        //폼에서 넘겨받은 데이터를 엔티티로 변환후에
        //해당 타겟에 대한 정보로 디비에 접근(ID로 조회)
        Notice UpdateTarget = noticeRepository.findById(detail.getNot_code()).orElse(null);
        //2. 엔티티 > 디비로
        if(UpdateTarget != null)
        {
            //수정 대상이 존재할 경우에만(null이 아닌경우) 수정 or 저장
            noticeRepository.save(detail);
        }
        //3. 디비 저장 후에 리다이렉트
        return "redirect:/admin/show/nt";
    }

    // 공지사항 삭제하기
    @GetMapping("/show/nt/{notCode}/delete")
    public String Delete(@PathVariable Integer notCode, RedirectAttributes ra){
        //삭제할 대상 가져오기
        Notice DeleteTarget = noticeRepository.findById(notCode).orElse(null);
        //대상 엔티티 삭제
        if(DeleteTarget != null)
        {
            //삭제 대상이 존재할 경우에만(null이 아닌경우) 삭제
            noticeRepository.delete(DeleteTarget);
            //addFlashAttribute(키 문자열, 값 문자)
            ra.addFlashAttribute("msg", "Delete Complete");
        }
        // 결과화면 리다이렉트
        return "redirect:/show/nt";
    }

}
