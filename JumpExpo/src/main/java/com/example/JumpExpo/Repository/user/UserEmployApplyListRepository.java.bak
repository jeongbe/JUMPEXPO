package com.example.JumpExpo.Repository.user;

import com.example.JumpExpo.Entity.etc.UserEmployApplyList;
import com.example.JumpExpo.Entity.user.PeremApplyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface UserEmployApplyListRepository extends JpaRepository<UserEmployApplyList,Integer> {

    @Query(value = "select p.pem_appnum, p.user_code, p.emnot_code, p.user_name, p.pem_can, a. emnot_title, a.emnot_start, a.emnot_end, a.com_code\n" +
            "from apply_employ a\n" +
            "join perem_apply_user p\n" +
            "on a.emnot_code = p.emnot_code\n" +
            "where p.user_code = :userCode", nativeQuery = true)
    ArrayList<UserEmployApplyList> applyList(@Param("userCode") int userCode);
}
