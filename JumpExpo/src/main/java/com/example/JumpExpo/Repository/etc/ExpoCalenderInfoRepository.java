package com.example.JumpExpo.Repository.etc;

import com.example.JumpExpo.Entity.etc.ExpoCalenderInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpoCalenderInfoRepository extends JpaRepository<ExpoCalenderInfo,Integer> {

    @Query(value = "select * \n" +
            "from schedule_insert", nativeQuery = true)
    List<ExpoCalenderInfo> getInfo();
}
