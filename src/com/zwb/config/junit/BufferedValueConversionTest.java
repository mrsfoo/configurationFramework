package com.zwb.config.junit;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import com.zwb.config.api.ConfigurationFactory;
import com.zwb.config.api.IConfiguration;
import com.zwb.config.api.MalformedValueException;
import com.zwb.config.api.NoConfigurationException;
import com.zwb.config.impl.EnvironmentPropertyAccessor;
import com.zwb.config.impl.PropertiesFromFileSystemAccessor;
import com.zwb.config.impl.PropertiesFromResourceAccessor;
import com.zwb.config.impl.RegistryAccessor;
import com.zwb.config.impl.SystemPropertyAccessor;

public class BufferedValueConversionTest extends TestCase
{
	final String id = "my.config.test.2";
	IConfiguration config = ConfigurationFactory.getBufferedConfiguration(id);

	public void testInvalid() throws NoConfigurationException
	{
		assertFalse(config.hasKey("key.42"));
		assertFalse(ConfigurationFactory.getConfiguration("sdfsdsdfgsfgsf").hasKey("key.1"));

		boolean thrown = false;
		try
		{
			config.getString("key.42");
		}
		catch (NoConfigurationException e)
		{
			thrown = true;
		}
		assertTrue(thrown);

		thrown = false;
		try
		{
			ConfigurationFactory.getConfiguration("sdfsdsdfgsfgsf").getString("key.1");
		}
		catch (NoConfigurationException e)
		{
			thrown = true;
		}
		assertTrue(thrown);
	}

	public void testIllegalArguments() throws NoConfigurationException
	{
		assertFalse(config.hasKey(""));
		assertFalse(ConfigurationFactory.getConfiguration("").hasKey("key.1"));
		assertFalse(config.hasKey(null));
		assertFalse(ConfigurationFactory.getConfiguration(null).hasKey("key.1"));

		boolean thrown = false;
		try
		{
			config.getString("");
		}
		catch (NoConfigurationException e)
		{
			thrown = true;
		}
		assertTrue(thrown);

		thrown = false;
		try
		{
			config.getString(null);
		}
		catch (NoConfigurationException e)
		{
			thrown = true;
		}
		assertTrue(thrown);

		thrown = false;
		try
		{
			ConfigurationFactory.getConfiguration("").getString("key.1");
		}
		catch (NoConfigurationException e)
		{
			thrown = true;
		}
		assertTrue(thrown);

		thrown = false;
		try
		{
			ConfigurationFactory.getConfiguration(null).getString("key.1");
		}
		catch (NoConfigurationException e)
		{
			thrown = true;
		}
		assertTrue(thrown);

	}

	public void testGetStringCorrect() throws NoConfigurationException
	{
		assertEquals("a string is a string", config.getString("key.string.correct.1"));
		assertEquals("-42.424242", config.getString("key.double.correct.2"));
		assertEquals("foo,bar ,      baz,bammm bum", config.getString("key.listOstrings.correct.1"));
		assertEquals("", config.getString("key.general.empty"));
		assertEquals("", config.getString("key.general.whitespaces"));
	}

	public void testGetIntCorrect() throws NoConfigurationException, MalformedValueException
	{
		assertEquals(42, config.getInt("key.int.correct.1"));
		assertEquals(-42, config.getInt("key.int.correct.2"));
		assertEquals(1, config.getInt("key.bool.correct.2"));
		assertEquals(0, config.getInt("key.bool.correct.4"));
	}

	public void testGetIntMalformed() throws NoConfigurationException
	{
		boolean thrown = false;
		try
		{
			config.getInt("key.string.correct.1");
		}
		catch (MalformedValueException e)
		{
			thrown = true;
		}
		assertTrue(thrown);

		thrown = false;
		try
		{
			config.getInt("key.double.correct.2");
		}
		catch (MalformedValueException e)
		{
			thrown = true;
		}
		assertTrue(thrown);

		thrown = false;
		try
		{
			config.getInt("key.listOstrings.correct.1");
		}
		catch (MalformedValueException e)
		{
			thrown = true;
		}
		assertTrue(thrown);

		thrown = false;
		try
		{
			config.getInt("key.general.empty");
		}
		catch (MalformedValueException e)
		{
			thrown = true;
		}
		assertTrue(thrown);

		thrown = false;
		try
		{
			config.getInt("key.general.whitespaces");
		}
		catch (MalformedValueException e)
		{
			thrown = true;
		}
		assertTrue(thrown);

		thrown = false;
		try
		{
			config.getInt("key.bool.correct.1");
		}
		catch (MalformedValueException e)
		{
			thrown = true;
		}
		assertTrue(thrown);
	}

