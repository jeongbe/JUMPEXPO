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
    //수정
    @Query(value = "select *\n" +
            "from schedule_insert", nativeQuery = true)
    Page<ScheduleInsert> AllExpoList(Pageable pageable);

    //2024.01.17 정정빈
    //행사 리스트 - 페어
    @Query(value = "SELECT *\n" +
            "FROM schedule_insert\n" +
            "WHERE CURDATE() BETWEEN DATE(apply_start) AND DATE(apply_end)\n" +
            "AND expo_cate = 1;", nativeQuery = true)
    Page<ScheduleInsert> FairAllList(Pageable pageable);

    //2024.01.17 정정빈
    //행사 리스트 - 취업
    @Query(value = "SELECT *\n" +
            "FROM schedule_insert\n" +
            "WHERE CURDATE() BETWEEN DATE(apply_start) AND DATE(apply_end)\n" +
            "AND expo_cate = 0;" , nativeQuery = true)
    Page<ScheduleInsert> EmpAllList(Pageable pageable);

    //2024.01.17 정정빈
    //행사 리스트 - 채용
    @Query(value = "SELECT *\n" +
            "FROM schedule_insert\n" +
            "WHERE CURDATE() BETWEEN DATE(apply_start) AND DATE(apply_end)\n" +
            "AND expo_cate = 2;" , nativeQuery = true)
    Page<ScheduleInsert> RecAllList(Pageable pageable);

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


    //2024.01.17 정정빈
    //유저 행사일정 페어 검색 쿼리
    @Query(value = "SELECT *\n" +
            "FROM schedule_insert\n" +
            "WHERE CURDATE() BETWEEN DATE(apply_start) AND DATE(apply_end)\n" +
            "and expo_start BETWEEN :StartDate AND date_add(:EndDate, INTERVAL 1 Month)\n" +
            "and expo_title like %:text% \n" +
            "and expo_cate = 1", nativeQuery = true)
    Page<ScheduleInsert> serchFair(Pageable pageable, @Param("text") String text,@Param("StartDate") String StartDate, @Param("EndDate") String EndDate);

    //2024.01.17 정정빈
    //유저 행사일정 취업 검색 쿼리
    @Query(value = "SELECT *\n" +
            "FROM schedule_insert\n" +
            "WHERE CURDATE() BETWEEN DATE(apply_start) AND DATE(apply_end)\n" +
            "and expo_start BETWEEN :StartDate AND date_add(:EndDate, INTERVAL 1 Month)\n" +
            "and expo_title like %:text% \n" +
            "and expo_cate = 0", nativeQuery = true)
    Page<ScheduleInsert> serchEmp(Pageable pageable, @Param("text") String text,@Param("StartDate") String StartDate, @Param("EndDate") String EndDate);

    //2024.01.17 정정빈
    //유저 행사일정 채용 쿼리
    @Query(value = "SELECT *\n" +
            "FROM schedule_insert\n" +
            "WHERE CURDATE() BETWEEN DATE(apply_start) AND DATE(apply_end)\n" +
            "and expo_start BETWEEN :StartDate AND date_add(:EndDate, INTERVAL 1 Month)\n" +
            "and expo_title like %:text% \n" +
            "and expo_cate = 2", nativeQuery = true)
    Page<ScheduleInsert> serchRec(Pageable pageable, @Param("text") String text,@Param("StartDate") String StartDate, @Param("EndDate") String EndDate);


    //2024.01.18 정정빈
    //유저 마이페이지 박람회 신청 내역
    //s나중에 타임 수정
    @Query(value = "select s.expo_code,s.expo_cate,s.expo_title, s.expo_start, s.expo_end, s.apply_start ,s.apply_end\n" +
            ",s.expo_add, s.expo_image, s.expo_time, s.expo_outline, s.expo_host, s.expo_manage, s.expo_exhibit\n" +
            ",s.master_name, s.master_phone, s.master_mail, s.expo_occ_cate, s.expo_content\n" +
            "from user_expo_apply u\n" +
            "join schedule_insert s\n" +
            "on u.expo_code = s.expo_code\n" +
            "where u.user_code = :userCode" , nativeQuery = true)
    Page<ScheduleInsert> UserAppExpoList(Pageable pageable, @Param("userCode") int userCode);

    //2024.01.18 정정빈
    //유저 마이페이지 박람회 신청 내역 검색
    //s나중에 타임 수정
    @Query(value = "select s.expo_code,s.expo_cate,s.expo_title, s.expo_start, s.expo_end, s.apply_start ,s.apply_end\n" +
            ",s.expo_add, s.expo_image, s.expo_time, s.expo_outline, s.expo_host, s.expo_manage, s.expo_exhibit\n" +
            ",s.master_name, s.master_phone, s.master_mail, s.expo_occ_cate, s.expo_content\n" +
            "from user_expo_apply u\n" +
            "join schedule_insert s\n" +
            "on u.expo_code = s.expo_code\n" +
            "where u.user_code = :userCode\n" +
            "and s.expo_start BETWEEN :StartDate AND date_add(:EndDate, INTERVAL 1 Month)\n" +
            "and s.expo_title like %:text%" , nativeQuery = true)
    Page<ScheduleInsert> UserAppExpoListSerch(Pageable pageable, @Param("userCode") int userCode,@Param("text") String text,@Param("StartDate") String StartDate, @Param("EndDate") String EndDate);

}
