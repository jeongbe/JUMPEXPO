package com.example.JumpExpo.DTO.user;

import com.example.JumpExpo.Entity.user.UserInterview;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInterviewForm {

    int ScNum;

    int UserCode;

    String ScTitle;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date ScStart;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date ScEnd;

    String StartTime;

    String EndTime;

    String ScNot;

    public UserInterview toEntity(){

        return new UserInterview(ScNum, UserCode, ScTitle, ScStart,ScEnd,StartTime,EndTime,ScNot);
    }
}
