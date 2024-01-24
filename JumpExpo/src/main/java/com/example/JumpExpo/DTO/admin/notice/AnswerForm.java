package com.example.JumpExpo.DTO.admin.notice;

import com.example.JumpExpo.Entity.admin.Answer;
import com.example.JumpExpo.Entity.user.QnA;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerForm {
    int anNum; // 답변 번호

    String anContent; // 답변 내용

    int quNum;

    public Answer toEntity(){
        return new Answer(anNum,
                anContent,quNum);
    }
}
