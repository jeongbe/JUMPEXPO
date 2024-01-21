package com.example.JumpExpo.DTO.admin.schedule;

import com.example.JumpExpo.Entity.admin.ScheduleInsert;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

//2024.01.08 정정빈 박람회 등록 Form
//페어 박람회 정보 DTO
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FairForm {

    int FExpoCode;
    int FExpoCate;  //취업 0 페어 1 채용 2
    String FExpoTitle; //25자리

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date FExpoStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date FExpoEnd;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date FApplyStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date FApplyEnd;

    String FExpoAdd;

    MultipartFile FExpoImage;

    String FExpoTime;
    String FExpoEndTime;
    String FExpoOutline;
    String FExpoHost;
    String FExpoManage;
    String FExpoExhibit;
    String FMasterName;
    String FMasterPhone;
    String FMasterEmail;
    String FExpoOccCate;
    String FExpoContent;

    public ScheduleInsert toEntity(){
        return new ScheduleInsert(FExpoCode,FExpoCate,FExpoTitle,FExpoStart,FExpoEnd,FApplyStart,FApplyEnd,
                FExpoAdd,FExpoImage != null ? FExpoImage.getOriginalFilename() : null,FExpoTime,FExpoEndTime,FExpoOutline,FExpoHost,FExpoManage,FExpoExhibit,
                FMasterName,FMasterPhone,FMasterEmail,FExpoOccCate,FExpoContent);
    }
}
