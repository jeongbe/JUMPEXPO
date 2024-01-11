package com.example.JumpExpo.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("ROLE_USER","개인"),
    COM("ROLE_COM", "기업"),
    ADMIN("ROLE_ADMIN","관리자");

    private final String key;
    private final String title;
}
