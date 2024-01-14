package com.example.JumpExpo.Repository.comuser;

import com.example.JumpExpo.Entity.comuser.ExpoAppCom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpoAppComRepository extends JpaRepository<ExpoAppCom,Integer> {

    //2024.01.13 정정빈
    //참여 기업수
    @Query(value = "select count(*)\n" +
            "from c_expo_apply\n" +
            "where expo_code = :expoCode", nativeQuery = true)
    public int getComCount(@Param("expoCode") int expoCode);
}
