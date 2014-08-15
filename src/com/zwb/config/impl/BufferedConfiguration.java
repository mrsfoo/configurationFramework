package com.zwb.config.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zwb.config.api.BufferNullValueException;
import com.zwb.config.api.BufferReflectException;
import com.zwb.config.api.BufferTypeException;
import com.zwb.config.api.IConfiguration;
import com.zwb.config.api.NoConfigurationException;
import com.zwb.config.api.MalformedValueException;

public class BufferedConfiguration extends AbstractConfiguration implements IConfiguration
{
	private IConfiguration config;

	private Map<String, Boolean> hasKeyBuffer = new HashMap<>();
	private Map<String, String> getStringValueBuffer = new HashMap<>();
	private Map<String, NoConfigurationException> getStringExceptionBuffer = new HashMap<>();
	private Map<String, Integer> getIntValueBuffer = new HashMap<>();
	private Map<String, Throwable> getIntExceptionBuffer = new HashMap<>();
	private Map<String, Double> getDoubleValueBuffer = new HashMap<>();
	private Map<String, Throwable> getDoubleExceptionBuffer = new HashMap<>();
	private Map<String, Boolean> getBoolValueBuffer = new HashMap<>();
	private Map<String, Throwable> getBoolExceptionBuffer = new HashMap<>();
	private Map<String, List<String>> getListOfStringsValueBuffer = new HashMap<>();
	private Map<String, NoConfigurationException> getListOfStringsExceptionBuffer = new HashMap<>();

	public BufferedConfiguration(IConfiguration config)
	{
		this.config = config;
	}

	private <E extends Throwable> void checkExceptionBuffer(String key, Map<String, E> buffer) throws E
	{
		if (buffer.containsKey(key))
		{
			throw buffer.get(key);
		}
	}

