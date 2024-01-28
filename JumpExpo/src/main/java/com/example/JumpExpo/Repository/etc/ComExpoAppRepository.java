package com.example.JumpExpo.Repository.etc;

import com.example.JumpExpo.Entity.admin.ScheduleInsert;
import com.example.JumpExpo.Entity.etc.ComExpoApp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ComExpoAppRepository extends JpaRepository<ComExpoApp,Integer> {

    //2024.01.27 정정빈
    //기업 박람회 신청 심사 여부
    @Query(value = "select s.expo_code,s.expo_cate,s.expo_title,s.expo_start,s.expo_end,s.expo_add," +
            " s.expo_image,s.expo_time, s.expo_end_time, c.recog_check\n" +
            "from schedule_insert s\n" +
            "join c_expo_apply c\n" +
            "on s.expo_code = c.expo_code\n" +
            "where c.com_code = :comCode", nativeQuery = true)
    public Page<ComExpoApp> getComExpoAppList(Pageable pageable, @Param("comCode") int comCode);
}
