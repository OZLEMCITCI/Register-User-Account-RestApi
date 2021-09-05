package com.springjwt1.security.JWT;

import com.springjwt1.service.UserServiceUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class JWTAuthFiliter extends OncePerRequestFilter {
    @Autowired
    JWTUtils jwtUtils;

    @Autowired
    UserServiceUserDetails userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            // Rquest içerisindeki Header'a git ve Authorization kismindaki token' ayıkla.
            String jwt = jwtAl(request);
            // Token'ı geçerle.

            if( jwt != null && jwtUtils.JwtTokenGecerle(jwt)) {
                String username = jwtUtils.usernameAl(jwt);
                // İstekte bulunan kisinin bilgilerini Service layer'dan getir.
                UserDetails userDetailes = userService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetailes, null, userDetailes.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //Kimlik denetim bilgilerini tutan Security Context'in güncellenmesi
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }catch(Exception e){
            System.out.println("Kimlik denetimi gerçekleştirilemedi." + e.getMessage());
        }

        filterChain.doFilter(request,response);
    }


    public String jwtAl(HttpServletRequest request){
        String headerAuth = request.getHeader("Authorization");
        if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }
        return null;
    }
}

