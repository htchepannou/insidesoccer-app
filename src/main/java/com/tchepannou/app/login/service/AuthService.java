package com.tchepannou.app.login.service;

import com.tchepannou.app.login.client.v1.login.AppLoginRequest;
import com.tchepannou.auth.client.v1.AccessTokenResponse;

import java.io.IOException;

public interface AuthService {
    AccessTokenResponse login (AppLoginRequest request, CommandContext context) throws IOException;
    void logout (CommandContext context) throws IOException;
}
