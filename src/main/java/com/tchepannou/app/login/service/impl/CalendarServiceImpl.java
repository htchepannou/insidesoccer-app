package com.tchepannou.app.login.service.impl;

import com.tchepannou.app.login.service.CommandContext;
import com.tchepannou.app.login.service.CalendarService;
import com.tchepannou.event.client.v1.EventCollectionResponse;
import com.tchepannou.event.client.v1.SearchRequest;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

public class CalendarServiceImpl extends AbstractHttpService implements CalendarService {
    //-- Attributes
    public static final String PATH_PREFIX = "/v1/calendar/";

    @Value("${calendar.hostname}")
    private String hostname;

    @Value("${calendar.port}")
    private int port;



    @Override
    public EventCollectionResponse search(SearchRequest request, CommandContext context) throws IOException {
        return createHttp(context)
                .withPath(PATH_PREFIX + "search")
                .withPayload(request)
                .post(EventCollectionResponse.class);
    }


    //-- AbstractHttpService overrides
    @Override protected String getHostname() {
        return hostname;
    }

    @Override protected int getPort() {
        return port;
    }
}
