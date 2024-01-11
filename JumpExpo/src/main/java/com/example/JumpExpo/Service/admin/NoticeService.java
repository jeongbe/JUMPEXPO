package com.example.JumpExpo.Service.admin;

import com.example.JumpExpo.DTO.admin.NoticeForm;
import com.example.JumpExpo.Entity.admin.Notice;
import com.example.JumpExpo.Repository.admin.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class NoticeService {
    @Autowired
    NoticeRepository noticeRepository;

    //2024-01-10 유수민
    //조회수 트랜잭션
    @Transactional
    public Notice selectNoticeDetail(Integer not_code){
        Notice data = noticeRepository.findById(not_code).get();
        data.updateNotCnt(data.getNot_cnt());
        return data;
    }
}

