package com.example.JumpExpo.Repository.comuser;

import com.example.JumpExpo.Entity.comuser.ComInterview;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComInterviewRepository extends JpaRepository<ComInterview, Integer> {

    @Query(value = "select *\n" +
            "from com_interview\n" +
            "where com_code = :comCode", nativeQuery = true)
    public List<ComInterview> getComIntervList(@Param("comCode") int comCode);
}
