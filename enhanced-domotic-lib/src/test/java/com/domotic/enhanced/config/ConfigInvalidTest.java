package com.domotic.enhanced.config;

import com.domotic.enhanced.domain.Protocol;

public class ConfigInvalidTest implements DomoticConfig {

	@Override
	public Protocol getProtocol() {
		return null;
	}

	@Override
	public String getHost() {
		return null;
	}

	@Override
	public Integer getPort() {
		return null;
	}

}
