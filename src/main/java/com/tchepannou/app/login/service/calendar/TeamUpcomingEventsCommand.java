package com.tchepannou.app.login.service.calendar;

import com.tchepannou.app.login.service.CommandContext;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class TeamUpcomingEventsCommand extends AbstractUpcomingEventsCommand {
    @Override
    protected List<Long> getTeamIds (CommandContext context) throws IOException {
        return Collections.singletonList(context.getId());
    }
}
