package com.tchepannou.app.login.service.impl;

import com.google.common.base.Joiner;
import com.tchepannou.app.login.service.BlogService;
import com.tchepannou.app.login.service.CommandContext;
import com.tchepannou.blog.client.v1.PostCollectionResponse;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.List;

public class BlogServiceImpl extends AbstractHttpService implements BlogService {
    //-- Attributes
    public static final String PATH_PREFIX = "/v1/blog/";

    @Value("${blog.hostname}")
    private String hostname;

    @Value("${blog.port}")
    private int port;


    //-- BlogService overrides
    @Override
    public PostCollectionResponse published (List<Long> blogIds, CommandContext context) throws IOException {
        String bid = Joiner.on(",").join(blogIds);
        return createHttp(context)
                .withPath(PATH_PREFIX + bid + "/published")
                .get(PostCollectionResponse.class);
    }

    //-- AbstractHttpService overrides
    @Override protected String getHostname() {
        return hostname;
    }

    @Override protected int getPort() {
        return port;
    }
}
