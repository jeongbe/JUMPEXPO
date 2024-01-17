package com.example.JumpExpo.Repository.user;

import com.example.JumpExpo.Entity.user.UserInterview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInterviewRepository extends JpaRepository<UserInterview,Integer> {

    //2024.01.18 정정빈
    //유저 면접 일정 관리 리스트
    @Query(value = "select *\n" +
            "from user_interview\n" +
            "where user_code = :userCode", nativeQuery = true)
    public List<UserInterview> getInterviewsList(@Param("userCode") int userCode);

}
