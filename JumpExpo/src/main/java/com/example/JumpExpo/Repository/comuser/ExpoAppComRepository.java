package com.example.JumpExpo.Repository.comuser;

import com.example.JumpExpo.Entity.comuser.ExpoAppCom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface ExpoAppComRepository extends JpaRepository<ExpoAppCom,Integer> {

    //2024.01.13 정정빈
    //참여 기업수
    @Query(value = "select count(*)\n" +
            "from c_expo_apply\n" +
            "where expo_code = :expoCode", nativeQuery = true)
    public int getComCount(@Param("expoCode") int expoCode);

    //2024.01.15 정정빈
    //참여 기업 리스트 (심사전)
    @Query(value = "select *\n" +
            "from c_expo_apply\n" +
            "where expo_code = :expoCode\n" +
            "and recog_check = 0;", nativeQuery = true)
    public Page<ExpoAppCom> getComList(Pageable pageable, @Param("expoCode") int expoCode);

    //2024.01.15 정정빈
    //참여 기업 리스트 (심사완료)
    @Query(value = "select *\n" +
            "from c_expo_apply\n" +
            "where expo_code = :expoCode\n" +
            "and recog_check = 1;", nativeQuery = true)
    public Page<ExpoAppCom> getComOkList(Pageable pageable, @Param("expoCode") int expoCode);

    //2024.01.20 정정빈
    // 기업 박람회 신청 여부확인
    @Query(value = "select *\n" +
            "from c_expo_apply\n" +
            "where com_code = :ComCode\n" +
            "and expo_code = :expoCode\n", nativeQuery = true)
    public ExpoAppCom ComAppCheck(@Param("expoCode") int expoCode, @Param("ComCode") int ComCode);
}
