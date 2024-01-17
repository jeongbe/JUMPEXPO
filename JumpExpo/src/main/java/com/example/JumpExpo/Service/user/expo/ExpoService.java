package com.example.JumpExpo.Service.user.expo;

import com.example.JumpExpo.Entity.admin.ScheduleInsert;
import com.example.JumpExpo.Entity.comuser.ExpoAppCom;
import com.example.JumpExpo.Repository.admin.SchInsetExpoRepository;
import com.example.JumpExpo.Repository.comuser.ExpoAppComRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ExpoService {

    @Autowired
    SchInsetExpoRepository schInsetExpoRepository;

    @Autowired
    ExpoAppComRepository expoAppComRepository;

    //2024.01.08 정정빈
    //박람회 전체 페이징 서비스
    public Page<ScheduleInsert> getAllList(int page){
        Pageable pageable = PageRequest.of(page,2);
        return this.schInsetExpoRepository.AllExpoList(pageable);
    }

    //2024.01.08 정정빈
    //전체일정 검색 단어 있을때
    public Page<ScheduleInsert> getSerchList(int page,String serch,String StartDate,String EndDate){
        Pageable pageable = PageRequest.of(page,2);
        return this.schInsetExpoRepository.serch1(pageable,serch,StartDate,EndDate);
    }

    //2024.01.11 정정빈
    //단어 없을때
    public Page<ScheduleInsert> getSerchList2(int page,String StartDate,String EndDate){
        Pageable pageable = PageRequest.of(page,2);
        return this.schInsetExpoRepository.serch2(pageable,StartDate,EndDate);
    }

    //행사 전체일정 1개월 버튼 페이징
    public Page<ScheduleInsert> get1Period(int page){
        Pageable pageable = PageRequest.of(page,2);
        return this.schInsetExpoRepository.period1(pageable);
    }

    //2024.01.12 정정빈
    //박람회 심사 채용 리스트
    public Page<ScheduleInsert> getRecList(int page){
        Pageable pageable = PageRequest.of(page,2);
        return this.schInsetExpoRepository.RecList(pageable);
    }

    //2024.01.15 정정빈
    //페어 박람회 심사 리스트
    public Page<ScheduleInsert> getFairList(int page){
        Pageable pageable = PageRequest.of(page,2);
        return this.schInsetExpoRepository.FairList(pageable);
    }

    //2024.01.15 정정빈
    //취업 박람회 심사 리스트
    public Page<ScheduleInsert> getEmpList(int page){
        Pageable pageable = PageRequest.of(page,2);
        return this.schInsetExpoRepository.EmpList(pageable);
    }



    //2024.01.15 정정빈
    //박람회 신청 기업 리스트
    public Page<ExpoAppCom> getComList(int page, int expoCode){
        Pageable pageable = PageRequest.of(page,2);
        return this.expoAppComRepository.getComList(pageable,expoCode);
    }

    //2024.01.15 정정빈
    //박람회 신청 기업 리스트 (심사 후)
    public Page<ExpoAppCom> getComOKList(int page, int expoCode){
        Pageable pageable = PageRequest.of(page,2);
        return this.expoAppComRepository.getComOkList(pageable,expoCode);
    }

    //2024.01.17 정정빈
    //박람회(유저) 페어 리스트
    public Page<ScheduleInsert> getUserFairList(int page){
        Pageable pageable = PageRequest.of(page,2);
        return this.schInsetExpoRepository.FairAllList(pageable);
    }

    //2024.01.17 정정빈
    //박람회(유저) 취업 리스트
    public Page<ScheduleInsert> getUserEmpList(int page){
        Pageable pageable = PageRequest.of(page,2);
        return this.schInsetExpoRepository.EmpAllList(pageable);
    }

    //2024.01.17 정정빈
    //박람회(유저) 채용 리스트
    public Page<ScheduleInsert> getUserRecList(int page){
        Pageable pageable = PageRequest.of(page,2);
        return this.schInsetExpoRepository.RecAllList(pageable);
    }

    //2024.01.17 정정빈
    //박람회(유저) 페어 검색 단어 있을때
    public Page<ScheduleInsert> getSerchFairList(int page,String serch,String StartDate,String EndDate){
        Pageable pageable = PageRequest.of(page,2);
        return this.schInsetExpoRepository.serchFair(pageable,serch,StartDate,EndDate);
    }

    //2024.01.17 정정빈
    //박람회(유저) 취업 검색 단어 있을때
    public Page<ScheduleInsert> getSerchEmpList(int page,String serch,String StartDate,String EndDate){
        Pageable pageable = PageRequest.of(page,2);
        return this.schInsetExpoRepository.serchEmp(pageable,serch,StartDate,EndDate);
    }

    //2024.01.17 정정빈
    //박람회(유저) 채용 검색 단어 있을때
    public Page<ScheduleInsert> getSerchRecList(int page,String serch,String StartDate,String EndDate){
        Pageable pageable = PageRequest.of(page,2);
        return this.schInsetExpoRepository.serchRec(pageable,serch,StartDate,EndDate);
    }
}
