package com.tchepannou.app.login.service;

import com.tchepannou.auth.client.v1.AccessTokenResponse;

import java.io.IOException;

public interface AccessTokenService {
    AccessTokenResponse get (CommandContext context) throws IOException;
}
