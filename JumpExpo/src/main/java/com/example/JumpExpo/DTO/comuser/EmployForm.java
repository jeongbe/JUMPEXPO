package com.example.JumpExpo.DTO.comuser;

import com.example.JumpExpo.Entity.comuser.ApplyEmploy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

//2024.01.08 박은채 채용 공고 신청 등록 Form
// 채용 공고 신청 등록 Form

@ToString
@Getter
@Setter
@NoArgsConstructor
public class EmployForm {

    int EmnotCode;
    String EmnotTitle;
    String EmnotContent;
    String EmnotOcc;
    int RecogCheck;
    int InterCate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date EmnotStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date EmnotEnd;

    String EmnotCareer;
    String EmnotEducation;
    String EmnotArea;
    String EmnotState;
    String EmnotSalary;
    int ComCode;
//    MultipartFile EmnotImage;

//    Date EmnotDate;

    public ApplyEmploy toEnttiy(){
        return new ApplyEmploy(EmnotCode, EmnotTitle, EmnotContent,
                EmnotOcc, RecogCheck, InterCate, EmnotStart, EmnotEnd, EmnotCareer, EmnotEducation, EmnotArea,
                EmnotState, EmnotSalary, ComCode);
    }
}
