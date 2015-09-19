package com.tchepannou.app.login.service.impl;

import com.tchepannou.app.login.service.CommandContext;
import com.tchepannou.app.login.service.PartyService;
import com.tchepannou.party.client.v1.PartyCollectionRequest;
import com.tchepannou.party.client.v1.PartyCollectionResponse;
import com.tchepannou.party.client.v1.PartyResponse;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class PartyServiceImpl extends AbstractHttpService implements PartyService{
    //-- Attributes
    public static final String PATH_PREFIX = "/v1/party/";
    
    @Value("${party.hostname}")
    private String hostname;

    @Value("${party.port}")
    private int port;


    //-- PartyService overrides
    @Override
    public PartyResponse get(long id, CommandContext context) throws IOException {
        return createHttp(context)
                .withPath(PATH_PREFIX + id)
                .get(PartyResponse.class);
    }

    @Override
    public PartyCollectionResponse children(long userId, CommandContext context) throws IOException {
        return createHttp(context)
                .withPath(PATH_PREFIX + "to/" + userId + "/relation/child")
                .get(PartyCollectionResponse.class)
        ;
    }

    @Override
    public PartyCollectionResponse teamsByUser(final long userId, final CommandContext context) throws IOException {
        return createHttp(context)
                .withPath(PATH_PREFIX + "from/" + userId + "/relation/member")
                .get(PartyCollectionResponse.class)
        ;
    }

    @Override
    public PartyCollectionResponse teamsByIds(Collection<Long> teamIds, final CommandContext context) throws IOException {
        PartyCollectionRequest request = new PartyCollectionRequest();
        request.setIds(new ArrayList<>(teamIds));
        return createHttp(context)
                .withPath(PATH_PREFIX + "list")
                .withPayload(request)
                .post(PartyCollectionResponse.class)
        ;
    }


    //-- AbstractHttpService overrides
    @Override protected String getHostname() {
        return hostname;
    }

    @Override protected int getPort() {
        return port;
    }

}
