package com.zwb.config.impl;

import java.util.ArrayList;
import java.util.List;

import com.zwb.config.api.IConfiguration;
import com.zwb.config.api.NoConfigurationException;
import com.zwb.config.api.MalformedValueException;

public class Configuration extends AbstractConfiguration implements IConfiguration
{
	private String id = "";
	private ConfigurationAccessor acc = ConfigurationAccessorFactory.getConfigurationAccessor();

	public Configuration(String id)
	{
		this.id = id;
	}

	@Override
	public boolean hasKey(String key)
	{
		if ((id == null) || id.isEmpty())
		{
			return false;
		}
		if ((key == null) || key.isEmpty())
		{
			return false;
		}
		return acc.hasKey(this.id, key);
	}

	@Override
	public String getString(String key) throws NoConfigurationException
	{
		assertArgumentKey(this.id, key);
		return acc.getString(this.id, key);
	}

	@Override
	public int getInt(String key) throws NoConfigurationException, MalformedValueException
	{
		assertArgumentKey(this.id, key);
		return acc.getInt(this.id, key);
	}

	@Override
	public double getDouble(String key) throws NoConfigurationException, MalformedValueException
	{
		assertArgumentKey(this.id, key);
		return acc.getDouble(this.id, key);
	}

	@Override
	public boolean getBool(String key) throws NoConfigurationException, MalformedValueException
	{
		assertArgumentKey(this.id, key);
		return acc.getBool(this.id, key);
	}

	@Override
	public List<String> getListOfStrings(String key) throws NoConfigurationException
	{
		assertArgumentKey(this.id, key);
		return acc.getListOfStrings(this.id, key);
	}

	@Override
	public String getString(String key, String defaultValue)
	{
		try
		{
			assertArgumentKey(this.id, key);
		}
		catch (NoConfigurationException e)
		{
			return new String(defaultValue);
		}
		return this.acc.getString(this.id, key, defaultValue);
	}

	@Override
	public int getInt(String key, int defaultValue)
	{
		try
		{
			assertArgumentKey(this.id, key);
		}
		catch (NoConfigurationException e)
		{
			return defaultValue;
		}
		return this.acc.getInt(this.id, key, defaultValue);
	}

	@Override
	public double getDouble(String key, double defaultValue)
	{
		try
		{
			assertArgumentKey(this.id, key);
		}
		catch (NoConfigurationException e)
		{
			return defaultValue;
		}
		return this.acc.getDouble(this.id, key, defaultValue);
	}

	@Override
	public boolean getBool(String key, boolean defaultValue)
	{
		try
		{
			assertArgumentKey(this.id, key);
		}
		catch (NoConfigurationException e)
		{
			return defaultValue;
		}
		return this.acc.getBool(this.id, key, defaultValue);
	}

	@Override
	public List<String> getListOfStrings(String key, List<String> defaultValue)
	{
		try
		{
			assertArgumentKey(this.id, key);
		}
		catch (NoConfigurationException e)
		{
			return new ArrayList<String>(defaultValue);
		}
		return this.acc.getListOfStrings(this.id, key, defaultValue);
	}

	@Override
	public String getId()
	{
		return this.id;
	}

}
