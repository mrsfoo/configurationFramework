package com.zwb.config.api;

import java.util.HashMap;
import java.util.Map;

import com.zwb.config.impl.BufferedConfiguration;
import com.zwb.config.impl.Configuration;

public class ConfigurationFactory
{
	private static final Map<String, IConfiguration> configurationMap = new HashMap<>();
	private static final Map<String, IConfiguration> bufferedConfigurationMap = new HashMap<>();
	
	public static IConfiguration getConfiguration(String id)
	{
		if(!configurationMap.containsKey(id))
		{
			configurationMap.put(id, new Configuration(id));
		}
		return configurationMap.get(id);
	}

	public static IConfiguration getBufferedConfiguration(String id)
	{
		if(!bufferedConfigurationMap.containsKey(id))
		{
			bufferedConfigurationMap.put(id, new BufferedConfiguration(getConfiguration(id)));
		}
		return bufferedConfigurationMap.get(id);
	}

	public static IConfiguration getConfiguration(String id, boolean buffered)
	{
		if(buffered)
		{
			return getBufferedConfiguration(id);
		}
		else
		{
			return getConfiguration(id);
		}
	}

	private static String appendSubConfiguration(String id, String sub)
	{
		if(id)
	}
	
}
