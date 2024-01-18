package com.example.testsecurity.controller;

import com.example.testsecurity.dto.JoinDTO;
import com.example.testsecurity.service.JoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class JoinController {

    // 지금은 필드 주입 방식으로 의존성을 주입 받았지만
    // 나중에는 생성자 주입 방식으로 의존성을 주입 받아야함
    @Autowired
    private JoinService joinService;

    @GetMapping("/join")
    public String joinP() {
        return "join";
    }

    @PostMapping("/joinProc")
    public String joinProcess(JoinDTO joinDTO) {

        System.out.println(joinDTO.getUsername());
        System.out.println(joinDTO.getPassword());

        joinService.joinProcess(joinDTO);

        return "redirect:/login";
    }
}
