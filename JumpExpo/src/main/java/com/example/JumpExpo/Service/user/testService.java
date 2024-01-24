package com.example.JumpExpo.Service.user;

import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;

@Controller
public class testService extends OidcUserService {

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) {
        OidcUser oidcUser = super.loadUser(userRequest);

        // 여기에서 필요한 추가 작업을 수행하거나 사용자 정보를 조작할 수 있습니다.

        return oidcUser;
    }
}
