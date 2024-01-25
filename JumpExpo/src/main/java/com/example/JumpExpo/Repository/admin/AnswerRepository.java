package com.example.JumpExpo.Repository.admin;

import com.example.JumpExpo.Entity.admin.Answer;
import com.example.JumpExpo.Entity.user.QnA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    @Override
    ArrayList<Answer> findAll();
}
