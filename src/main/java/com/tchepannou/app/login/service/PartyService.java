package com.tchepannou.app.login.service;

import com.tchepannou.party.client.v1.PartyCollectionResponse;
import com.tchepannou.party.client.v1.PartyResponse;

import java.io.IOException;
import java.util.Collection;

public interface PartyService {
    PartyResponse get(long id, CommandContext context) throws IOException;
    PartyCollectionResponse children(long userId, CommandContext context) throws IOException;
    PartyCollectionResponse teamsByUser(long userId, CommandContext context) throws IOException;
    PartyCollectionResponse teamsByIds(Collection<Long> teamIds, CommandContext context) throws IOException;
}
