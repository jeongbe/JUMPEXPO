package com.example.JumpExpo.Repository.admin;

import com.example.JumpExpo.Entity.admin.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Integer> {
    @Override
    ArrayList<Notice> findAll();

    //2024.01.31 정정빈
    //메인페이지 공지
    @Query(value = "select *\n" +
            "from notice\n" +
            "order by not_date desc\n" +
            "limit 5" ,nativeQuery = true)
    public List<Notice> mainNotice();
}
