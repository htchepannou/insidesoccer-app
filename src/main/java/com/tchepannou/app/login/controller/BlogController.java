package com.tchepannou.app.login.controller;

import com.tchepannou.app.login.client.v1.blog.AppPostCollectionResponse;
import com.tchepannou.app.login.service.blog.MyPostsCommand;
import com.tchepannou.app.login.service.blog.TeamPostsCommand;
import com.tchepannou.core.http.Http;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Api(basePath = "/v1/app/blog", value = "Blog", produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(value="/v1/app/blog", produces = MediaType.APPLICATION_JSON_VALUE)
public class BlogController extends AbstractController {
    @Autowired
    private TeamPostsCommand teamPostsCommand;

    @Autowired
    private MyPostsCommand myPostsCommand;

    //-- REST methods
    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation("Returns a user's posts")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Login required")
    })
    public AppPostCollectionResponse me(
            @RequestHeader(value= Http.HEADER_ACCESS_TOKEN) String accessToken,
            @RequestParam(defaultValue = "20") int limit,
            @RequestHeader(defaultValue = "0") int offset
    ) throws IOException {
        return myPostsCommand.execute(null,
                new CommandContextImpl()
                        .withAccessTokenId(accessToken)
                        .withLimit(limit)
                        .withOffset(offset)
        );
    }

    @RequestMapping(method = RequestMethod.GET, value="/{bid}")
    @ApiOperation("Returns a team's posts")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success")
    })
    public AppPostCollectionResponse team(
            @RequestHeader(value= Http.HEADER_ACCESS_TOKEN) String accessToken,
            @PathVariable long bid,
            @RequestParam(defaultValue = "20") int limit,
            @RequestHeader(defaultValue = "0") int offset
    ) throws IOException {
        return teamPostsCommand.execute(bid,
                new CommandContextImpl()
                        .withAccessTokenId(accessToken)
                        .withLimit(limit)
                        .withOffset(offset)
        );
    }
}
