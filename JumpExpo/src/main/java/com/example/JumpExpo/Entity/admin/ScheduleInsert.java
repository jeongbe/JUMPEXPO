package com.example.JumpExpo.Entity.admin;

import com.example.JumpExpo.DTO.admin.schedule.EmpForm;
import com.example.JumpExpo.DTO.admin.schedule.FairForm;
import com.example.JumpExpo.DTO.admin.schedule.RecForm;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

//2024.01.08 정정빈 박람회 등록 Entity
//박람회 정보 Entity
@Entity
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleInsert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int expo_code;

    @Column
    int expo_cate;  //취업 0 페어 1 채용 2

    @Column
    String expo_title; //25자리

    @Temporal(TemporalType.DATE)
    Date expo_start;

    @Temporal(TemporalType.DATE)
    Date expo_end;

    @Temporal(TemporalType.DATE)
    Date apply_start;

    @Temporal(TemporalType.DATE)
    Date apply_end;

    @Column
    String expo_add;

    @Column
    String  expo_image;

    @Column
    String expo_time;

    @Column
    String expo_outline;

    @Column
    String expo_host;

    @Column
    String expo_manage;

    @Column
    String expo_exhibit;

    @Column
    String master_name;

    @Column
    String master_phone;

    @Column
    String master_mail;

    @Column
    String expo_occ_cate;

    @Column
    String expo_content;

    // ScheduleInsert 클래스에 추가
    public void updateDataFromForm(FairForm form) {
        this.expo_title = form.getFExpoTitle();
        this.expo_host = form.getFExpoHost();
        this.expo_time = form.getFExpoTime();
        this.expo_manage = form.getFExpoManage();
        this.expo_add = form.getFExpoAdd();
        this.expo_exhibit = form.getFExpoExhibit();
//        this.expo_image = form.getFExpoImage();
        this.master_name = form.getFMasterName();
        this.apply_start = form.getFApplyStart();
        this.apply_end = form.getFApplyEnd();
        this.master_phone = form.getFMasterPhone();
        this.expo_start = form.getFExpoStart();
        this.expo_end = form.getFExpoEnd();
        this.master_mail = form.getFMasterEmail();
        this.expo_outline = form.getFExpoOutline();
        this.expo_content = form.getFExpoContent();
    }

    public void updateDataFromForm(EmpForm form) {
        this.expo_title = form.getExpoTitle();
        this.expo_host = form.getExpoHost();
        this.expo_time = form.getExpoTime();
        this.expo_manage = form.getExpoManage();
        this.expo_add = form.getExpoAdd();
        this.expo_exhibit = form.getExpoExhibit();
//        this.expo_image = form.();
        this.master_name = form.getMasterName();
        this.apply_start = form.getApplyStart();
        this.apply_end = form.getApplyEnd();
        this.master_phone = form.getMasterPhone();
        this.expo_start = form.getExpoStart();
        this.expo_end = form.getExpoEnd();
        this.master_mail = form.getMasterEmail();
        this.expo_outline = form.getExpoOutline();
        this.expo_content = form.getExpoContent();
    }

    public void updateDataFromForm(RecForm form) {
        this.expo_title = form.getRExpoTitle();
        this.expo_host = form.getRExpoHost();
        this.expo_time = form.getRExpoTime();
        this.expo_manage = form.getRExpoManage();
        this.expo_add = form.getRExpoAdd();
        this.expo_exhibit = form.getRExpoExhibit();
//        this.expo_image = form.();
        this.master_name = form.getRMasterName();
        this.apply_start = form.getRApplyStart();
        this.apply_end = form.getRApplyEnd();
        this.master_phone = form.getRMasterPhone();
        this.expo_start = form.getRExpoStart();
        this.expo_end = form.getRExpoEnd();
        this.master_mail = form.getRMasterEmail();
        this.expo_outline = form.getRExpoOutline();
        this.expo_content = form.getRExpoContent();
        this.expo_occ_cate = form.getRExpoOccCate();
    }
}
