package com.itrip.common.security.rememberme;

import com.itrip.common.security.AuthenticationException;

@SuppressWarnings("serial")
public class RememberMeAuthenticationException extends AuthenticationException {
	public RememberMeAuthenticationException() {
	}

	public RememberMeAuthenticationException(String msg) {
		super(msg);
	}
}
