package com.tchepannou.app.login.service.calendar;

import com.tchepannou.app.login.service.CommandContext;
import com.tchepannou.event.client.v1.SearchRequest;

import java.io.IOException;

public abstract class AbstractUpcomingEventsCommand extends AbstractSearchEventCommand {
    protected SearchRequest createSearchRequest (final CommandContext context) throws IOException {
        final SearchRequest request = new SearchRequest();
        request.setCalendarIds(getTeamIds(context));
        request.setLimit(context.getLimit());
        request.setOffset(context.getOffset());

        next7Days(next7Days(request));

        return request;
    }
}
