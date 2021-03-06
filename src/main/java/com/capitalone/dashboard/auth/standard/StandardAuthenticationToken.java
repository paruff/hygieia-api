package com.capitalone.dashboard.auth.standard;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class StandardAuthenticationToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = 7187799207155545385L;
	
	private final Object principal;
	private Object credentials;

	@SuppressWarnings("PMD")
	public StandardAuthenticationToken(Object principal, Object credentials) {
		super(null);
		this.principal = principal;
		this.credentials = credentials;
		setAuthenticated(false);
	}

	@SuppressWarnings("PMD")
	public StandardAuthenticationToken(Object principal, Object credentials,
			Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.principal = principal;
		this.credentials = credentials;
		super.setAuthenticated(true);
	}

	public Object getCredentials() {
		return this.credentials;
	}

	public Object getPrincipal() {
		return this.principal;
	}

	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		if (isAuthenticated) {
			throw new IllegalArgumentException(
					"Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
		}

		super.setAuthenticated(false);
	}

	@Override
	public void eraseCredentials() {
		super.eraseCredentials();
		credentials = null;
	}

	private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
		throw new java.io.NotSerializableException("com.capitalone.dashboard.auth.standard.StandardAuthenticationToken");
	}

	private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
		throw new java.io.NotSerializableException("com.capitalone.dashboard.auth.standard.StandardAuthenticationToken");
	}
}
