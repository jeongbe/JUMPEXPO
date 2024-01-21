package com.example.JumpExpo.DTO.admin.schedule;

import com.example.JumpExpo.Entity.admin.ScheduleInsert;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

//2024.01.08 정정빈 박람회 등록 Form
//취업 박람회 정보 DTO
@ToString
@Getter
@Setter
@NoArgsConstructor
public class EmpForm {

    int ExpoCode;
    int ExpoCate;  //취업 0 페어 1 채용 2
    String ExpoTitle; //25자리

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date ExpoStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date ExpoEnd;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date ApplyStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date ApplyEnd;

    String ExpoAdd;

    MultipartFile ExpoImage;

    String ExpoTime;
    String ExpoEndTime;
    String ExpoOutline;
    String ExpoHost;
    String ExpoManage;
    String ExpoExhibit;
    String MasterName;
    String MasterPhone;
    String MasterEmail;
    String ExpoOccCate;
    String ExpoContent;

    public ScheduleInsert toEntity(){
        return new ScheduleInsert(ExpoCode,ExpoCate,ExpoTitle,ExpoStart,ExpoEnd,ApplyStart,ApplyEnd,
                ExpoAdd,ExpoImage != null ? ExpoImage.getOriginalFilename() : null,ExpoTime,ExpoEndTime,ExpoOutline,ExpoHost,ExpoManage,ExpoExhibit,
                MasterName,MasterPhone,MasterEmail,ExpoOccCate,ExpoContent);
    }
}
