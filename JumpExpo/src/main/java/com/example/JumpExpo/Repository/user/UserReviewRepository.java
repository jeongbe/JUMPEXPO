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
    UserReview UserR(@Param("userCode") int userCode, @Param("expoCode") int expoCode);

    //2024-01-23 정정빈
    // 관리자 박람회리뷰
    @Query(value = "SELECT *\n" +
            "    FROM review_table\n" +
            "    WHERE expo_cate = :expoCate", nativeQuery = true)
    Page<UserReview> AdReviewList(Pageable pageable, @Param("expoCate") int expoCate);


    //2024.01.24 정정빈
    // 유저 리뷰 검색 했을떄
    @Query(value = "select *\n" +
            "from review_table\n" +
            "where expo_cate = :expoCate\n" +
            "and re_title like %:search%\n" +
            "and re_date BETWEEN :StartDate AND date_add(:EndDate, INTERVAL 1 Month)", nativeQuery = true)
    Page<UserReview> UserReSerch(Pageable pageable,@Param("search") String search,@Param("expoCate") int expoCate,@Param("StartDate") String StartDate, @Param("EndDate") String EndDate);

    //검색 했을때 리뷰 많은 순했을때
    @Query(value = "select *\n" +
            "from review_table\n" +
            "where expo_cate = :expoCate\n" +
            "and re_title like %:search%\n" +
            "order by re_cnt desc;\n" , nativeQuery = true)
    Page<UserReview> UserReHit(Pageable pageable,@Param("search") String search,@Param("expoCate") int expoCate);

    //그냥 조회수만
    @Query(value = "select *\n" +
            "from review_table\n" +
            "where expo_cate = :expoCate\n" +
            "order by re_cnt desc;\n" , nativeQuery = true)
    Page<UserReview> UserReHit2(Pageable pageable,@Param("expoCate") int expoCate);
}
