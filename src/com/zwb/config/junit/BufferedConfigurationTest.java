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

public class BufferedConfigurationTest extends TestCase
{
	final String id = "my.config.test.2";

	public void testValueBuffer() throws NoConfigurationException
	{
		String s = ConfigurationFactory.getBufferedConfiguration(id).getString("key.string.correct.1");
		assertEquals("a string is a string", s);

		for (int i = 0; i < 12; i++)
		{
			String ss = ConfigurationFactory.getBufferedConfiguration(id).getString("key.string.correct.1");
			assertEquals("a string is a string", s);
			assertEquals("a string is a string", ss);
			assertTrue(s.equals(ss));
			assertTrue(s == ss);
		}
	}

	public void testExceptionBuffer() throws NoConfigurationException
	{
		MalformedValueException ex = null;
		boolean thrown = false;
		try
		{
			ConfigurationFactory.getBufferedConfiguration(id).getInt("key.string.correct.1");
		}
		catch (MalformedValueException e)
		{
			ex = e;
			thrown = true;
		}
		assertTrue(thrown);

		for (int i = 0; i < 12; i++)
		{
			thrown = false;
			try
			{
				ConfigurationFactory.getBufferedConfiguration(id).getInt("key.string.correct.1");
			}
			catch (MalformedValueException e)
			{
				thrown = true;
				assertTrue(e.equals(ex));
				assertTrue(e == ex);
			}
			assertTrue(thrown);
		}
	}

	public void testValueNotBuffered() throws NoConfigurationException
	{
		String s = ConfigurationFactory.getConfiguration(id).getString("key.string.correct.1");
		assertEquals("a string is a string", s);

		for (int i = 0; i < 12; i++)
		{
			String ss = ConfigurationFactory.getConfiguration(id).getString("key.string.correct.1");
			assertEquals("a string is a string", s);
			assertEquals("a string is a string", ss);
			assertTrue(s.equals(ss));
			assertFalse(s == ss);
		}
	}

	public void testExceptionNotBuffered() throws NoConfigurationException
	{
		MalformedValueException ex = null;
		boolean thrown = false;
		try
		{
			ConfigurationFactory.getConfiguration(id).getInt("key.string.correct.1");
		}
		catch (MalformedValueException e)
		{
			ex = e;
			thrown = true;
		}
		assertTrue(thrown);

		for (int i = 0; i < 12; i++)
		{
			thrown = false;
			try
			{
				ConfigurationFactory.getConfiguration(id).getInt("key.string.correct.1");
			}
			catch (MalformedValueException e)
			{
				thrown = true;
				assertFalse(e.equals(ex));
				assertFalse(e == ex);
			}
			assertTrue(thrown);
		}
	}

	public void testRuntimeGainValueBuffer() throws NoConfigurationException
	{
		int times = 1000;

		long startUnbuffered = System.currentTimeMillis();
		for (int i = 0; i < times; i++)
		{
			ConfigurationFactory.getConfiguration(id).getString("key.string.correct.1");
		}
		long runtimeUnbuffered = System.currentTimeMillis() - startUnbuffered;

		long startBuffered = System.currentTimeMillis();
		for (int i = 0; i < times; i++)
		{
			ConfigurationFactory.getBufferedConfiguration(id).getString("key.string.correct.1");
		}
		long runtimeBuffered = System.currentTimeMillis() - startBuffered;

		System.out.println("RUNTIME UNBUFFERED [VALUE BUFFER]  : " + runtimeUnbuffered);
		System.out.println("RUNTIME BUFFERED [VALUE BUFFER]    : " + runtimeBuffered);

		assertTrue(runtimeUnbuffered > runtimeBuffered * 50);
	}

	public void testRuntimeGainExceptionBuffer() throws NoConfigurationException
	{
		int times = 1000;

		long startUnbuffered = System.currentTimeMillis();
		for (int i = 0; i < times; i++)
		{
			boolean thrown = false;
			try
			{
				ConfigurationFactory.getConfiguration(id).getInt("key.string.correct.1");
			}
			catch (MalformedValueException e)
			{
				thrown = true;
			}
			assertTrue(thrown);
		}
		long runtimeUnbuffered = System.currentTimeMillis() - startUnbuffered;

		long startBuffered = System.currentTimeMillis();
		for (int i = 0; i < times; i++)
		{
			boolean thrown = false;
			try
			{
				ConfigurationFactory.getBufferedConfiguration(id).getInt("key.string.correct.1");
			}
			catch (MalformedValueException e)
			{
				thrown = true;
			}
			assertTrue(thrown);
		}
		long runtimeBuffered = System.currentTimeMillis() - startBuffered;

		System.out.println("RUNTIME UNBUFFERED [EXCEPTION BUFFER]  : " + runtimeUnbuffered);
		System.out.println("RUNTIME BUFFERED [EXCEPTION BUFFER]    : " + runtimeBuffered);

		assertTrue(runtimeUnbuffered > runtimeBuffered * 50);
	}

}
