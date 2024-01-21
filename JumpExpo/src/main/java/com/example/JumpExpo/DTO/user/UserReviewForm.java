package com.example.JumpExpo.DTO.user;

import com.example.JumpExpo.Entity.user.UserReview;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserReviewForm {

    private int ReNum;

    private int UserCode;

    private int ExpoCode;

    private String  UserId;

    private String ReTitle;

    private String ReContent;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ReDate;

    private int ReCnt;

    private MultipartFile ReImage1;
    private MultipartFile ReImage2;
    private MultipartFile ReImage3;

    //수정 날짜
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ModiDate;

    private int ExpoCate;

    public UserReview toEntity() {
        return new UserReview(ReNum,UserCode,ExpoCode,UserId,ReTitle,ReContent,ReDate,ReCnt,
                ReImage1 != null ? ReImage1.getOriginalFilename() : null,
                ReImage2 != null ? ReImage2.getOriginalFilename() : null,
                ReImage3 != null ? ReImage3.getOriginalFilename() : null,
                ModiDate,ExpoCate);
    }
}
