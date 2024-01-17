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
public class UserInterview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int sc_num;

    @Column
    int user_code;

    @Column
    String sc_title;

    @Column
    @Temporal(TemporalType.DATE)
    Date sc_start;

    @Column
    @Temporal(TemporalType.DATE)
    Date sc_end;

    @Column
    String start_time;

    @Column
    String end_time;

    @Column
    String sc_not;
}