	private <E1 extends Throwable, E2 extends Throwable> void checkExceptionBuffer2(String key, Map<String, Throwable> buffer, Class<E1> exceptionType1, Class<E2> exceptionType2) throws E1, E2
	{
		if (buffer.containsKey(key))
		{
			Throwable t = buffer.get(key);
			if (t.getClass().equals(exceptionType1))
			{
				throw (E1) t;
			}
			else if (t.getClass().equals(exceptionType2))
			{
				throw (E2) t;
			}
			else
			{
				BufferTypeException.throwBufferTypeException(this.getId(), key, t);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private <T> T getValueViaReflection(String key, String getValueMethodName, Map<String, T> valueBuffer) throws Throwable
	{
		try
		{
			if (valueBuffer.containsKey(key))
			{
				return valueBuffer.get(key);
			}

			Method method = this.config.getClass().getMethod(getValueMethodName, String.class);
			T value = (T) method.invoke(this.config, key);
			valueBuffer.put(key, value);
			return value;
		}
		catch (NoSuchMethodException | SecurityException e)
		{
			BufferReflectException.throwBufferReflectException(this.getId(), key, e);
		}
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
		{
			if (e.getClass().equals(InvocationTargetException.class))
			{
				throw e.getCause();
			}
			BufferReflectException.throwBufferReflectException(this.getId(), key, e);
		}
		// never reached
		return null;
	}

	@SuppressWarnings("unchecked")
	private <T, E extends Throwable> T checkValueBuffer(String key, String getValueMethodName, Map<String, T> valueBuffer, Map<String, E> exceptionBuffer, Class<E> exceptionType) throws E
	{
		checkExceptionBuffer(key, exceptionBuffer);
		try
		{
			T value = getValueViaReflection(key, getValueMethodName, valueBuffer);
			if (value == null)
			{
				BufferNullValueException.throwBufferNullValueException(this.getId(), key);
			}
			return value;
		}
		catch (Throwable t)
		{
			if (t.getClass().equals(exceptionType))
			{
				exceptionBuffer.put(key, (E) t);
				throw (E) t;
			}
			else
			{
				BufferTypeException.throwBufferTypeException(this.getId(), key, t);
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private <T, E1 extends Throwable, E2 extends Throwable> T checkValueBuffer2(String key, String getValueMethodName, Map<String, T> valueBuffer, Map<String, Throwable> exceptionBuffer, Class<E1> exceptionType1, Class<E2> exceptionType2) throws E1, E2
	{
		checkExceptionBuffer2(key, exceptionBuffer, exceptionType1, exceptionType2);
		try
		{
			T value = getValueViaReflection(key, getValueMethodName, valueBuffer);
			if (value == null)
			{
				BufferNullValueException.throwBufferNullValueException(this.getId(), key);
			}
			return value;
		}
		catch (Throwable t)
		{
			if (t.getClass().equals(exceptionType1))
			{
				exceptionBuffer.put(key, (E1) t);
				throw (E1) t;
			}
			else if (t.getClass().equals(exceptionType2))
			{
				exceptionBuffer.put(key, (E2) t);
				throw (E2) t;
			}
			else
			{
				BufferTypeException.throwBufferTypeException(this.getId(), key, t);
			}
		}
		return null;
	}

	@Override
	public boolean hasKey(String key)
	{
		if ((getId() == null) || getId().isEmpty())
		{
			return false;
		}
		if ((key == null) || key.isEmpty())
		{
			return false;
		}
		if (!hasKeyBuffer.containsKey(key))
		{
			hasKeyBuffer.put(key, config.hasKey(key));
		}
		return hasKeyBuffer.get(key);
	}

	@Override
	public String getString(String key) throws NoConfigurationException
	{
		assertArgumentKey(this.getId(), key);
		return checkValueBuffer(key, "getString", getStringValueBuffer, getStringExceptionBuffer, NoConfigurationException.class);
	}

	@Override
	public int getInt(String key) throws NoConfigurationException, MalformedValueException
	{
		assertArgumentKey(this.getId(), key);
		return checkValueBuffer2(key, "getInt", getIntValueBuffer, getIntExceptionBuffer, NoConfigurationException.class, MalformedValueException.class);
	}

	@Override
	public double getDouble(String key) throws NoConfigurationException, MalformedValueException
	{
		assertArgumentKey(this.getId(), key);
		return checkValueBuffer2(key, "getDouble", getDoubleValueBuffer, getDoubleExceptionBuffer, NoConfigurationException.class, MalformedValueException.class);
	}

	@Override
	public boolean getBool(String key) throws NoConfigurationException, MalformedValueException
	{
		assertArgumentKey(this.getId(), key);
		return checkValueBuffer2(key, "getBool", getBoolValueBuffer, getBoolExceptionBuffer, NoConfigurationException.class, MalformedValueException.class);
	}

	@Override
	public List<String> getListOfStrings(String key) throws NoConfigurationException
	{
		assertArgumentKey(this.getId(), key);
		return checkValueBuffer(key, "getListOfStrings", getListOfStringsValueBuffer, getListOfStringsExceptionBuffer, NoConfigurationException.class);
	}

	@Override
	public String getString(String key, String defaultValue)
	{
		try
		{
			assertArgumentKey(this.getId(), key);
		}
		catch (NoConfigurationException e)
		{
			return defaultValue;
		}
		try
		{
			return this.getString(key);
		}
		catch (NoConfigurationException e)
		{
			return new String(defaultValue);
		}
	}

	@Override
	public int getInt(String key, int defaultValue)
	{
		try
		{
			assertArgumentKey(this.getId(), key);
		}
		catch (NoConfigurationException e)
		{
			return defaultValue;
		}
		try
		{
			return this.getInt(key);
		}
		catch (NoConfigurationException | MalformedValueException e)
		{
			return defaultValue;
		}
	}

	@Override
	public double getDouble(String key, double defaultValue)
	{
		try
		{
			assertArgumentKey(this.getId(), key);
		}
		catch (NoConfigurationException e)
		{
			return defaultValue;
		}
		try
		{
			return this.getDouble(key);
		}
		catch (NoConfigurationException | MalformedValueException e)
		{
			return defaultValue;
		}
	}

	@Override
	public boolean getBool(String key, boolean defaultValue)
	{
		try
		{
			assertArgumentKey(this.getId(), key);
		}
		catch (NoConfigurationException e)
		{
			return defaultValue;
		}
		try
		{
			return this.getBool(key);
		}
		catch (NoConfigurationException | MalformedValueException e)
		{
			return defaultValue;
		}
	}

	@Override
	public List<String> getListOfStrings(String key, List<String> defaultValue)
	{
		try
		{
			assertArgumentKey(this.getId(), key);
		}
		catch (NoConfigurationException e)
		{
			return defaultValue;
		}
		try
		{
			return this.getListOfStrings(key);
		}
		catch (NoConfigurationException e)
		{
			return new ArrayList<String>(defaultValue);
		}
	}

	@Override
	public String getId()
	{
		return this.config.getId();
	}

}
