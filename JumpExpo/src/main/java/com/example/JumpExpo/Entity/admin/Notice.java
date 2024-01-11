package com.example.JumpExpo.Entity.admin;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

//2024.01.08 유수민 공지사항 등록 Entity
//공지사항 Entity
@Entity
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notice extends DateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int not_code; // 공지 번호

    @Column
    String not_title; //공지 제목

    @Column
    String not_content; // 공지 내용

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int not_cnt; //조회수

    @Column
    String not_file; // 첨부 파일

    public Notice updateNotCnt(Integer not_cnt){
        this.not_cnt = not_cnt+1;
        return this;
    }

}
