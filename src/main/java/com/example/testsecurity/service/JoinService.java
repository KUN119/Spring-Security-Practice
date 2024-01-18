package com.example.testsecurity.service;

import com.example.testsecurity.dto.JoinDTO;
import com.example.testsecurity.entity.UserEntity;
import com.example.testsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //joinDTO에서 받은 데이터를 엔티티로 반환하고 DB에 보내줌
    public void joinProcess(JoinDTO joinDTO) {
        //DB에 접근할 Repository 인터페이스와 Repository가 들고갈 entity라는 바구니를 만들어야함

        //DB에 이미 동일한 username을 가진 회원이 존재하는지 검증하는 로직
        boolean isUser = userRepository.existsByUsername(joinDTO.getUsername());
        if (isUser){
            return; //username이 존재하면 메서드 종료
        }

        UserEntity data = new UserEntity();

        data.setUsername(joinDTO.getUsername());
        //data.setPassword(joinDTO.getPassword()); //비밀번호는 암호화해서 보내줘야함
        data.setPassword(bCryptPasswordEncoder.encode(joinDTO.getPassword())); //메서드를 통해 암호화 진행
        data.setRole("ROLE_ADMIN");

        userRepository.save(data);
    }
}
