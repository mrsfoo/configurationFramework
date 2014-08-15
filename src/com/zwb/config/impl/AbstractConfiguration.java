package com.zwb.config.impl;

import com.zwb.config.api.IConfiguration;
import com.zwb.config.api.NoConfigurationException;

public abstract class AbstractConfiguration implements IConfiguration
{
	protected void assertArgumentKey(String id, String key) throws NoConfigurationException
	{
		if ((id == null) || id.isEmpty())
		{
			throw new NoConfigurationException("configuration id <" + id + "> not valid");
		}
		if ((key == null) || key.isEmpty())
		{
			throw new NoConfigurationException("configuration key <" + key + "> not valid");
		}
	}
}
