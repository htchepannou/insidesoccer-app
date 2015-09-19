package com.tchepannou.app.login.service.login;

import com.tchepannou.app.login.client.v1.Constants;
import com.tchepannou.app.login.client.v1.login.AppLoginRequest;
import com.tchepannou.app.login.client.v1.login.AppLoginResponse;
import com.tchepannou.app.login.exception.LoginException;
import com.tchepannou.app.login.service.AuthService;
import com.tchepannou.app.login.service.CommandContext;
import com.tchepannou.app.login.service.impl.AbstractCommand;
import com.tchepannou.auth.client.v1.AccessTokenResponse;
import com.tchepannou.core.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class LoginCommand extends AbstractCommand<AppLoginRequest, AppLoginResponse> {
    //-- Attributes
    @Autowired
    private AuthService authService;


    //-- Command overrides
    @Override
    protected AppLoginResponse doExecute(AppLoginRequest request, CommandContext context) throws IOException {
        try {
            AccessTokenResponse accessToken = authService.login(request, context);

            return new AppLoginResponse(getTransactionInfo(), accessToken);

        } catch (HttpException e){
            final int status = e.getStatus();

            if (status == 409){
                throw new LoginException(Constants.ERROR_AUTH_FAILED, e);
            } else {
                throw e;
            }
        }
    }
}
