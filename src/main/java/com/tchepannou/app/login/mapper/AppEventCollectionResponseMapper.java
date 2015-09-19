package com.tchepannou.app.login.mapper;

import com.tchepannou.app.login.client.v1.TransactionInfo;
import com.tchepannou.app.login.client.v1.event.AppEventCollectionResponse;
import com.tchepannou.event.client.v1.EventResponse;
import com.tchepannou.party.client.v1.PartyResponse;

import java.util.List;
import java.util.Map;

public class AppEventCollectionResponseMapper {
    //-- Attribute
    private List<EventResponse> events;
    private Map<Long, PartyResponse> teams;
    private TransactionInfo transactionInfo;

    //-- Public
    public AppEventCollectionResponse map (){
        AppEventCollectionResponse result = new AppEventCollectionResponse (transactionInfo, events, teams);
        return result;
    }

    public AppEventCollectionResponseMapper withTransactionInfo (TransactionInfo transactionInfo){
        this.transactionInfo = transactionInfo;
        return this;
    }
    public AppEventCollectionResponseMapper withEvents(List<EventResponse> events) {
        this.events = events;
        return this;
    }

    public AppEventCollectionResponseMapper withTeams(Map<Long, PartyResponse> teams) {
        this.teams = teams;
        return this;
    }
}
