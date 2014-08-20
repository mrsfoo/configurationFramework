package com.zwb.config.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.zwb.config.api.ConfigurationValueType;
import com.zwb.config.api.NoConfigurationException;
import com.zwb.config.api.MalformedValueException;

public class ConfigurationAccessor
{
	private static List<IConfigurationAccessor> accessors = new ArrayList<IConfigurationAccessor>();
	private static final String LIST_OF_STRINGS_SPLIT = ",";

	public ConfigurationAccessor()
	{
		if (accessors.isEmpty())
		{
			accessors.add(new SystemPropertyAccessor());
			accessors.add(new EnvironmentPropertyAccessor());
			accessors.add(new RegistryAccessor());

			accessors.add(new PropertiesFromResourceAccessor(""));
			accessors.add(new PropertiesFromResourceAccessor("config"));
			accessors.add(new PropertiesFromResourceAccessor("resources"));
			accessors.add(new PropertiesFromResourceAccessor("resources/config"));

			List<String> pathes = PropertiesFromFileSystemAccessor.getPossiblePathes();
			for (String path : pathes)
			{
				accessors.add(new PropertiesFromFileSystemAccessor(path));
			}
		}
	}

	public boolean hasKey(String id, String key)
	{
		for (IConfigurationAccessor a : accessors)
		{
			if (a.hasKey(id, key))
			{
				return true;
			}
		}
		return false;
	}

	public String getString(String id, String key) throws NoConfigurationException
	{
		for (IConfigurationAccessor a : accessors)
		{
			try
			{
				String val = a.getString(id, key);
				val = val.trim();
				return new String(val);
			}
			catch (NoConfigurationException e)
			{
				// go on!
			}
		}
		NoConfigurationException.throwNoConfigurationException(id, key);
		// will never be reached, just to satisfy compiler!
		return null;
	}

	public int getInt(String id, String key) throws NoConfigurationException, MalformedValueException
	{
		try
		{
			return Integer.parseInt(getString(id, key));
		}
		catch (NumberFormatException e)
		{
			MalformedValueException.throwMalformedValueException(id, key, ConfigurationValueType.INT);
		}
		// will never be reached, just to satisfy compiler!
		return 0;
	}

	public boolean getBool(String id, String key) throws NoConfigurationException, MalformedValueException
	{
		String s = getString(id, key).toLowerCase();
		boolean b = false;
		if (s.equals("true"))
		{
			return true;
		}
		else if (s.equals("1"))
		{
			return true;
		}
		else if (s.equals("false"))
		{
			return false;
		}
		else if (s.equals("0"))
		{
			return false;
		}
		else
		{
			MalformedValueException.throwMalformedValueException(id, key, ConfigurationValueType.BOOLEAN);
		}
		// will never be reached, just to satisfy compiler!
		return false;
	}

	public double getDouble(String id, String key) throws NoConfigurationException, MalformedValueException
	{
		try
		{
			return Double.parseDouble(getString(id, key));
		}
		catch (NumberFormatException e)
		{
			MalformedValueException.throwMalformedValueException(id, key, ConfigurationValueType.DOUBLE);
		}
		// will never be reached, just to satisfy compiler!
		return 0;
	}

	public List<String> getListOfStrings(String id, String key) throws NoConfigurationException
	{
		String string = getString(id, key);
		List<String> list = Arrays.asList(string.split(LIST_OF_STRINGS_SPLIT));
		List<String> returnList = new ArrayList<>();
		for (String s : list)
		{
			returnList.add(s.trim());
		}
		return returnList;
	}

	public String getString(String id, String key, String defaultValue)
	{
		try
		{
			return getString(id, key);
		}
		catch (NoConfigurationException e)
		{
			return new String(defaultValue);
		}
	}

	public int getInt(String id, String key, int defaultValue)
	{
		try
		{
			return getInt(id, key);
		}
		catch (NoConfigurationException | MalformedValueException e)
		{
			return defaultValue;
		}
	}

	public double getDouble(String id, String key, double defaultValue)
	{
		try
		{
			return getDouble(id, key);
		}
		catch (NoConfigurationException | MalformedValueException e)
		{
			return defaultValue;
		}
	}

	public boolean getBool(String id, String key, boolean defaultValue)
	{
		try
		{
			return getBool(id, key);
		}
		catch (NoConfigurationException | MalformedValueException e)
		{
			return defaultValue;
		}
	}

	public List<String> getListOfStrings(String id, String key, List<String> defaultValue)
	{
		try
		{
			return getListOfStrings(id, key);
		}
		catch (NoConfigurationException e)
		{
			return new ArrayList<String>(defaultValue);
		}
	}

	private void assertArgumentKey(String id, String key) throws NoConfigurationException
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
