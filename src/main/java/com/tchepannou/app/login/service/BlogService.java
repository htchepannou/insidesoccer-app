package com.tchepannou.app.login.service;

import com.tchepannou.blog.client.v1.PostCollectionResponse;
import com.tchepannou.blog.client.v1.SearchRequest;

import java.io.IOException;

public interface BlogService {
    PostCollectionResponse search(SearchRequest request, CommandContext context) throws IOException;
}
