package com.hyunebin.dividend.Web.Service;

import com.hyunebin.dividend.Web.Persist.Entity.MemberEntity;
import com.hyunebin.dividend.Web.Persist.Model.Auth;

public interface MemberService {
    public MemberEntity register(Auth.SignUp signUp);
    public MemberEntity authenticate(Auth.SignIn signIn);
}
