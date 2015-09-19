package com.tchepannou.app.login.service.impl;

import com.tchepannou.app.login.exception.AuthenticationException;
import com.tchepannou.app.login.service.AccessTokenService;
import com.tchepannou.app.login.service.CommandContext;
import com.tchepannou.auth.client.v1.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public abstract class AbstractSecuredCommand<I, O> extends AbstractCommand<I, O> {
    //-- Attributes
    @Autowired
    private AccessTokenService accessTokenService;

    private AccessTokenResponse accessToken;

    //-- AbstractCommand overrides
    @Override
    protected void authenticate(CommandContext context) {
        try {
            this.accessToken = accessTokenService.get(context);
        } catch (IOException e) {
            throw new AuthenticationException("Unable to resolve accessToken", e);
        }
    }

    //-- Protected
    public long getUserId ()  {
        return getAccessToken ().getUserId ();
    }

    public AccessTokenResponse getAccessToken() {
        return accessToken;
    }
}
