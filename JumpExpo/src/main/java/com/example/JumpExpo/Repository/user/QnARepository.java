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

}

