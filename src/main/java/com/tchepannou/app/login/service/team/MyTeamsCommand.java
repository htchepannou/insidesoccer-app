package com.tchepannou.app.login.service.team;

import com.tchepannou.app.login.client.v1.team.AppMyTeamsResponse;
import com.tchepannou.app.login.mapper.AppMyTeamsResponseMapper;
import com.tchepannou.app.login.service.CommandContext;
import com.tchepannou.app.login.service.PartyService;
import com.tchepannou.app.login.service.impl.AbstractSecuredCommand;
import com.tchepannou.party.client.v1.PartyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class MyTeamsCommand extends AbstractSecuredCommand<Void, AppMyTeamsResponse> {
    //-- Attributes
    @Autowired
    private PartyService partyService;

    //-- AbstractSecuredCommand overrides
    @Override
    protected AppMyTeamsResponse doExecute(Void request, CommandContext context) throws IOException {
        final List<PartyResponse> children = partyService.children(getUserId(), context).getParties();

        final Map<PartyResponse, List<PartyResponse>> teamsByChild = new HashMap<>();
        for (final PartyResponse child : children){
            teamsByChild.put(child, partyService.teamsByUser(child.getId(), context).getParties());
        }

        final List<PartyResponse> teams = partyService.teamsByUser(getUserId(), context).getParties();

        return new AppMyTeamsResponseMapper()
                .withTeams(teams)
                .withTeamsByChild(teamsByChild)
                .map();
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
