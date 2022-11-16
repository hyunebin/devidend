package com.hyunebin.dividend.Web.Service;


import com.hyunebin.dividend.Web.Persist.Entity.MemberEntity;
import com.hyunebin.dividend.Web.Persist.Model.Auth;
import com.hyunebin.dividend.Web.Repository.Member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service


public class MemberServiceImp implements MemberService, UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("유저를 찾을 수 없습니다."+ username));
    }

    public MemberEntity register(Auth.SignUp signUp){

        boolean exist = memberRepository.existsByUsername(signUp.getUsername());
        if(exist){
            throw new RuntimeException("이미 사용중인 아이디입니다.");
        }

        signUp.setPassword(passwordEncoder.encode(signUp.getPassword()));


        return memberRepository.save(signUp.of());
    }

    public MemberEntity authenticate(Auth.SignIn signIn){
        MemberEntity memberEntity = memberRepository.findByUsername(signIn.getUsername()).orElseThrow(() -> new RuntimeException("회원 정보가 존재하지 않습니다."));

        if(!passwordEncoder.matches(signIn.getPassword(), memberEntity.getPassword())){
            throw new RuntimeException("비빌번호가 매칭되지 않습니다.");
        }
        return memberEntity;
    }
}
