package com.example.JumpExpo.Controller.comuser;

import com.example.JumpExpo.DTO.user.QnAForm;
import com.example.JumpExpo.Entity.admin.Answer;
import com.example.JumpExpo.Entity.admin.DateEntity;
import com.example.JumpExpo.Entity.comuser.Company;
import com.example.JumpExpo.Entity.user.QnA;
import com.example.JumpExpo.Entity.user.Users;
import com.example.JumpExpo.Repository.admin.AnswerRepository;
import com.example.JumpExpo.Repository.comuser.CompanyRepository;
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
import java.util.Date;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/com")
public class ComQnAController {

    @Autowired
    UserReository userReository;

    @Autowired
    QnARepository qnARepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    AnswerRepository answerRepository;

    //질문 리스트
    //기업용 수정하세요
    @GetMapping("/show/qu")
    public String QuList(Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Company company = companyRepository.findcom(username);
        model.addAttribute("company", company);
        model.addAttribute("comCode",company.getCom_id());

        List<QnA> QnAList = qnARepository.getQnAList(1);
        model.addAttribute("QnAList", QnAList);
        return  "comuser/qna/Qna_List";
    }

    //쓰기 페이지
    @GetMapping("/insert/qu")
    public String insertQu(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Company company = companyRepository.findcom(username);
        model.addAttribute("company", company);

        model.addAttribute("userCode",company.getCom_code());
        return "comuser/qna/Qna_New";
    }

    @PostMapping("/save/qu")
    public String SaveQu(QnAForm form, Model model){
        log.info(form.toString());
        QnA qna = form.toEntity();
        log.info(qna.toString());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Company company = companyRepository.findcom(username);
        model.addAttribute("company", company);

        qna.setQu_state(0);
        qna.setUser_id(company.getCom_id());
        qna.setDivide_code(1);
        qna.setQu_date(new Date());
        QnA saved = qnARepository.save(qna);
        log.info(form.toString());

        return "redirect:/com/show/qu";
    }

    //질문 상세 페이지
    @GetMapping("/show/qu/{quNum}")
    public String Show(@PathVariable("quNum")int quNum, Model model){


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Company company = companyRepository.findcom(username);
        model.addAttribute("company", company);
        model.addAttribute("comid",company.getCom_id());


        log.info("quNum = " + quNum);
        QnA qna = qnARepository.findById(quNum).orElse(null);
        model.addAttribute("qna", qna);

        Answer answer = answerRepository.findById(quNum).orElse(null);
        model.addAttribute("answer", answer);

        //        답변이 이미 있는 경우 답변 있는 페이지로 이동
        if(answer != null && answer.getAn_content() != null) {
            return "comuser/qna/Qna_Answer";
        }
        else { //        답변이 없을 경우 답변 작성 페이지로 이동
            return "comuser/qna/Qna_Detail";
        }
    }

    //미답변 리스트
    @GetMapping("/show/nanswer")
    public String noAnswer(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Company company = companyRepository.findcom(username);
        model.addAttribute("company", company);

        List<QnA> QnAList = qnARepository.getSate();
        model.addAttribute("QnAList", QnAList);
        return "user/qna/Qna_List_No";
    }
    //답변완료 리스트
    @GetMapping("/show/yanswer")
    public String YesAnswer(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 인증된 사용자의 사용자명을 가져옵니다.
        String username = authentication.getName();
        Company company = companyRepository.findcom(username);
        model.addAttribute("company", company);

        List<QnA> QnAList = qnARepository.getState();
        model.addAttribute("QnAList", QnAList);
        log.info(QnAList.toString());
        return "user/qna/Qna_List_Yes";
    }
}
