package com.example.JumpExpo.Entity.etc;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComExpoApp {

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

    @Column
    String expo_add;

    @Column
    String  expo_image;

    @Column
    String expo_time;

    @Column
    String expo_end_time;

    @Column
    int recog_check;

    @Column
    String app_title;

    @Temporal(TemporalType.DATE)
    Date app_date;
}
