package com.zwb.config.api;

public class MalformedValueException extends Exception
{
	private static final long serialVersionUID = 9087823619688518698L;

	public MalformedValueException(String msg)
	{
		super(msg);
	}

	public static void throwMalformedValueException(String id, String key, ConfigurationValueType type) throws MalformedValueException
	{
		throw new MalformedValueException("error retrieving configuration key <" + key + "> for configuration id <" + id + "> with type" + type +"; the configuration value seems to be malformed");
	}
}
