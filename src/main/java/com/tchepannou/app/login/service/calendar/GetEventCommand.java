package com.tchepannou.app.login.service.calendar;

import com.tchepannou.app.login.service.CalendarService;
import com.tchepannou.app.login.service.CommandContext;
import com.tchepannou.app.login.service.impl.AbstractSecuredCommand;
import com.tchepannou.event.client.v1.EventResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class GetEventCommand  extends AbstractSecuredCommand<Void, EventResponse>  {

    @Autowired
    private CalendarService calendarService;

    @Override
    protected EventResponse doExecute(Void request, CommandContext context) throws IOException {
        return calendarService.get(context);
    }
}
