package com.zwb.config.impl;

import com.zwb.config.impl.ConfigurationAccessor;

public class ConfigurationAccessorFactory
{
	private static ConfigurationAccessor instance = new ConfigurationAccessor();
	public static ConfigurationAccessor getConfigurationAccessor()
	{
		return instance;
	}
}
