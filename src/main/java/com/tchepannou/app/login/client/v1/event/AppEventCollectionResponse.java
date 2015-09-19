package com.tchepannou.app.login.client.v1.event;

import com.tchepannou.app.login.client.v1.BaseResponse;
import com.tchepannou.app.login.client.v1.TransactionInfo;
import com.tchepannou.event.client.v1.EventResponse;
import com.tchepannou.party.client.v1.PartyResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppEventCollectionResponse extends BaseResponse {
    private List<EventResponse> events = new ArrayList<>();
    private Map<Long, PartyResponse> teams = new HashMap<>();

    public AppEventCollectionResponse(TransactionInfo transactionInfo,
            List<EventResponse> events, Map<Long, PartyResponse> teams) {
        super(transactionInfo);
        this.events = events;
        this.teams = teams;
    }

    public List<EventResponse> getEvents() {
        return events;
    }

    public void setEvents(List<EventResponse> events) {
        this.events = events;
    }

    public Map<Long, PartyResponse> getTeams() {
        return teams;
    }

    public void setTeams(Map<Long, PartyResponse> teams) {
        this.teams = teams;
    }
}
