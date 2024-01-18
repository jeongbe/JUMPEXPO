package com.example.JumpExpo.DTO.comuser;

import com.example.JumpExpo.Entity.comuser.ComInterview;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComInterviewForm {

    int CScNum;

    int ComCode;

    String CScTitle;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date CScStart;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date CScEnd;

    String CStartTime;

    String CEndTime;

    String CScNot;

    public ComInterview toEntity(){

        return new ComInterview(CScNum,ComCode,CScTitle,CScStart,CScEnd,CStartTime,CEndTime,CScNot);
    }
}
