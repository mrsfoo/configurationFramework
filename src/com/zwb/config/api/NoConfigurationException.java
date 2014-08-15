package com.zwb.config.api;

public class NoConfigurationException extends Exception
{
	private static final long serialVersionUID = 1812255097999296514L;

	public NoConfigurationException(String msg)
	{
		super(msg);
	}

	public static void throwNoConfigurationException(String id, String key) throws NoConfigurationException
	{
		throw new NoConfigurationException("configuration key <" + key + "> not found for configuration id <" + id + ">");
	}
}
