package com.example.JumpExpo.Entity.user;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

//2024.01.12 박은채
//채용 공고 보기 Entity
@Entity
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PeremApplyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int pem_appnum;

    @Column
    int user_code;

    @Column
    int emnot_code;

    @Column
    String user_name;

    @Column
    Date pem_date;

    @Column
    String pem_file;

    @Column
    int pem_can;

}
