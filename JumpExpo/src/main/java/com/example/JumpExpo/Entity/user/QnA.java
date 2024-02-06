package com.example.JumpExpo.Entity.user;

import com.example.JumpExpo.Entity.admin.DateEntity;
import jakarta.persistence.*;
import lombok.*;

//2024.01.23 유수민 공지사항 질문 Entity
//질문 Entity
@Entity
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QnA extends DateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int qu_num; // 질문 번호

    @Column
    String qu_title; //질문 제목

    @Column
    String qu_content; // 질문 내용

    @Column
    int qu_state;//답변 상태

    @Column
    int qu_scr;//비밀글

    @Column
    String user_id;

    @Column
    String com_id;

    @Column
    int user_code;

    @Column
    int divide_code;
}
