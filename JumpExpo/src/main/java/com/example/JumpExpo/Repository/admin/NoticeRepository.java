package com.example.JumpExpo.Repository.admin;

import com.example.JumpExpo.Entity.admin.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Integer> {
    @Override
    ArrayList<Notice> findAll();
}
