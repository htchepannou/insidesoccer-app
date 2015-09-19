package com.tchepannou.app.login.service.impl;

import com.tchepannou.app.login.service.AccessTokenService;
import com.tchepannou.app.login.service.CommandContext;
import com.tchepannou.auth.client.v1.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

public class AccessTokenServiceImpl extends AbstractHttpService implements AccessTokenService {
    //-- Attributes
    public static final String PATH_PREFIX = "/v1/access_token/";

    @Value("${auth.hostname}")
    private String hostname;

    @Value("${auth.port}")
    private int port;


    //-- AccessToken overrides
    @Override
    public AccessTokenResponse get(CommandContext context) throws IOException {
        return createHttp(context)
                .withPath(PATH_PREFIX + context.getAccessTokenId())
                .get(AccessTokenResponse.class);
    }

    //-- AbstractHttpService override

    @Override
    protected String getHostname() {
        return hostname;
    }

    @Override
    protected int getPort() {
        return port;
    }
}
