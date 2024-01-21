package com.example.JumpExpo.Entity.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "review_table")
public class UserReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int re_num;

    @Column
    int user_code;

    @Column
    int expo_code;

    @Column
    String user_id;

    @Column
    String re_title;

    @Column
    String re_content;

    @Column
    @Temporal(TemporalType.DATE)
    Date re_date;

    @Column
    int re_cnt;

    @Column
    String re_image1;
    @Column
    String re_image2;
    @Column
    String re_image3;

    @Column
    @Temporal(TemporalType.DATE)
    Date modi_date;

    @Column
    int expo_cate;

}
