package com.lq.service;

import com.lq.common.exception.UserPasswordNotMatchException;
import com.lq.domain.LoginUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Service
public class SysLoginService {
    @Resource
   private AuthenticationManager authenticationManager;

    @Resource
   private TokenService tokenService;

    public String login(String username, String password) {
        // 获取认证对象
        LoginUser loginUser = null;
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            loginUser = (LoginUser) authenticate.getPrincipal();
        } catch (BadCredentialsException e) {
            throw new UserPasswordNotMatchException();
        }
        String token = tokenService.createToken(loginUser);

        return token;
    }
}
