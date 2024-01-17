package com.example.JumpExpo.DTO.user;

import com.example.JumpExpo.Entity.user.UserExpoApply;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserExpoApplyForm {

    //신청 번호
    int AppNum;

    //유저 코드
    int UserCode;

    //박람회 코드
    int ExpoCode;

    //박람회 구분 코드
    int ExpoCate;

    public UserExpoApply toEntity(){

        return new UserExpoApply(AppNum,UserCode,ExpoCode,ExpoCate);
    }
}
