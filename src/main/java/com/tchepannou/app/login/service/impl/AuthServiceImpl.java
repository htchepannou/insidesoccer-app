package com.tchepannou.app.login.service.impl;

import com.tchepannou.app.login.client.v1.login.AppLoginRequest;
import com.tchepannou.app.login.service.AuthService;
import com.tchepannou.app.login.service.CommandContext;
import com.tchepannou.auth.client.v1.AccessTokenResponse;
import com.tchepannou.core.http.Http;
import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;

public class AuthServiceImpl implements AuthService {
    //-- Attributes
    public static final String PATH_PREFIX = "/v1/auth/";

    @Value("${auth.hostname}")
    private String hostname;

    @Value("${auth.port}")
    private int port;

    @Autowired
    private HttpClient httpClient;

    @Autowired
    private Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder;


    //-- AuthService
    @Override
    public AccessTokenResponse login(AppLoginRequest request, CommandContext context) throws IOException {
        return createHttp(context)
                .withPath(PATH_PREFIX + "login")
                .withPayload(request)
                .post(AccessTokenResponse.class)
        ;
    }

    @Override
    public void logout(CommandContext context) throws IOException {
        createHttp(context)
                .withPath(PATH_PREFIX + "logout/")
                .post()
        ;
    }

    //-- Private
    private Http createHttp (final CommandContext context){
        return new Http()
                .withHttpClient(httpClient)
                .header(Http.HEADER_ACCESS_TOKEN, context.getAccessTokenId())
                .header(Http.HEADER_TRANSACTION_ID, context.getTransactionId())
                .withObjectMapper(jackson2ObjectMapperBuilder.build())
                .withPort(port)
                .withHost(hostname);
    }
}
