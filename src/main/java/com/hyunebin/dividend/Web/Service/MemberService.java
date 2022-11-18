package com.hyunebin.dividend.Web.Service;

import com.hyunebin.dividend.Web.Persist.Entity.MemberEntity;
import com.hyunebin.dividend.Web.Persist.Model.Auth;
import org.springframework.security.core.userdetails.UserDetails;

public interface MemberService {
    public MemberEntity register(Auth.SignUp signUp);
    public MemberEntity authenticate(Auth.SignIn signIn);

    public UserDetails loadUserByUsername(String username);
}
