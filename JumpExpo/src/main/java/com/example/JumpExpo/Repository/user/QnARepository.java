package com.example.JumpExpo.Repository.user;

import com.example.JumpExpo.Entity.admin.Notice;
import com.example.JumpExpo.Entity.user.QnA;
import com.example.JumpExpo.Entity.user.UserInterview;
import com.example.JumpExpo.Entity.user.UserReview;
import com.example.JumpExpo.Entity.user.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface QnARepository extends JpaRepository<QnA, Integer> {
    @Override
    ArrayList<QnA> findAll();
    @Query(value = "SELECT * \n" +
            "FROM qna\n" +
            "where qu_state = 1", nativeQuery = true)
    public List<QnA> getState();

    @Query(value = "SELECT * \n" +
            "FROM qna\n" +
            "where qu_state = 0", nativeQuery = true)
    public List<QnA> getSate();

    //2024.01.31 정정빈
    //메인페이지 qna
    @Query(value = "select *\n" +
            "from qna\n" +
            "where divide_code = :divCode\n" +
            "order by qu_date desc\n" +
            "limit 5" ,nativeQuery = true)
    public List<QnA> getList(@Param("divCode") int divCode);

    //큐엔에이 기업,유저 나누기
    //2024.02.02
    @Query(value = "select *\n" +
            "from qna\n" +
            "where divide_code = :divCode",nativeQuery = true)
    public List<QnA> getQnAList(@Param("divCode") int divCode);

    @Query(value = "SELECT * \n" +
            "FROM qna\n" +
            "where divide_code = 1\n" +
            "and qu_state = 1", nativeQuery = true)
    public List<QnA> getComState();
    @Query(value = "SELECT * \n" +
            "FROM qna\n" +
            "where divide_code = 1\n" +
            "and qu_state = 0", nativeQuery = true)
    public List<QnA> getComSate();

}

