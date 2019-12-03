package com.softmetrix.configuration;

import com.softmetrix.model.Role;
import java.io.IOException;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;

public class AuthenticationSuccessHandler implements org.springframework.security.web.authentication.AuthenticationSuccessHandler{
    
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest hsr, HttpServletResponse hsr1, Authentication a) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = a.getAuthorities();
        
        String targetUrl = "/";
        
        for(GrantedAuthority grantedAuthority: authorities){
            if(grantedAuthority.getAuthority().equals(Role.ADMINISTRATOR)){
                targetUrl = "/administrator";
                break;
            }
            if(grantedAuthority.getAuthority().equals(Role.PRINCIPAL)){
                targetUrl = "/principal";
                break;
            }
        }

        redirectStrategy.sendRedirect(hsr, hsr1, targetUrl);
    }
}
