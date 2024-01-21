package com.example.JumpExpo.Repository.user;

import com.example.JumpExpo.Entity.admin.ScheduleInsert;
import com.example.JumpExpo.Entity.user.UserExpoApply;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserExpoApplyRepository extends JpaRepository<UserExpoApply, Integer> {

    //유저가 박람회 신쳥했는데 안했는지 확인
    @Query(value = "select *\n" +
            "from user_expo_apply\n" +
            "where expo_code = :ExpoCode\n" +
            "and user_code = :UserCode", nativeQuery = true)
    public UserExpoApply UserAppCheck(@Param("ExpoCode") int ExpoCode,@Param("UserCode") int UserCode);


    //유저 박람회 취ㅏ소
    @Modifying
    @Transactional
    @Query(value = "delete\n" +
            "from user_expo_apply\n" +
            "where expo_code = :expoCode\n" +
            "and user_code = :userCode", nativeQuery = true)
    void UserCancelExpo(@Param("expoCode") int expoCode, @Param("userCode") int userCode);
}
