package com.example.JumpExpo.DTO.user;
//2024.01.08 유수민 큐앤에이 Form
//큐앤에이 DTO

import com.example.JumpExpo.Entity.admin.Notice;
import com.example.JumpExpo.Entity.user.QnA;
import jakarta.persistence.Column;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QnAForm {

    int  quNum; // 공지 코드

    String quTitle; // 공지 제목

    String  quContent; // 공지 내용

    int quState; //답변 상태

    int quScr; //비밀글

    public QnA toEntity(){
        return new QnA(quNum,
                quTitle,quContent,quState,quScr);
    }
}
