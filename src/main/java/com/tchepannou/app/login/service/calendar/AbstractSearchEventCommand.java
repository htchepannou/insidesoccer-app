package com.tchepannou.app.login.service.calendar;

import com.tchepannou.app.login.client.v1.event.AppEventCollectionResponse;
import com.tchepannou.app.login.mapper.AppEventCollectionResponseMapper;
import com.tchepannou.app.login.service.CalendarService;
import com.tchepannou.app.login.service.CommandContext;
import com.tchepannou.app.login.service.PartyService;
import com.tchepannou.app.login.service.impl.AbstractSecuredCommand;
import com.tchepannou.event.client.v1.EventResponse;
import com.tchepannou.event.client.v1.SearchRequest;
import com.tchepannou.party.client.v1.PartyResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractSearchEventCommand extends AbstractSecuredCommand<Void, AppEventCollectionResponse> {
    //-- Attributes
    @Autowired
    private PartyService partyService;

    @Autowired
    private CalendarService calendarService;


    //-- Abstract
    protected abstract List<Long> getTeamIds (final CommandContext context) throws IOException;

    protected abstract SearchRequest createSearchRequest (final CommandContext context) throws IOException;

    //-- AbstractSearchEventCommand overrides
    @Override
    protected AppEventCollectionResponse doExecute(Void req, CommandContext context) throws IOException {
        final SearchRequest request = createSearchRequest(context);

        final List<EventResponse> events = calendarService.search(request, context).getEvents();

        final Map<Long, PartyResponse> teams = getTeams(events, context);

        return new AppEventCollectionResponseMapper()
                .withEvents(events)
                .withTransactionInfo(getTransactionInfo())
                .withTeams(teams)
                .map();
    }

    //-- Getter
    public PartyService getPartyService() {
        return partyService;
    }


    //-- Protected
    protected SearchRequest next7Days (SearchRequest request){
        final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 0); //anything 0 - 23
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        request.setStartDate(dateFormat.format(cal.getTime()));

        cal.add(Calendar.DAY_OF_MONTH, 7);
        request.setEndDate(dateFormat.format(cal.getTime()));

        return request;
    }

    //-- Private
    private Map<Long, PartyResponse> getTeams (final List<EventResponse> events, final CommandContext context) throws IOException {
        final Set<Long> ids = events.stream()
                .map(event -> event.getCalendarId())
                .collect(Collectors.toSet());

        return partyService.teamsByIds(ids, context)
                .getParties()
                .stream()
                .collect(Collectors.toMap(p -> p.getId(), p -> p))
                ;
    }

}
