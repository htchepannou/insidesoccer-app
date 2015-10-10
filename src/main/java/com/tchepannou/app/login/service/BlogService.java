package com.tchepannou.app.login.service;

import com.tchepannou.blog.client.v1.PostCollectionResponse;

import java.io.IOException;
import java.util.List;

public interface BlogService {
    PostCollectionResponse published (List<Long> blogIds, CommandContext context) throws IOException;
}
