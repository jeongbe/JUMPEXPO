package com.example.JumpExpo.Controller.join;


import com.example.JumpExpo.Config.UserSecurityService;
import com.example.JumpExpo.DTO.user.UserForm;
import com.example.JumpExpo.Entity.user.Users;
import com.example.JumpExpo.Repository.user.UserReository;
import com.example.JumpExpo.domain.Role;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import jdk.jfr.Description;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

//카카오 로그인 2024-01-15 맹성우
@Slf4j
@Controller
@Service
public class KakaoController{

    @Autowired
    UserReository userReository;
    @Autowired
    Users kakaojoin;
    @Autowired
    UserSecurityService userSecurityService;

    @GetMapping("/JumpExpo/kakaologin")
    public String kakaologin(@RequestParam("code") String code) throws JsonProcessingException {

        //post 방식으로  key = value 데이터를 요청
        RestTemplate rt = new RestTemplate();

        //httpheader 오브젝트
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");


        //httpbody 오브젝트
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("grant_type","authorization_code");
        params.add("client_id","bcf283702c88974c445ca041bb3d6ad6");
        params.add("redirect_uri","http://localhost:8080/JumpExpo/kakaologin");
        params.add("client_secret","Mjlzvv3JOqrjnJ3IVkWsqDDNNlddmPuO");
        params.add("code",code);


        //하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String,String>> kakaoRequest = new HttpEntity<>(params,headers);

        //http 요청하기 - post 방식으로 - 그리고 response 변수의 응답 받음.
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token", //토근 발급 요청주소
                HttpMethod.POST, // 요청 메서드
                kakaoRequest,  //데이터
                String.class   //응답 받을 타입
        );

        // 응답 내용 출력
        String responseBody = response.getBody();
        System.out.println("response = " + responseBody);

        ObjectMapper objectMapper = new ObjectMapper();

        OAuthToken oAuthToken = objectMapper.readValue(responseBody,OAuthToken.class);

        System.out.println("카카오 엑세스토큰" + oAuthToken.getAccess_token());

        //post 방식으로  key = value 데이터를 요청
        RestTemplate rt2 = new RestTemplate();

        //httpheader 오브젝트
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
        headers2.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");


        //httpbody 오브젝트
        MultiValueMap<String,String> params2 = new LinkedMultiValueMap<>();
        params.add("grant_type","authorization_code");
        params.add("client_id","bcf283702c88974c445ca041bb3d6ad6");
        params.add("redirect_uri","http://localhost:8080/JumpExpo/kakaologin");
        params.add("client_secret","Mjlzvv3JOqrjnJ3IVkWsqDDNNlddmPuO");
        params.add("code",code);


        //하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String,String>> kakaoRequest2 = new HttpEntity<>(params2,headers2);

        //http 요청하기 - post 방식으로 - 그리고 response 변수의 응답 받음.
        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me", //토근 발급 요청주소
                HttpMethod.GET, // 요청 메서드
                kakaoRequest2,  //데이터
                String.class   //응답 받을 타입
        );

        // Kakao API로부터 받은 JSON 응답
        String responseBody2 = response2.getBody();

        // ObjectMapper를 사용하여 JSON을 Java 객체로 변환
        ObjectMapper objectMapper2 = new ObjectMapper();
        JsonNode jsonNode = objectMapper2.readTree(responseBody2);

        // properties 정보 추출
        JsonNode propertiesNode = jsonNode.get("properties");
        String nickname = propertiesNode.get("nickname").asText();

        // kakao_account 정보 추출
        JsonNode kakaoAccountNode = jsonNode.get("kakao_account");
        String email = kakaoAccountNode.get("email").asText();

        // 추출한 정보 사용
        System.out.println("닉네임: " + nickname);
        System.out.println("이메일: " + email);

        //이미 가입되어있는 정보인지 확인함
        Users users = userReository.kakao(email,nickname);
        //이미 가입되어있다면 바로 메인페이지로 반환
        if (users != null){

            //시큐리티 로그인 매서드 호출
            userSecurityService.loadUserByUsername(email);

            return "redirect:/users/Main";

        }
        //아니라면 정보를 저장하면서 메인페이지
        else {

            //정보 넣어주기
            kakaojoin.setUser_id(email);
            kakaojoin.setOauth2(oAuthToken.getAccess_token());
            kakaojoin.setUser_pw(oAuthToken.getAccess_token());
            kakaojoin.setUser_name(nickname);
            kakaojoin.setUser_email(email);
            kakaojoin.setUser_sec(1);
            kakaojoin.setRole(Role.USER);

            //저장하기
            userReository.save(kakaojoin);

            //시큐리티 로그인 매서드 호출
            userSecurityService.loadUserByUsername(email);


            return "redirect:/users/Main";

        }


    }


}
