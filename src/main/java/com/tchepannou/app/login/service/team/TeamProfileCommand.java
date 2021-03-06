package com.tchepannou.app.login.service.team;

import com.tchepannou.app.login.client.v1.Constants;
import com.tchepannou.app.login.client.v1.team.AppTeamResponse;
import com.tchepannou.app.login.exception.NotFoundException;
import com.tchepannou.app.login.service.CommandContext;
import com.tchepannou.app.login.service.PartyService;
import com.tchepannou.app.login.service.impl.AbstractSecuredCommand;
import com.tchepannou.core.http.HttpException;
import com.tchepannou.party.client.v1.PartyResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class TeamProfileCommand extends AbstractSecuredCommand<Void, AppTeamResponse> {
    //-- Attributes
    @Autowired
    private PartyService partyService;

    //-- AbstractSecuredCommand overrides
    @Override
    protected AppTeamResponse doExecute(Void request, CommandContext context) throws IOException {
        try {
            PartyResponse response = partyService.get(context.getId(), context);
            return new AppTeamResponse(getTransactionInfo(), response);
        } catch (HttpException e){
            final int status = e.getStatus();

            if (status == 404){
                throw new NotFoundException(Constants.ERROR_NOT_FOUND, e);
            } else {
                throw e;
            }
        }
    }
}
