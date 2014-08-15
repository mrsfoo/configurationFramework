package com.zwb.config.junit;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import com.zwb.config.api.ConfigurationFactory;
import com.zwb.config.api.IConfiguration;
import com.zwb.config.api.NoConfigurationException;
import com.zwb.config.impl.EnvironmentPropertyAccessor;
import com.zwb.config.impl.PropertiesFromFileSystemAccessor;
import com.zwb.config.impl.PropertiesFromResourceAccessor;
import com.zwb.config.impl.RegistryAccessor;
import com.zwb.config.impl.SystemPropertyAccessor;

public class PathHierarchyTest extends TestCase
{
	IConfiguration config = ConfigurationFactory.getConfiguration("my.config.test");

	public void testHasKey() throws NoConfigurationException
	{
		assertTrue(config.hasKey("key.1"));
		assertTrue(config.hasKey("key.2"));
		assertTrue(config.hasKey("key.3"));
		assertTrue(config.hasKey("key.4"));
		assertTrue(config.hasKey("key.5"));
		assertTrue(config.hasKey("key.6"));
		assertTrue(config.hasKey("key.7"));
		assertTrue(config.hasKey("key.8"));
	}

	public void testSystemProperty() throws NoConfigurationException
	{
		assertEquals("system property", config.getString("key.1"));
	}

	public void testEnironmentVariables() throws NoConfigurationException
	{
		assertEquals("user env variable", config.getString("key.2"));
		assertEquals("system env variable", config.getString("key.3"));
	}

	public void testRegistry() throws NoConfigurationException
	{
		assertEquals("registry", config.getString("key.4"));
	}

	public void testResourceLoading() throws NoConfigurationException
	{
		assertEquals("c:/zwb/prog/Eclipse-Workspace_[Java]/MyConfig/src/my.config.test.properties", config.getString("key.5"));
		assertEquals("c:/zwb/prog/Eclipse-Workspace_[Java]/MyConfig/src/config/my.config.test.properties", config.getString("key.6"));
		assertEquals("c:/zwb/prog/Eclipse-Workspace_[Java]/MyConfig/src/resources/my.config.test.properties", config.getString("key.7"));
		assertEquals("c:/zwb/prog/Eclipse-Workspace_[Java]/MyConfig/src/resources/config/my.config.test.properties", config.getString("key.8"));
	}
	
	public void testGetPathFromProperty() throws NoConfigurationException
	{
		assertEquals("c:/zwb/prog/Eclipse-Workspace_[Java]/MyConfig/ConfigurationFolder/my.config.test.properties", config.getString("key.9"));
		assertEquals("c:/zwb/prog/Eclipse-Workspace_[Java]/MyConfig/ConfigurationFolder/config/my.config.test.properties", config.getString("key.10"));
	}
	

//		String newString = new String(string) + File.pathSeparator + "config";
//		out.add(string);
//		out.add(newString);
//
//		Set<String> l = new HashSet<String>();
//		/** current dir */
//		l.add(new File("").getAbsolutePath());
//
//		/** system property: user.dir */
//		String prop = getSystemProperty("user.dir");
//		if (prop != null)
//			l.add(prop.substring(0, prop.length() - 4));
//
//		/** system property: user.home */
//		prop = getSystemProperty("user.home");
//		if (prop != null)
//			l.add(prop);
//
//		/** environment variable: USERPROFILE */
//		prop = getEnvProperty("USERPROFILE");
//		if (prop != null)
//			l.add(prop);
//
//		/** environment variable: LOCALAPPDATA */
//		prop = getEnvProperty("LOCALAPPDATA");
//		if (prop != null)
//			l.add(prop);
//
//		/** environment variable: APPDATA */
//		prop = getEnvProperty("APPDATA");
//		if (prop != null)
//			l.add(prop);
//
//		/** environment variable: PUBLIC */
//		prop = getEnvProperty("PUBLIC");
//		if (prop != null)
//			l.add(prop);

}
