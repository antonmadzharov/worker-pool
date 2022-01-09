package com.app.workerpool.security;

import javax.servlet.http.HttpServletRequest;

public interface AuthenticationService {

    String getUsername(HttpServletRequest req);

}