	public void testGetDoubleCorrect() throws NoConfigurationException, MalformedValueException
	{
		assertEquals(42.0, config.getDouble("key.double.correct.1"));
		assertEquals(-42.424242, config.getDouble("key.double.correct.2"));
		assertEquals(1.0, config.getDouble("key.bool.correct.2"));
		assertEquals(0.0, config.getDouble("key.bool.correct.4"));
		assertEquals(42.0, config.getDouble("key.int.correct.1"));
		assertEquals(-42.0, config.getDouble("key.int.correct.2"));
	}

	public void testGetDoubleMalformed() throws NoConfigurationException
	{
		boolean thrown = false;
		try
		{
			config.getDouble("key.string.correct.1");
		}
		catch (MalformedValueException e)
		{
			thrown = true;
		}
		assertTrue(thrown);

		thrown = false;
		try
		{
			config.getDouble("key.listOstrings.correct.1");
		}
		catch (MalformedValueException e)
		{
			thrown = true;
		}
		assertTrue(thrown);

		thrown = false;
		try
		{
			config.getDouble("key.general.empty");
		}
		catch (MalformedValueException e)
		{
			thrown = true;
		}
		assertTrue(thrown);

		thrown = false;
		try
		{
			config.getDouble("key.general.whitespaces");
		}
		catch (MalformedValueException e)
		{
			thrown = true;
		}
		assertTrue(thrown);

		thrown = false;
		try
		{
			config.getDouble("key.bool.correct.1");
		}
		catch (MalformedValueException e)
		{
			thrown = true;
		}
		assertTrue(thrown);
	}

	public void testGetBoolCorrect() throws NoConfigurationException, MalformedValueException
	{
		assertEquals(true, config.getBool("key.bool.correct.1"));
		assertEquals(true, config.getBool("key.bool.correct.2"));
		assertEquals(false, config.getBool("key.bool.correct.3"));
		assertEquals(false, config.getBool("key.bool.correct.4"));
	}

	public void testGetBoolMalformed() throws NoConfigurationException
	{
		boolean thrown = false;
		try
		{
			config.getBool("key.string.correct.1");
		}
		catch (MalformedValueException e)
		{
			thrown = true;
		}
		assertTrue(thrown);

		thrown = false;
		try
		{
			config.getBool("key.double.correct.2");
		}
		catch (MalformedValueException e)
		{
			thrown = true;
		}
		assertTrue(thrown);

		thrown = false;
		try
		{
			config.getBool("key.listOstrings.correct.1");
		}
		catch (MalformedValueException e)
		{
			thrown = true;
		}
		assertTrue(thrown);

		thrown = false;
		try
		{
			config.getBool("key.general.empty");
		}
		catch (MalformedValueException e)
		{
			thrown = true;
		}
		assertTrue(thrown);

		thrown = false;
		try
		{
			config.getBool("key.general.whitespaces");
		}
		catch (MalformedValueException e)
		{
			thrown = true;
		}
		assertTrue(thrown);
	}

	public void testGetListOfStringsCorrect() throws NoConfigurationException, MalformedValueException
	{
		assertEquals(Arrays.asList("foo", "bar", "baz", "bammm bum"), config.getListOfStrings(("key.listOstrings.correct.1")));
		assertEquals(Arrays.asList("im not a list!"), config.getListOfStrings(("key.listOstrings.correct.2")));
		assertEquals(Arrays.asList("-42.424242"), config.getListOfStrings(("key.double.correct.2")));
		assertEquals(Arrays.asList(""), config.getListOfStrings(("key.general.empty")));
	}


}
