package com.example.JumpExpo.Entity.admin;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@MappedSuperclass
@EntityListeners(value = { AuditingEntityListener.class })
@Getter

public class DateEntity {
    @CreatedDate
    @Column(updatable = false)
    @Temporal(TemporalType.DATE)
    private Date not_date;

}
