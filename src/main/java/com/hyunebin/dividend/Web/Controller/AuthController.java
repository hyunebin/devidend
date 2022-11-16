package com.hyunebin.dividend.Web.Controller;

import com.hyunebin.dividend.Web.Persist.Entity.MemberEntity;
import com.hyunebin.dividend.Web.Persist.Model.Auth;
import com.hyunebin.dividend.Web.Repository.Member.MemberRepository;
import com.hyunebin.dividend.Web.Security.TokenProvider;
import com.hyunebin.dividend.Web.Service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor

public class AuthController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Auth.SignUp request){
        var result = memberService.register(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody Auth.SignIn request){
        //패스워드 인증
        var member = memberService.authenticate(request);

        var token = tokenProvider.generateToken(member.getUsername(), member.getRoles());

        return ResponseEntity.ok(token);
    }
}
