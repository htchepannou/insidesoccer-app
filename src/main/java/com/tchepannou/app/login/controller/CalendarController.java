package com.tchepannou.app.login.controller;

import com.tchepannou.app.login.client.v1.Constants;
import com.tchepannou.app.login.client.v1.event.AppEventCollectionResponse;
import com.tchepannou.app.login.service.calendar.GetEventCommand;
import com.tchepannou.app.login.service.calendar.MyUpcomingEventsCommand;
import com.tchepannou.app.login.service.calendar.TeamUpcomingEventsCommand;
import com.tchepannou.core.http.Http;
import com.tchepannou.event.client.v1.EventResponse;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Api(basePath = "/v1/app/calendar", value = "Calendar", produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(value="/v1/app/calendar", produces = MediaType.APPLICATION_JSON_VALUE)
public class CalendarController extends AbstractController {
    @Autowired
    MyUpcomingEventsCommand myUpcomingEvents;

    @Autowired
    TeamUpcomingEventsCommand teamUpcomingEvents;

    @Autowired
    GetEventCommand getEventCommand;

    //-- REST methods
    @RequestMapping(method = RequestMethod.GET, value="/upcoming")
    @ApiOperation("Returns user upcoming events")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = Constants.ERROR_AUTH_FAILED),
    })
    public AppEventCollectionResponse myUpcoming(
            @RequestHeader(value= Http.HEADER_ACCESS_TOKEN) String accessToken,
            @RequestParam(defaultValue = "30") int limit,
            @RequestHeader(defaultValue = "0") int offset
    ) throws IOException {
        return myUpcomingEvents.execute(null,
                new CommandContextImpl()
                        .withAccessTokenId(accessToken)
                        .withLimit(limit)
                        .withOffset(offset)
        );
    }

    @RequestMapping(method = RequestMethod.GET, value="/{teamId}/upcoming")
    @ApiOperation("Returns teams upcoming events")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = Constants.ERROR_AUTH_FAILED),
    })
    public AppEventCollectionResponse teamUpcoming(
            @RequestHeader(value= Http.HEADER_ACCESS_TOKEN, required = false) String accessToken,
            @PathVariable long teamId,
            @RequestParam(defaultValue = "30") int limit,
            @RequestHeader(defaultValue = "0") int offset
    ) throws IOException {
        return teamUpcomingEvents.execute(null,
                new CommandContextImpl()
                        .withId(teamId)
                        .withAccessTokenId(accessToken)
                        .withLimit(limit)
                        .withOffset(offset)
        );
    }


    @RequestMapping(method = RequestMethod.GET, value="/event/{id}")
    @ApiOperation("Returns an event")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = Constants.ERROR_NOT_FOUND),
    })
    public EventResponse get(
            @RequestHeader(value= Http.HEADER_ACCESS_TOKEN, required = false) String accessToken,
            @PathVariable long id
    ) throws IOException {
        return getEventCommand.execute(null,
                new CommandContextImpl()
                        .withId(id)
                        .withAccessTokenId(accessToken)
        );
    }
}
