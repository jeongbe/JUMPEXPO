package com.example.JumpExpo.Repository.user;

import com.example.JumpExpo.Controller.join.OAuthToken;
import com.example.JumpExpo.Entity.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public interface UserReository extends JpaRepository<Users,Integer> {


    //중복체크 쿼리
    //탈퇴를 하지 않은 회원들중 기업,개인 모두를 비교한다
    @Query(value = "SELECT u.*\n" +
            "FROM usertable u\n" +
            "JOIN company c\n" +
            "ON u.user_sec= c.com_sec\n" +
            "WHERE u.user_id = :userid \n" +
            "OR c.com_id = :userid \n" +
            "LIMIT 1 ", nativeQuery = true)
    Users DupCheck(@Param("userid") String userid);


    //아이디로 조회
    @Query(value = "SELECT * \n" +
            "FROM usertable\n" +
            "WHERE user_id = :userid " +
            "AND user_sec = 1 ", nativeQuery = true)
    Users username(@Param("userid") String userid);


    //아이디 찾기
    @Query(value = "SELECT user_id\n" +
            "FROM usertable\n" +
            "WHERE user_email = :email \n" +
            "AND user_name = :name " , nativeQuery = true)
    String userid(@Param("email") String email, @Param("name") String name);


    //비밀번호 찾기
    @Query(value = "SELECT user_id \n" +
            "FROM usertable\n" +
            "WHERE user_id = :userid\n" +
            "AND user_name = :username \n" +
            "AND user_email = :email " , nativeQuery = true)
    String findpw(@Param("userid") String userid, @Param("username") String username, @Param("email") String email);

    //아이디 기준으로 유저정보 가져오기
    @Query(value = "SELECT *\n" +
            "FROM usertable\n" +
            "WHERE user_id = :userid " +
            "AND user_sec = 1 " ,nativeQuery = true)
    Users finduser(@Param("userid") String userid);


    //카카오 로그인시 이메일과 닉네임(이름)으로 조회할것임
    @Query(value = "SELECT *\n" +
            "FROM usertable\n" +
            "WHERE user_email = :email \n" +
            "AND user_name = :nickname ", nativeQuery = true)
    Users kakao(@Param("email") String email, @Param("nickname") String name);

    //이메일로 유저정보 가져오기
    //카카오 로그인시 이메일과 닉네임(이름)으로 조회할것임
    @Query(value = "SELECT *\n" +
            "FROM usertable\n" +
            "WHERE user_email = :email " +
            "Limit 1 \n", nativeQuery = true)
    Users kakao2(@Param("email") String email);



}
