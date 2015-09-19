package com.tchepannou.app.login.service.event;

import com.tchepannou.app.login.client.v1.event.AppEventCollectionResponse;
import com.tchepannou.app.login.service.CommandContext;
import com.tchepannou.app.login.service.impl.AbstractSecuredCommand;

import java.io.IOException;

public class GetUpcomingEventsCommand extends AbstractSecuredCommand<Void, AppEventCollectionResponse> {
    @Override
    protected AppEventCollectionResponse doExecute(Void req, CommandContext context) throws IOException {
        return null;
    }
}
