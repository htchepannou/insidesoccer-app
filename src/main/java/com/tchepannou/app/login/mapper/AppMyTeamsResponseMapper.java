package com.tchepannou.app.login.mapper;

import com.tchepannou.app.login.client.v1.team.AppMyTeamsResponse;
import com.tchepannou.party.client.v1.PartyResponse;

import java.util.List;
import java.util.Map;

public class AppMyTeamsResponseMapper {
    //-- Attributes
    private List<PartyResponse> teams;
    private Map<PartyResponse, List<PartyResponse>> teamsByChild;

    //-- Public
    public AppMyTeamsResponse map (){
        AppMyTeamsResponse response = new AppMyTeamsResponse();

        teams.forEach(team -> response.addTeam(team));

        teamsByChild.keySet().forEach(child -> {
            response.addChild(child);
            teamsByChild.get(child).forEach(team -> response.link(child, team));
        });

        response.sortTeams();

        return response;
    }

    public AppMyTeamsResponseMapper withTeams (List<PartyResponse> teams){
        this.teams = teams;
        return this;
    }

    public AppMyTeamsResponseMapper withTeamsByChild (Map<PartyResponse, List<PartyResponse>> teamsByChild){
        this.teamsByChild = teamsByChild;
        return this;
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
