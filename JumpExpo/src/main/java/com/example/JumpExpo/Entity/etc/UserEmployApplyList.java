package com.example.JumpExpo.Entity.etc;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserEmployApplyList {

    @Id
    int pem_appnum;

    @Column
    int user_code;

    @Column
    int emnot_code;

    @Column
    String user_name;

    @Column
    int pem_can;





    @Column
    String emnot_title; //제목 25자리 제한

    @Temporal(TemporalType.DATE)
    Date emnot_start;

    @Temporal(TemporalType.DATE)
    Date emnot_end;


    @Column
    int com_code;
}
