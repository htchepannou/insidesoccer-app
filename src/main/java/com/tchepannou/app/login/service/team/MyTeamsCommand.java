package com.tchepannou.app.login.service.team;

import com.tchepannou.app.login.client.v1.Constants;
import com.tchepannou.app.login.client.v1.team.AppMyTeamsResponse;
import com.tchepannou.app.login.mapper.AppMyTeamsResponseMapper;
import com.tchepannou.app.login.service.CommandContext;
import com.tchepannou.app.login.service.impl.AbstractSecuredCommand;
import com.tchepannou.party.client.v1.PartyCollectionResponse;
import com.tchepannou.party.client.v1.PartyResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class MyTeamsCommand extends AbstractSecuredCommand<Void, AppMyTeamsResponse> {
    //-- Attributes
    @Value("${party.hostname}")
    private String partyHostname;

    @Value("${party.port}")
    private int partyPort;

    //-- AbstractSecuredCommand overrides
    @Override
    protected AppMyTeamsResponse doExecute(Void request, CommandContext context) throws IOException {
        final List<PartyResponse> children = getChildren();

        final Map<PartyResponse, List<PartyResponse>> teamsByChild = new HashMap<>();
        for (final PartyResponse child : children){
            teamsByChild.put(child, getTeams(child.getId()).getParties());
        }

        final List<PartyResponse> teams = getTeams(getUserId()).getParties();

        return new AppMyTeamsResponseMapper()
                .withTeams(teams)
                .withTeamsByChild(teamsByChild)
                .map();
    }

    private List<PartyResponse> getChildren () throws IOException {
        return getHttp()
                .withPort(partyPort)
                .withHost(partyHostname)
                .withPath(Constants.URI_PARTY + "/to/" + getUserId() + "/relation/child")
                .get(PartyCollectionResponse.class)
                .getParties()
        ;
    }

    private PartyCollectionResponse getTeams (long userId) throws IOException {
        return getHttp()
                .withPort(partyPort)
                .withHost(partyHostname)
                .withPath(Constants.URI_PARTY + "/from/" + userId + "/relation/member")
                .get(PartyCollectionResponse.class)
        ;
    }

    private int occurence(final PartyResponse team, final Map<PartyResponse, List<PartyResponse>> teamsByChildren){
        int value = 0;
        for (final List<PartyResponse> teams : teamsByChildren.values()){
            if (teams.contains(team)){
                value++;
            }
        }
        return value;
    }
}
