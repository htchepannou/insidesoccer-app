package com.tchepannou.app.login.service.impl;

import com.tchepannou.app.login.client.v1.login.AppLoginRequest;
import com.tchepannou.app.login.service.AuthService;
import com.tchepannou.app.login.service.CommandContext;
import com.tchepannou.auth.client.v1.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

public class AuthServiceImpl extends AbstractHttpService implements AuthService {
    //-- Attributes
    public static final String PATH_PREFIX = "/v1/auth/";

    @Value("${auth.hostname}")
    private String hostname;

    @Value("${auth.port}")
    private int port;


    //-- AuthService
    @Override
    public AccessTokenResponse login(AppLoginRequest request, CommandContext context) throws IOException {
        return createHttp(context)
                .withPath(PATH_PREFIX + "login")
                .withPayload(request)
                .post(AccessTokenResponse.class);
    }

    @Override
    public void logout(CommandContext context) throws IOException {
        createHttp(context)
                .withPath(PATH_PREFIX + "logout/")
                .post()
        ;
    }

    //-- AbstractHttpService overrides
    @Override protected String getHostname() {
        return hostname;
    }

    @Override protected int getPort() {
        return port;
    }
}
