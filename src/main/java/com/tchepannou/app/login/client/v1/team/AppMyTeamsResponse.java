package com.tchepannou.app.login.client.v1.team;

import com.tchepannou.app.login.client.v1.BaseResponse;
import com.tchepannou.party.client.v1.PartyResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AppMyTeamsResponse extends BaseResponse{
    private List<PartyResponse> teams = new ArrayList<>();
    private Map<Long, PartyResponse> children = new HashMap<>();
    private Map<Long, Set<Long>> childrenByTeam = new HashMap<>();

    public void addTeam (PartyResponse team){
        teams.add(team);
    }

    public void addChild (PartyResponse child){
        children.put(child.getId(), child);
    }

    public void link(PartyResponse child, PartyResponse team){
        final long childId = child.getId();
        final long teamId = team.getId();

        Set<Long> children = childrenByTeam.get(teamId);
        if (children == null){
            children = new HashSet<>();
            childrenByTeam.put(teamId, children);
        }
        children.add(childId);
    }

    public void sortTeams (){
        Collections.sort(teams, (t1, t2) -> weight(t2) - weight(t1));
    }

    private int weight (PartyResponse team){
        Set<Long> children = childrenByTeam.get(team.getId());
        return children == null ? 0 : children.size();
    }

    //-- Getters
    public List<PartyResponse> getTeams() {
        return teams;
    }

    public Map<Long, Set<Long>> getChildrenByTeam() {
        return childrenByTeam;
    }

    public Map<Long, PartyResponse> getChildren() {
        return children;
    }

}
