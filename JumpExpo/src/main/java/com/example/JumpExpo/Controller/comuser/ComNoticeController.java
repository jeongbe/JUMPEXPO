package com.example.JumpExpo.Controller.comuser;

import com.example.JumpExpo.Entity.admin.Notice;
import com.example.JumpExpo.Entity.comuser.Company;
import com.example.JumpExpo.Repository.admin.NoticeRepository;
import com.example.JumpExpo.Repository.comuser.CompanyRepository;
import com.example.JumpExpo.Service.admin.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Slf4j
@Controller
@RequestMapping("/com")
public class ComNoticeController {

    @Autowired
    NoticeRepository noticeRepository;

    @Autowired
    NoticeService noticeService;

    @Autowired
    CompanyRepository companyRepository;

    //공지사항 리스트
    @GetMapping("/show/nt")
    public String AdminNtList(Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Company company = companyRepository.findcom(username);
        model.addAttribute("company", company);

        //1. 디비에서 notice 테이블에 있는 모든 데이터 가져오기
        ArrayList<Notice> NoticeList = noticeRepository.findAll();

        //2. Notice 묶음을 모델에 등록( Entity > Model )
        model.addAttribute("NoticeList", NoticeList);

        //3. 뷰에 모델 뿌리기
        return  "comuser/notice/Notice_List";
    }
    //공지사항 상세 페이지
    @GetMapping("/show/nt/{notCode}")
    public String Show(@PathVariable("notCode") Integer notCode, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Company company = companyRepository.findcom(username);
        model.addAttribute("company", company);

        Notice board = noticeService.selectNoticeDetail(notCode);

        Notice detail = noticeRepository.findById(notCode).orElse(null);
        model.addAttribute("notice", detail);
        return "comuser/notice/Notice_Detail";
    }
}
