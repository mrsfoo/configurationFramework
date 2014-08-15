package com.zwb.config.api;

import java.util.List;

public interface IConfiguration
{
	public boolean hasKey(String key);

	public String getString(String key) throws NoConfigurationException;
	public int getInt(String key) throws NoConfigurationException, MalformedValueException;
	public double getDouble(String key) throws NoConfigurationException, MalformedValueException;
	public boolean getBool(String key) throws NoConfigurationException, MalformedValueException;
	public List<String> getListOfStrings(String key) throws NoConfigurationException;

	public String getString(String key, String defaultValue);
	public int getInt(String key, int defaultValue);
	public double getDouble(String key, double defaultValue);
	public boolean getBool(String key, boolean defaultValue);
	public List<String> getListOfStrings(String key, List<String> defaultValue);

	public String getId();
}
