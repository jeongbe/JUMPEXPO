package com.example.JumpExpo.Repository.user;

import com.example.JumpExpo.Entity.comuser.ApplyEmploy;
import com.example.JumpExpo.Entity.user.PeremApplyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

//2024.01.11 박은채 채용 공고 보기 Repository
//채용 공고 보기 Repository
public interface PeremApplyRepository extends JpaRepository<PeremApplyUser,Integer> {

    //2024.01.15 박은채
    // emnot_code와 user_code로 중복 체크
    @Query(value = "SELECT *\n" +
            "FROM perem_apply_user p\n" +
            "WHERE p.emnot_code = :emnotCode AND p.user_code = :userCode", nativeQuery = true)
    PeremApplyUser findByEmnotCodeAndUserCode(@Param("emnotCode") int emnotCode, @Param("userCode") int userCode);

    // emnot_code에 따른 지원자 수 카운트
    @Query(value = "SELECT COUNT(*)\n" +
            "FROM perem_apply_user\n" +
            "WHERE emnot_code = :emnotCode", nativeQuery = true)
    int countByEmnotCode(@Param("emnotCode") int emnotCode);


    //2024.01.24 박은채
    //emnot_code별 지원자 리스트
    @Query(value = "SELECT *\n" +
            "FROM perem_apply_user\n" +
            "WHERE emnot_code = :emnotCode", nativeQuery = true)
    ArrayList<PeremApplyUser> EmcodeUserList(@Param("emnotCode") int emnotCode);

    //2024.01.24 박은채
//    user_code별 채용 신청 리스트
//    @Query(value = "SELECT *\n" +
//            "FROM perem_apply_user\n" +
//            "WHERE user_code = :userCode", nativeQuery = true)
//    List<PeremApplyUser> UserApplyList(@Param("userCode") int userCode);

    @Query(value = "select P.pem_appnum, P.user_code, P.emnot_code, P.\n" +
            "from apply_employ A\n" +
            "inner join perem_apply_user P\n" +
            "on A.emnot_code = P.emnot_code\n" +
            "where P.user_code = :userCode", nativeQuery = true)
    ArrayList<PeremApplyUser> UserApplyList(@Param("userCode") int userCode);

}
