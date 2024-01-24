package com.example.JumpExpo.Controller.admin;

import com.example.JumpExpo.DTO.admin.notice.AnswerForm;
import com.example.JumpExpo.DTO.admin.notice.NoticeForm;
import com.example.JumpExpo.DTO.user.QnAForm;
import com.example.JumpExpo.Entity.admin.Answer;
import com.example.JumpExpo.Entity.admin.Notice;
import com.example.JumpExpo.Entity.user.QnA;
import com.example.JumpExpo.Entity.user.Users;
import com.example.JumpExpo.Repository.admin.AnswerRepository;
import com.example.JumpExpo.Repository.user.QnARepository;
import com.example.JumpExpo.Repository.user.UserReository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

//2024-01-23 유수민
//답변 등록 컨트롤러
@Slf4j
@Controller
public class AnswerController {
    @Autowired
    QnARepository qnARepository;

    @Autowired
    UserReository userReository;

    @Autowired
    AnswerRepository answerRepository;

    //질문 리스트
    @GetMapping("/admin/show/qu")
    public String QuList(Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Users users = userReository.finduser(username);
        model.addAttribute("users", users);

        model.addAttribute("userId",users.getUser_id());

        ArrayList<QnA> QnAList = qnARepository.findAll();
        model.addAttribute("QnAList", QnAList);
        log.info(QnAList.toString());
        return  "admin/qna/Qna_List";
    }
//상세 페이지
    @GetMapping("/admin/show/qu/{quNum}")
    public String Answer(@PathVariable("quNum")int quNum, Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Users users = userReository.finduser(username);
        model.addAttribute("users", users);

        QnA qna = qnARepository.findById(quNum).orElse(null);
        model.addAttribute("qna", qna);
        log.info(qna.toString());

        Answer answer = answerRepository.findById(quNum).orElse(null);
        model.addAttribute("answer", answer);

        model.addAttribute("userName",users.getUser_name());
//        답변이 이미 있는 경우 답변 있는 페이지로 이동
        if(answer != null && answer.getAn_content() != null) {
            return "admin/qna/Qna_Answer";
        }
        else { //        답변이 없을 경우 답변 작성 페이지로 이동
            return "admin/qna/Qna_Detail";
        }
    }
    @PostMapping("/qu/{quNum}/answer")
    public String Update(@PathVariable("quNum")int quNum,AnswerForm form) {
        QnA qna = qnARepository.findById(quNum).orElse(null);
        log.info(qna.toString());
        qna.setQu_state(1);
        QnA save = qnARepository.save(qna);

        log.info(form.toString());
        Answer answer = form.toEntity();
        log.info(answer.toString());
        Answer saved = answerRepository.save(answer);
        log.info(form.toString());

        return "redirect:/admin/show/qu";
    }
}
