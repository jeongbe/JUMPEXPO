package com.example.JumpExpo.Entity.etc;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpoCalenderInfo {

    @Id
    int expo_code;

    @Column
    String expo_start;

    @Column
    int expo_cate;

    @Column
    String expo_title;
}
