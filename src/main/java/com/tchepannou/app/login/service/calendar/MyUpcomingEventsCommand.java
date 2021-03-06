package com.tchepannou.app.login.service.calendar;

import com.tchepannou.app.login.service.CommandContext;
import com.tchepannou.party.client.v1.PartyResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class MyUpcomingEventsCommand extends AbstractUpcomingEventsCommand {
    @Override
    protected List<Long> getTeamIds (CommandContext context) throws IOException {
        return getPartyService().teamsByUser(getUserId(), context)
                .getParties()
                .stream()
                .map(PartyResponse::getId)
                .collect(Collectors.toList());
    }
}
