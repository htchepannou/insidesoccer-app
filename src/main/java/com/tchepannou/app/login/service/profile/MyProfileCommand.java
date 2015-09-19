package com.tchepannou.app.login.service.profile;

import com.tchepannou.app.login.client.v1.Constants;
import com.tchepannou.app.login.client.v1.profile.AppProfileResponse;
import com.tchepannou.app.login.exception.NotFoundException;
import com.tchepannou.app.login.service.CommandContext;
import com.tchepannou.app.login.service.PartyService;
import com.tchepannou.app.login.service.impl.AbstractSecuredCommand;
import com.tchepannou.core.http.HttpException;
import com.tchepannou.party.client.v1.PartyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class MyProfileCommand extends AbstractSecuredCommand<Void, AppProfileResponse> {
    //-- Attributes
    @Autowired
    private PartyService partyService;

    //-- MyProfileCommand overrides
    @Override
    protected AppProfileResponse doExecute(Void request, CommandContext context) throws IOException {
        try {
            PartyResponse party = partyService.get(getUserId(), context);
            return new AppProfileResponse(getTransactionInfo(), party);

        } catch (HttpException e) {
            final int status = e.getStatus();

            if (status == 404) {
                throw new NotFoundException(Constants.ERROR_NOT_FOUND, e);
            } else {
                throw e;
            }
        }
    }
}
