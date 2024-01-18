package com.example.testsecurity.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.Iterator;

//커스텀 컨피그를 설정하지 않으면 모든 페이지에 스프링 시큐리티가 적용됨
@Controller
public class MainController {

    @GetMapping("/")
    public String mainP(Model model) {

        //세션 로그인한 id 값
        String id = SecurityContextHolder.getContext().getAuthentication().getName();

        //세션 로그인한 role 값
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();

        //model 인터페이스에 넣어줌
        model.addAttribute("id", id);
        model.addAttribute("role", role);
        return "main";
    }
}
