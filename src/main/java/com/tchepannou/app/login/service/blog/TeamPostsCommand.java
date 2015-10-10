package com.tchepannou.app.login.service.blog;

import com.tchepannou.app.login.client.v1.blog.AppPostCollectionResponse;
import com.tchepannou.app.login.mapper.AppPostCollectionResponseMapper;
import com.tchepannou.app.login.service.BlogService;
import com.tchepannou.app.login.service.CommandContext;
import com.tchepannou.app.login.service.PartyService;
import com.tchepannou.app.login.service.impl.AbstractCommand;
import com.tchepannou.blog.client.v1.PostCollectionResponse;
import com.tchepannou.party.client.v1.PartyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class TeamPostsCommand extends AbstractCommand<Long, AppPostCollectionResponse> {
    //-- Attributes
    @Autowired
    private PartyService partyService;

    @Autowired
    private BlogService blogService;

    @Value("${insidesoccer.assset_url_prefix}")
    private String assetUrlPrefix;


    //-- AbstractCommand overrides
    @Override
    protected AppPostCollectionResponse doExecute(Long blogId, CommandContext context) throws IOException {
        PostCollectionResponse posts = blogService.published(Collections.singletonList(blogId), context);

        final Map<Long, PartyResponse> teams = getTeams(posts, context);

        return new AppPostCollectionResponseMapper()
                .withAssertUrlPrefix(assetUrlPrefix)
                .withPosts(posts.getPosts())
                .withTransactionInfo(getTransactionInfo())
                .withTeams(teams)
                .map();
    }


    //-- Private
    private Map<Long, PartyResponse> getTeams (PostCollectionResponse posts, final CommandContext context) throws IOException {
        final Set<Long> ids = posts.getPosts()
                .stream()
                .map(post -> post.getBlogId())
                .collect(Collectors.toSet());

        return partyService.teamsByIds(ids, context)
                .getParties()
                .stream()
                .collect(Collectors.toMap(p -> p.getId(), p -> p))
        ;

    }
}
