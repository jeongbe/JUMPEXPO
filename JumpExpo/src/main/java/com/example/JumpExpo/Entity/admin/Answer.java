package com.example.JumpExpo.Entity.admin;

import com.example.JumpExpo.Entity.user.QnA;
import jakarta.persistence.*;
import lombok.*;

//2024.01.24 유수민 공지사항 답변 Entity
//답변 Entity
@Entity
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Answer extends DateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int an_num; // 질문 번호

    @Column
    String an_content; // 답변 내용

    @Column
    int qu_num;
}
