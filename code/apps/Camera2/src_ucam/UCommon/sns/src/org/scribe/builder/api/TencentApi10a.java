/**
 *   Copyright (C) 2010,2011 Thundersoft Corporation
 *   All rights Reserved
 */
package org.scribe.builder.api;

import org.scribe.model.Token;

public class TencentApi10a extends DefaultApi10a {

    private static final String REQUEST_TOKEN_URL = "https://open.t.qq.com/cgi-bin/request_token";
    private static final String ACCESS_TOKEN_URL = "https://open.t.qq.com/cgi-bin/access_token";
    private static final String AUTHORIZE_URL = "https://open.t.qq.com/cgi-bin/authorize?oauth_token=%s";

    @Override
    public String getRequestTokenEndpoint() {
        return REQUEST_TOKEN_URL;
    }

    @Override
    public String getAccessTokenEndpoint() {
        return ACCESS_TOKEN_URL;
    }

    @Override
    public String getAuthorizationUrl(Token requestToken) {
        return String.format(AUTHORIZE_URL, requestToken.getToken());
    }

    public String getLogoutUrl(Token accessToken) {
        return null;
    }
}
