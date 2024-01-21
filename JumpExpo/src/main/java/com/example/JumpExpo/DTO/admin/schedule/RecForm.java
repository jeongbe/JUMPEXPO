package com.example.JumpExpo.DTO.admin.schedule;

import com.example.JumpExpo.Entity.admin.ScheduleInsert;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

//2024.01.08 정정빈 취업박람회 등록 Form
//채용 박람회 정보 DTO
@ToString
@Getter
@Setter
@NoArgsConstructor
public class RecForm {

    int RExpoCode;
    int RExpoCate;  //취업 0 페어 1 채용 2
    String RExpoTitle; //25자리

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date RExpoStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date RExpoEnd;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date RApplyStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date RApplyEnd;

    String RExpoAdd;

    MultipartFile RExpoImage;

    String RExpoTime;
    String RExpoEndTime;
    String RExpoOutline;
    String RExpoHost;
    String RExpoManage;
    String RExpoExhibit;
    String RMasterName;
    String RMasterPhone;
    String RMasterEmail;
    String RExpoOccCate;
    String RExpoContent;

    public ScheduleInsert toEntity(){
        return new ScheduleInsert(RExpoCode,RExpoCate,RExpoTitle,RExpoStart,RExpoEnd,RApplyStart,RApplyEnd,
                RExpoAdd,RExpoImage != null ? RExpoImage.getOriginalFilename() : null,RExpoTime,RExpoEndTime,RExpoOutline,RExpoHost,RExpoManage,RExpoExhibit,
                RMasterName,RMasterPhone,RMasterEmail,RExpoOccCate,RExpoContent);
    }
}
