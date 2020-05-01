package com.real.tv.service;

import com.real.tv.dto.TokenRequest;
import com.real.tv.dto.TokenResponse;
import com.real.tv.exception.AuthenticationException;
import com.real.tv.security.JwtTokenUtil;
import com.real.tv.security.UserPrincipalDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * Spring service to provide tokens
 */
@Service
public class TokenService {

    private static final String DISABLED_USER_MSG = "msg.disabled-user";

    private static final String BAD_CREDENTIALS_MSG = "msg.bad-credentials";

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserPrincipalDetailsService userDetailsService;

    public TokenResponse generateToken(TokenRequest tokenRequest){

        try{
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(tokenRequest.getUsername(), tokenRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }catch (DisabledException e) {
            throw new AuthenticationException(DISABLED_USER_MSG);
        } catch (BadCredentialsException e) {
            throw new AuthenticationException(BAD_CREDENTIALS_MSG);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(tokenRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return new TokenResponse(token);
    }
}
