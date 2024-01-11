package com.example.JumpExpo.DTO.comuser;

import com.example.JumpExpo.Entity.comuser.ExpoAppCom;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.MapUtils;

import java.util.Date;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ExpoAppForm {

     int CappNum;

     int ComCode;

     int ExpoCode;

     String ComAddr;

     MultipartFile AppFileName;

     String AppTitle;

     int ExpoCate;

    //승인 여부
     int RecogCheck;

     String OccCate;

    //신청날짜
    @DateTimeFormat(pattern = "yyyy-MM-dd")
     Date AppDate;

    public ExpoAppCom toEntity() {

        return new ExpoAppCom(CappNum,ComCode,ExpoCode,ComAddr,AppFileName != null ? AppFileName.getOriginalFilename() : null,
                AppTitle,ExpoCate,RecogCheck,OccCate,AppDate);
    }


}
