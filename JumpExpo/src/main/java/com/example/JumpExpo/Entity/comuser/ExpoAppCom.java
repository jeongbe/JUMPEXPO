package com.example.JumpExpo.Entity.comuser;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "c_expo_apply")
public class ExpoAppCom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     int capp_num;

    @Column
     int com_code;

    @Column
     int expo_code;

    @Column
     String com_addr;

    @Column
     String app_file_name;

    @Column
     String app_title;

    @Column
     int expo_cate;

    @Column
     int recog_check;

    @Column
     String  occ_cate;

    @CreatedDate
    @Column(updatable = false)
    @Temporal(TemporalType.DATE)
     Date app_date;


}
