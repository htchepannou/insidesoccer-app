package com.tchepannou.app.login.service.impl;

import com.tchepannou.app.login.service.BlogService;
import com.tchepannou.app.login.service.CommandContext;
import com.tchepannou.blog.client.v1.PostCollectionResponse;
import com.tchepannou.blog.client.v1.SearchRequest;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

public class BlogServiceImpl extends AbstractHttpService implements BlogService {
    //-- Attributes
    public static final String PATH_PREFIX = "/v1/blog/";

    @Value("${blog.hostname}")
    private String hostname;

    @Value("${blog.port}")
    private int port;


    //-- BlogService overrides
    @Override
    public PostCollectionResponse search(SearchRequest request, CommandContext context) throws IOException {
        return createHttp(context)
                .withPath(PATH_PREFIX + "search")
                .withPayload(request)
                .post(PostCollectionResponse.class);
    }

    //-- AbstractHttpService overrides
    @Override protected String getHostname() {
        return hostname;
    }

    @Override protected int getPort() {
        return port;
    }
}
