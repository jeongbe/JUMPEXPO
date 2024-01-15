package com.example.JumpExpo.Repository.admin;

import com.example.JumpExpo.Entity.admin.ScheduleInsert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchInsetExpoRepository extends JpaRepository<ScheduleInsert, Integer> {

//    //2024.01.08 정정빈
//    @Query(value = "select expo_start\n" +
//            "from schedule_insert", nativeQuery = true)
//    List startDay();
//
//    //2024.01.08 정정빈
//    @Query(value = "select expo_title\n" +
//            "from schedule_insert", nativeQuery = true)
//    List ExpoName();

    //2024.01.08 정정빈
    //행사 전체 리스트
//    SELECT *
//    FROM schedule_insert
//    WHERE CURDATE() BETWEEN apply_start AND apply_end;
    @Query(value = "select *\n" +
            "from schedule_insert", nativeQuery = true)
    Page<ScheduleInsert> AllExpoList(Pageable pageable);

    //2024.01.11 정정빈
    //행사 전체 리스트에서 검색 단어 있을떄
    @Query(value = "SELECT *\n" +
            "FROM schedule_insert\n" +
            "WHERE expo_start BETWEEN :StartDate AND date_add(:EndDate, INTERVAL 1 Month)\n" +
            "and expo_title like %:text%", nativeQuery = true)
    Page<ScheduleInsert> serch1(Pageable pageable, @Param("text") String text,@Param("StartDate") String StartDate, @Param("EndDate") String EndDate);

    //2024.01.11 정정빈
    //단어 없을때
    @Query(value = "SELECT *\n" +
            "FROM schedule_insert\n" +
            "WHERE expo_start BETWEEN :StartDate AND date_add(:EndDate, INTERVAL 1 Month)", nativeQuery = true)
    Page<ScheduleInsert> serch2(Pageable pageable,@Param("StartDate") String StartDate, @Param("EndDate") String EndDate);

    //행사 전체 리스트 1개월 버튼
    @Query(value = "SELECT *\n" +
            "FROM schedule_insert\n" +
            "WHERE expo_start BETWEEN NOW() AND date_add(NOW(), INTERVAL 1 Month)", nativeQuery = true)
    Page<ScheduleInsert> period1(Pageable pageable);

    //2024.01.12 정정빈
    //관리자 채용 박람회 심사 목록
    @Query(value = "select *\n" +
            "from schedule_insert\n" +
            "where expo_cate = 2", nativeQuery = true)
    Page<ScheduleInsert> RecList(Pageable pageable);

    //2024.01.15 정정빈
    //관리자 페어 박람회 심사 목록
    @Query(value = "select *\n" +
            "from schedule_insert\n" +
            "where expo_cate = 1", nativeQuery = true)
    Page<ScheduleInsert> FairList(Pageable pageable);

    //관리자 취업 박람회 심사 목록
    @Query(value = "select *\n" +
            "from schedule_insert\n" +
            "where expo_cate = 0", nativeQuery = true)
    Page<ScheduleInsert> EmpList(Pageable pageable);
}
