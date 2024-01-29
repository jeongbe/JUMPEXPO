package com.example.JumpExpo.Repository.comuser;

import com.example.JumpExpo.Entity.comuser.Company;
import com.example.JumpExpo.Entity.user.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface CompanyRepository extends JpaRepository<Company,Integer> {

    //중복체크 쿼리
    @Query(value = "SELECT c.*\n" +
            "FROM usertable u\n" +
            "JOIN company c\n" +
            "ON u.user_sec= c.com_sec\n" +
            "WHERE u.user_id = :comid \n" +
            "OR c.com_id = :comid \n" +
            "LIMIT 1 ", nativeQuery = true)
    Company ComDupCheck(@Param("comid") String comid);

    //아이디로 조회
    @Query(value = "SELECT * \n" +
            "FROM company\n" +
            "WHERE com_id = :comid ", nativeQuery = true)
    Company comname(@Param("comid") String comid);


    //아이디 찾기
    @Query(value = "SELECT com_id\n" +
            "FROM company \n" +
            "WHERE com_email = :email \n" +
            "AND com_manager = :name " , nativeQuery = true)
    String userid(@Param("email") String email, @Param("name") String name);


    //비밀번호 찾기
    @Query(value = "SELECT com_id \n" +
            "FROM company \n" +
            "WHERE com_id = :comid\n" +
            "AND com_manager = :commanager \n" +
            "AND com_email = :comemail ", nativeQuery = true)
    String findcompw(@Param("comid") String comid, @Param("commanager") String commanager, @Param("comemail") String comemail);

    //아이디 기준으로 유저정보 가져오기
    @Query(value = "SELECT *\n" +
            "FROM company\n" +
            "WHERE com_id = :comid " ,nativeQuery = true)
    Company findcom(@Param("comid") String userid);


    //2024-01-22 맹성우
    //탈퇴 여부로 기업 정보 가져오기
    @Query(value = "SELECT *\n" +
            "FROM company\n" +
            "WHERE com_sec = 1;", nativeQuery = true)
    Page<Company> comM(Pageable pageable, @Param("expoCate") int expoCate);



}
