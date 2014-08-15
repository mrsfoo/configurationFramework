package com.zwb.config.junit;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import junit.framework.TestCase;

public class TrashTest extends TestCase
{
	private static final String na = "<n/a>";

	public void testPath()
	{
		System.err.println("#############################################");
		System.err.println("#############################################");
		System.err.println("#############################################");
		System.err.println(Arrays.asList(File.listRoots()));
		System.err.println(new File("").getAbsolutePath());
		System.err.println("#############################################");
		System.err.println("#############################################");
		System.err.println("#############################################");
	}
	
	public void test()
	{
		printSystemProperties();
		System.out.println("\n\n\n");

		printEnvirontmentVariables();
		System.out.println("\n\n\n");

		printRegistryEntriesUser();
		System.out.println("\n\n\n");

		printRegistryEntriesSystem();
		System.out.println("\n\n\n");

		for (int i = 0; i < 10; i++)
		{
			String id = "my.id";
			String key = "property." + i;
			String prop = getProperty(id, key);
			System.out.println(key + "  ->  " + prop);
		}

	}

	private void printSystemProperties()
	{
		System.getProperties().list(System.out);
	}

	private void printEnvirontmentVariables()
	{
		System.out.println("-- listing env properties --");
		System.out.println(System.getenv().toString().replace(',', '\n'));
	}

	private void printRegistryEntriesUser() 
	{
		System.out.println("-- listing user registry entries --");
		try
		{
			System.out.println("PATH: "+Preferences.userRoot().absolutePath());
			Preferences.userRoot().exportSubtree(System.out);
		}
		catch (IOException | BackingStoreException e)
		{
			e.printStackTrace();
		}
	}

	private void printRegistryEntriesSystem() 
	{
		System.out.println("-- listing system registry entries --");
		try
		{
			System.out.println("PATH: "+Preferences.systemRoot().absolutePath());
			Preferences.systemRoot().exportSubtree(System.out);
		}
		catch (IOException | BackingStoreException e)
		{
			e.printStackTrace();
		}
	}

	private String getProperty(String id, String key)
	{
		String prop = new String(na);
		prop = getSystemProperty(id, key);
		if (!prop.equals(na))
		{
			return "[System-Property]" + prop;
		}

		prop = getEnvironmentVariable(id, key);
		if (!prop.equals(na))
		{
			return "[Env-Varibale]" + prop;
		}

		prop = getRegistrySystemRoot(id, key);
		if (!prop.equals(na))
		{
			return "[Registry-Entry]" + prop;
		}

		prop = getRegistryUserRoot(id, key);
		if (!prop.equals(na))
		{
			return "[Registry-Entry]" + prop;
		}
		return "[NOT_FOUND]" + prop;
	}

	private String getSystemProperty(String id, String key)
	{
		return System.getProperty(id + "." + key, na);
	}

	private String getEnvironmentVariable(String id, String key)
	{
		String prop = System.getenv(id + "." + key);
		if (prop != null)
		{
			return prop;
		}
		else
		{
			return new String(na);
		}
	}

	private String getRegistryUserRoot(String id, String key)
	{
		Preferences prefs = Preferences.userRoot().node(id);
		return prefs.get(key, na);
	}

	private String getRegistrySystemRoot(String id, String key)
	{
		Preferences prefs = Preferences.systemRoot().node(id);
//		System.out.println("\n\n\n------------------");
//		try
//		{
//			System.out.println(Arrays.asList(Preferences.systemRoot().childrenNames()));
//		}
//		catch (BackingStoreException e)
//		{
//			e.printStackTrace();
//		}
//		System.out.println("------------------\n\n\n");
		return prefs.get(key, na);
	}

}
