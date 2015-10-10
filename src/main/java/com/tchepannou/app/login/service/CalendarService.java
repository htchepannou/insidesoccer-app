package com.tchepannou.app.login.service;

import com.tchepannou.event.client.v1.EventCollectionResponse;
import com.tchepannou.event.client.v1.EventResponse;
import com.tchepannou.event.client.v1.SearchRequest;

import java.io.IOException;

public interface CalendarService {
    EventCollectionResponse search (SearchRequest request, CommandContext context) throws IOException;
    EventResponse get(CommandContext context) throws IOException;
}
