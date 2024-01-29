package com.example.JumpExpo.Service.admin;


import com.example.JumpExpo.Entity.comuser.Company;
import com.example.JumpExpo.Entity.comuser.ExpoAppCom;
import com.example.JumpExpo.Entity.user.Users;
import com.example.JumpExpo.Repository.comuser.CompanyRepository;
import com.example.JumpExpo.Repository.user.UserReository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


//2024-01-29 유저관리 페이징처리 서비스
@Service
public class AdminUserService {

    @Autowired
    UserReository userReository;

    @Autowired
    CompanyRepository companyRepository;

    public Page<Users> getuserList(int page, int expoCate){
        Pageable pageable = PageRequest.of(page,5);
        return this.userReository.userM(pageable,expoCate);

    }

    public Page<Company> getcomList(int page, int expoCate){
        Pageable pageable = PageRequest.of(page,5);
        return this.companyRepository.comM(pageable,expoCate);

    }
}
