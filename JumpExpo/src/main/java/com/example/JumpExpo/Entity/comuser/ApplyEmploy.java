package com.example.JumpExpo.Entity.comuser;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

//2024.01.08 박은채 채용 공고 신청 Entity
//채용 공고 신청 Entity
@Entity
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplyEmploy extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int emnot_code;

    @Column
    String emnot_title; //제목 25자리 제한

    @Column
    String emnot_content;

//    @Column
//    String emnot_image;

    @Column
    String emnot_occ;

    @Column
    int recog_check; //Default 비승인 0  승인 1

    @Column
    int inter_cate; //비대면 0  대면 1

    @Temporal(TemporalType.DATE)
    Date emnot_start;

    @Temporal(TemporalType.DATE)
    Date emnot_end;

    @Column
    String emnot_career;

    @Column
    String emnot_education;

    @Column
    String emnot_area;

    @Column
    String emnot_state;

    @Column
    String emnot_salary;

    @Column
    int com_code;

//    @Column
//    String emnot_image;
}
