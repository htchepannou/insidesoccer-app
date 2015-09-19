package com.tchepannou.app.login.service.blog;

import com.tchepannou.app.login.client.v1.blog.AppPostCollectionResponse;
import com.tchepannou.app.login.mapper.AppPostCollectionResponseMapper;
import com.tchepannou.app.login.service.BlogService;
import com.tchepannou.app.login.service.CommandContext;
import com.tchepannou.app.login.service.PartyService;
import com.tchepannou.app.login.service.impl.AbstractSecuredCommand;
import com.tchepannou.blog.client.v1.PostResponse;
import com.tchepannou.blog.client.v1.SearchRequest;
import com.tchepannou.party.client.v1.PartyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractSearchPostCommand extends AbstractSecuredCommand<Void, AppPostCollectionResponse> {
    //-- Attributes
    @Autowired
    private PartyService partyService;

    @Autowired
    private BlogService blogService;

    @Value("${insidesoccer.assset_url_prefix}")
    private String assetUrlPrefix;


    //-- AbstractSecuredCommand overrides
    @Override
    protected AppPostCollectionResponse doExecute(Void req, CommandContext context) throws IOException {
        final SearchRequest request = createSearchRequest(context);

        final List<PostResponse> posts = blogService.search(request, context).getPosts();

        final Map<Long, PartyResponse> teams = getTeams(posts, context);

        return new AppPostCollectionResponseMapper()
                .withAssertUrlPrefix(assetUrlPrefix)
                .withPosts(posts)
                .withTransactionInfo(getTransactionInfo())
                .withTeams(teams)
                .map();
    }

    //-- Protected
    protected SearchRequest createSearchRequest (final CommandContext context) throws IOException {
        final SearchRequest request = new SearchRequest();
        request.setStatus("published");
        request.setBlogIds(getTeamIds(context));
        request.setLimit(context.getLimit());
        request.setOffset(context.getOffset());

        return request;
    }

    protected abstract List<Long> getTeamIds (final CommandContext context) throws IOException;


    //-- Getter
    public PartyService getPartyService() {
        return partyService;
    }

    //-- Private
    private Map<Long, PartyResponse> getTeams (final List<PostResponse> posts, final CommandContext context) throws IOException {
        final Set<Long> ids = posts.stream()
                .map(post -> post.getBlogId())
                .collect(Collectors.toSet());

        return getPartyService().teamsByIds(ids, context)
                .getParties()
                .stream()
                .collect(Collectors.toMap(p -> p.getId(), p -> p))
        ;

    }
}
