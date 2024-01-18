package com.example.JumpExpo.Entity.comuser;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComInterview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int c_sc_num;

    @Column
    int com_code;

    @Column
    String c_sc_title;

    @Column
    @Temporal(TemporalType.DATE)
    Date c_sc_start;

    @Column
    @Temporal(TemporalType.DATE)
    Date c_sc_end;

    @Column
    String c_start_time;

    @Column
    String c_end_time;

    @Column
    String c_sc_not;
}
