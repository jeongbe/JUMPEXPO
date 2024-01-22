package com.example.JumpExpo.Repository.user;

import com.example.JumpExpo.Entity.user.UserReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserReviewRepository extends JpaRepository<UserReview,Integer> {

    //2024.01.22 정정빈
    //리뷰 리스트
    @Query(value = "select *\n" +
            "from review_table\n" +
            "where user_code = :userCode",nativeQuery = true)
    Page<UserReview> UserReList(Pageable pageable, @Param("userCode") int userCode);


    //리뷰 디테일
    @Query(value = "select *\n" +
            "from review_table\n" +
            "where user_code = :userCode",nativeQuery = true)
    UserReview UserReinfo(@Param("userCode") int userCode);


    //리뷰 작성 여부
    @Query(value = "select *\n" +
            "from review_table\n" +
            "where user_code = :userCode\n" +
            "and expo_code = :expoCode", nativeQuery = true)
    Boolean UserR(@Param("userCode") int userCode, @Param("expoCode") int expoCode);

}
