package com.zwb.config.impl;

import com.zwb.config.api.NoConfigurationException;

public class SystemPropertyAccessor implements IConfigurationAccessor
{
	@Override
	public boolean hasKey(String id, String key)
	{
		return System.getProperties().containsKey(createKey(id, key));
	}

	@Override
	public String getString(String id, String key) throws NoConfigurationException
	{
		return getString(createKey(id, key));
	}
	
	public String getString(String key) throws NoConfigurationException
	{
		String val = System.getProperty(key);
		if(val==null)
		{
			NoConfigurationException.throwNoConfigurationException("<none>", key);
		}
		return new String(val);
	}
	
	private String createKey(String id, String key)
	{
		return id +"."+key;
	}
}
