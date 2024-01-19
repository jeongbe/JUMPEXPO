package com.example.JumpExpo.Repository.comuser;

import com.example.JumpExpo.Entity.comuser.ApplyEmploy;
import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.ArrayList;

//2024.01.09 박은채
//채용 공고 심사 Repository
@Repository
public interface ApplyEmployRepository extends JpaRepository<ApplyEmploy, Integer> {

    @Query(value = "SELECT *\n" +
            "FROM apply_employ\n" +
            "WHERE recog_check = 0;", nativeQuery = true)
        ArrayList<ApplyEmploy> AllreqEmployList();

    @Query(value = "SELECT *\n" +
            "FROM apply_employ\n" +
            "WHERE recog_check = 1 OR recog_check = 2;\n", nativeQuery = true)
    ArrayList<ApplyEmploy> AlldisreqEmployList();

    //2024.01.10 박은채
//    @Query(value = "SELECT *\n" +
//            "FROM apply_employ\n" +
//            "INNER JOIN comuser ON apply_employ.com_code = comuser.com_code\n" +
//            "WHERE recog_check = 0;", nativeQuery = true)
//    ArrayList<ApplyEmploy> AllreqEmployList();
//
//    @Query(value = "SELECT *\n" +
//            "FROM apply_employ\n" +
//            "INNER JOIN comuser ON apply_employ.com_code = comuser.com_code\n" +
//            "WHERE recog_check = 1;", nativeQuery = true)
//    ArrayList<ApplyEmploy> AlldisreqEmployList();


    //2024.01.11 박은채

    //현재날짜를 기준으로 시작날짜와 끝나는날짜 사이에 있는 전체 데이터 리스트 불러오기
    @Query(value = "SELECT *\n" +
            "FROM apply_employ\n" +
            "WHERE CURDATE() BETWEEN emnot_start AND emnot_end;", nativeQuery = true)
    ArrayList<ApplyEmploy> AllEmployList();

    //(위와동일)디자인 직종 전체 데이터 리스트 불러오기
    @Query(value = "SELECT *\n" +
            "FROM apply_employ\n" +
            "WHERE emnot_occ = '디자인'\n" +
            "AND CURDATE() BETWEEN emnot_start AND emnot_end;", nativeQuery = true)
    ArrayList<ApplyEmploy> DesignEmployList();

    //(위와동일)프론트 직종 전체 데이터 리스트 불러오기
    @Query(value = "SELECT *\n" +
            "FROM apply_employ\n" +
            "WHERE emnot_occ = '프론트'\n" +
            "AND CURDATE() BETWEEN emnot_start AND emnot_end;", nativeQuery = true)
    ArrayList<ApplyEmploy> FrontEmployList();

    //(위와동일)백엔드 직종 전체 데이터 리스트 불러오기
    @Query(value = "SELECT *\n" +
            "FROM apply_employ\n" +
            "WHERE emnot_occ = '백엔드'\n" +
            "AND CURDATE() BETWEEN emnot_start AND emnot_end;", nativeQuery = true)
    ArrayList<ApplyEmploy> BackendEmployList();


    //(위와동일)기타 직종 전체 데이터 리스트 불러오기
    @Query(value = "SELECT *\n" +
            "FROM apply_employ\n" +
            "WHERE emnot_occ NOT IN ('프론트', '디자인', '백엔드')\n" +
            "AND CURDATE() BETWEEN emnot_start AND emnot_end;", nativeQuery = true)
    ArrayList<ApplyEmploy> EtcEmployList();

    //2024.01.18 박은채
    @Query(value = "SELECT *\n" +
            "FROM apply_employ\n" +
            "where com_code = :comCode", nativeQuery = true)
    ArrayList<ApplyEmploy> AllList(@Param("comCode") int comCode);

}
