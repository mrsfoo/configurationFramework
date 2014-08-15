package com.zwb.config.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import com.zwb.config.api.NoConfigurationException;

public class PropertiesFromFileSystemAccessor extends PropertiesFileAccessor
{
	private String folder;
	private static final String PATH_PROPERTY_ID = "com.zwb.config";
	private static final String PATH_PROPERTY_KEY = "configuration_path";

	public PropertiesFromFileSystemAccessor(String folder)
	{
		this.folder = folder;
	}

	@Override
	protected Properties getProps(String id, String key) throws NoConfigurationException
	{
		if (!this.props.containsKey(id))
		{
			Properties p = new Properties();
			File f = new File(this.buildFilename(this.folder, id));
			if (!f.exists() || !f.isFile())
			{
				NoConfigurationException.throwNoConfigurationException(id, key);
			}
			try
			{
				p.load(new FileReader(f));
				this.props.put(id, p);
				return p;
			}
			catch (FileNotFoundException e)
			{
				NoConfigurationException.throwNoConfigurationException(id, key);
			}
			catch (IOException e)
			{
				NoConfigurationException.throwNoConfigurationException(id, key);
			}
		}
		return this.props.get(id);
	}

	private String buildFilename(String folder, String id)
	{
		return folder + File.separator + id + ".properties";
	}

	public static List<String> getPossiblePathes()
	{
		List<String> l = new ArrayList<String>();
		String path = getConfigPathFromProperty();
		if ((path != null) && !path.isEmpty())
		{
			l.add(path);
		}
		l.addAll(getAvailablePathes());
		List<String> out = new ArrayList<String>();
		for (String string : l)
		{
			String newString = new String(string) + File.separator + "config";
			out.add(string);
			out.add(newString);
		}
		return out;
	}

	private static Set<String> getAvailablePathes()
	{
		Set<String> l = new HashSet<String>();
		/** current dir */
		l.add(new File("").getAbsolutePath());

		/** system property: user.dir */
		String prop = getSystemProperty("user.dir");
		if (prop != null)
			l.add(prop.substring(0, prop.length() - 4));

		/** system property: user.home */
		prop = getSystemProperty("user.home");
		if (prop != null)
			l.add(prop);

		/** environment variable: USERPROFILE */
		prop = getEnvProperty("USERPROFILE");
		if (prop != null)
			l.add(prop);

		/** environment variable: LOCALAPPDATA */
		prop = getEnvProperty("LOCALAPPDATA");
		if (prop != null)
			l.add(prop);

		/** environment variable: APPDATA */
		prop = getEnvProperty("APPDATA");
		if (prop != null)
			l.add(prop);

		/** environment variable: PUBLIC */
		prop = getEnvProperty("PUBLIC");
		if (prop != null)
			l.add(prop);

		return l;
	}

	private static String getConfigPathFromProperty()
	{
		try
		{
			return new SystemPropertyAccessor().getString(PATH_PROPERTY_ID, PATH_PROPERTY_KEY);
		}
		catch (NoConfigurationException e)
		{
			// go on
		}
		try
		{
			return new EnvironmentPropertyAccessor().getString(PATH_PROPERTY_ID, PATH_PROPERTY_KEY);
		}
		catch (NoConfigurationException e)
		{
			// go on
		}
		try
		{
			return new RegistryAccessor().getString(PATH_PROPERTY_ID, PATH_PROPERTY_KEY);
		}
		catch (NoConfigurationException e)
		{
			// go on
		}
		return null;
	}

	private static String getSystemProperty(String key)
	{
		try
		{
			return new SystemPropertyAccessor().getString(key);
		}
		catch (NoConfigurationException e)
		{
			return null;
		}
	}

	private static String getEnvProperty(String key)
	{
		try
		{
			return new EnvironmentPropertyAccessor().getString(key);
		}
		catch (NoConfigurationException e)
		{
			return null;
		}
	}

}
