package com.example.JumpExpo.DTO.admin;

import com.example.JumpExpo.Entity.admin.Notice;
import com.example.JumpExpo.Entity.admin.ScheduleInsert;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

//2024.01.08 유수민 공지사항 등록 Form
//공지사항 DTO

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoticeForm {

    int  notCode; // 공지 코드

    String notTitle; // 공지 제목

    String  notContent; // 공지 내용

    int notCnt; // 조회수

    MultipartFile notFile; // 첨부파일

    public Notice toEntity(){
        return new Notice(notCode,
                notTitle,notContent,notCnt,notFile != null ? notFile.getOriginalFilename() : null);
    }
}
