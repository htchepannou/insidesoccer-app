package com.tchepannou.app.login.service.login;

import com.tchepannou.app.login.service.AuthService;
import com.tchepannou.app.login.service.CommandContext;
import com.tchepannou.app.login.service.impl.AbstractSecuredCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class LogoutCommand extends AbstractSecuredCommand<Void, Void> {
    //-- Attributes
    @Autowired
    private AuthService authService;


    //-- Command overrides
    @Override
    protected Void doExecute(Void request, CommandContext context) throws IOException {
        authService.logout(context);
        return null;
    }
}
