package com.example.JumpExpo.Entity.user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserExpoApply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int app_num;

    @Column
    private int user_code;

    @Column
    private int expo_code;

    @Column
    private int expo_cate;
}
