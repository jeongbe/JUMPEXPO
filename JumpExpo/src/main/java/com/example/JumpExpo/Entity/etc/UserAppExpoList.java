package com.example.JumpExpo.Entity.etc;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAppExpoList {

    @Id
    int expo_code;

    @Column
    int expo_cate;
}
