package com.example.JumpExpo.Entity.admin;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@MappedSuperclass
@EntityListeners(value = { AuditingEntityListener.class })
@Getter
@Setter
public class DateEntity {

    @CreatedDate
    @Column(updatable = false)
    @Temporal(TemporalType.DATE)
    private Date not_date; // 공지사항 등록일

    @CreatedDate
    @Column(updatable = false)
    @Temporal(TemporalType.DATE)
    private Date qu_date; // 큐앤에이 질문 등록일

    @CreatedDate
    @Column(updatable = false)
    @Temporal(TemporalType.DATE)
    private Date an_date; // 큐앤에이 답변 등록일

}
