package com.example.JumpExpo.Service.user.expo;

import com.example.JumpExpo.Entity.admin.ScheduleInsert;
import com.example.JumpExpo.Repository.admin.SchInsetExpoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ExpoService {

    @Autowired
    SchInsetExpoRepository schInsetExpoRepository;

    //2024.01.08 정정빈
    //박람회 전체 페이징 서비스
    public Page<ScheduleInsert> getAllList(int page){
        Pageable pageable = PageRequest.of(page,3);
        return this.schInsetExpoRepository.AllExpoList(pageable);
    }

    //2024.01.08 정정빈
    //전체일정 검색
    public Page<ScheduleInsert> getSerchList(int page,String serch){
        Pageable pageable = PageRequest.of(page,1);
        return this.schInsetExpoRepository.serch1(pageable,serch);
    }

    //행사 전체일정 1개월 버튼 페이징
    public Page<ScheduleInsert> get1Period(int page){
        Pageable pageable = PageRequest.of(page,1);
        return this.schInsetExpoRepository.period1(pageable);
    }
}
