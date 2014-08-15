package com.zwb.config.impl;

import com.zwb.config.api.NoConfigurationException;

public class EnvironmentPropertyAccessor implements IConfigurationAccessor
{
	@Override
	public boolean hasKey(String id, String key)
	{
		return System.getenv().containsKey(createKey(id, key));
	}

	@Override
	public String getString(String id, String key) throws NoConfigurationException
	{
		return getString(createKey(id, key));
	}
	
	public String getString(String key) throws NoConfigurationException
	{
		String prop = System.getenv(key);
		if (prop == null)
		{
			NoConfigurationException.throwNoConfigurationException("<none>", key);
		}
		return new String(prop);
	}
	
	private String createKey(String id, String key)
	{
		return id +"."+key;
	}
}
