package com.example.JumpExpo.Repository.user;

import com.example.JumpExpo.Entity.user.UserExpoApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserExpoApplyRepository extends JpaRepository<UserExpoApply, Integer> {

}
