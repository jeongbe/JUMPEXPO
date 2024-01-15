package com.example.JumpExpo.DTO.user;

import com.example.JumpExpo.Entity.user.PeremApplyUser;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

//2024.01.12 박은채
//채용 공고 보기 Dto
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PeremApplyUserForm {

    int PemAppnum;

    int UserCode;

    int EmnotCode;

    String UserName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date PemDate;

    MultipartFile PemFile;

    int PemCan;

    public PeremApplyUser toEntity(){
        return new PeremApplyUser(PemAppnum, UserCode, EmnotCode, UserName,
                PemDate, PemFile != null ? PemFile.getOriginalFilename() : null, PemCan);
    }
}
