package com.tchepannou.app.login.service.impl;

import com.tchepannou.app.login.service.CommandContext;
import com.tchepannou.core.http.Http;
import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

public abstract class AbstractHttpService {
    //-- Attributes
    @Autowired
    private HttpClient httpClient;

    @Autowired
    private Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder;


    //-- Abstract
    protected abstract String getHostname ();

    protected abstract int getPort ();

    protected final Http createHttp (final CommandContext context){
        return new Http()
                .withHttpClient(httpClient)
                .header(Http.HEADER_ACCESS_TOKEN, context.getAccessTokenId())
                .header(Http.HEADER_TRANSACTION_ID, context.getTransactionId())
                .withObjectMapper(jackson2ObjectMapperBuilder.build())
                .withPort(getPort())
                .withHost(getHostname());
    }

}
