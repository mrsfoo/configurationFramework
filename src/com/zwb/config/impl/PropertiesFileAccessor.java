package com.zwb.config.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.zwb.config.api.NoConfigurationException;

public abstract class PropertiesFileAccessor implements IConfigurationAccessor
{
	protected Map<String, Properties> props = new HashMap<String, Properties>();	
	
	@Override
	public boolean hasKey(String id, String key)
	{
		try
		{
			Properties p = getProps(id, key);
			if(!p.containsKey(key))
			{
				return false;
			}
			else
			{
				return true;
			}
		}
		catch (NoConfigurationException e)
		{
			return false;
		}
	}

	@Override
	public String getString(String id, String key) throws NoConfigurationException
	{
		Properties p = getProps(id, key);
		String val = p.getProperty(key);
		if(val==null)
		{
			NoConfigurationException.throwNoConfigurationException(id, key);
		}
		return new String(val);
	}
	
	protected abstract Properties getProps(String id, String key) throws NoConfigurationException;
}
