package com.example.JumpExpo.Service.user.expo;

import com.example.JumpExpo.Entity.admin.ScheduleInsert;
import com.example.JumpExpo.Entity.comuser.ExpoAppCom;
import com.example.JumpExpo.Entity.etc.ComExpoApp;
import com.example.JumpExpo.Entity.user.UserReview;
import com.example.JumpExpo.Repository.admin.SchInsetExpoRepository;
import com.example.JumpExpo.Repository.comuser.ExpoAppComRepository;
import com.example.JumpExpo.Repository.etc.ComExpoAppRepository;
import com.example.JumpExpo.Repository.user.UserExpoApplyRepository;
import com.example.JumpExpo.Repository.user.UserReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpoService {

    @Autowired
    SchInsetExpoRepository schInsetExpoRepository;

    @Autowired
    ExpoAppComRepository expoAppComRepository;

    @Autowired
    UserReviewRepository userReviewRepository;

    @Autowired
    ComExpoAppRepository comExpoAppRepository;

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

    //2024.01.18 정정빈
    // 유저 박람회 신청 내역
    public Page<ScheduleInsert> getUserAppExpoList(int page,int userCode){
        Pageable pageable = PageRequest.of(page,2);
        return this.schInsetExpoRepository.UserAppExpoList(pageable,userCode);
    }

    //2024.01.18 정정빈
    // 유저 박람회 신청 내역 검색
    public Page<ScheduleInsert> getUserAppExpoListSearch(int page,int userCode,String search,String StartDate,String EndDate){
        Pageable pageable = PageRequest.of(page,2);
        return this.schInsetExpoRepository.UserAppExpoListSerch(pageable,userCode,search,StartDate,EndDate);
    }

    //유저 마이 리뷰
    public Page<UserReview> getUserReList(int page,int userCode){

        Pageable pageable = PageRequest.of(page,6);
        return this.userReviewRepository.UserReList(pageable,userCode);
    }

    //2024.01.23 정정빈
    //관리자 리뷰 리스트
    public Page<UserReview> getAdUserReviewList(int page, int expoCate) {
        Pageable pageable = PageRequest.of(page, 6); // 여기서 6은 페이지 크기
        return this.userReviewRepository.AdReviewList(pageable, expoCate);
    }

    //2024.01.24 정정빈
    // 리뷰 검색 했을때
    public Page<UserReview> getUserReSearchList(int page, String search,int expoCate,String StartDate,String EndDate){
        Pageable pageable = PageRequest.of(page,6);
        return this.userReviewRepository.UserReSerch(pageable,search,expoCate,StartDate,EndDate);
    }

    public Page<UserReview> getUserReHitList(int page,String search,int expoCate){
        Pageable pageable = PageRequest.of(page,6);
        return this.userReviewRepository.UserReHit(pageable,search,expoCate);
    }

    public Page<UserReview> getUserReHitList2(int page,int expoCate){
        Pageable pageable = PageRequest.of(page,6);
        return this.userReviewRepository.UserReHit2(pageable,expoCate);
    }

    //2024.01.27 정정빈
    //기업 박람회 신청 마이페이지 리스트
    public Page<ComExpoApp> getComEAppList(int page, int comCode){
        Pageable pageable = PageRequest.of(page,6);
        return this.comExpoAppRepository.getComExpoAppList(pageable,comCode);
    }

}
