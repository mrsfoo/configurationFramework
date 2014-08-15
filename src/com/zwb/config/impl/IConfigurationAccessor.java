package com.zwb.config.impl;

import com.zwb.config.api.NoConfigurationException;

public interface IConfigurationAccessor
{
	public boolean hasKey(String id, String key);
	public String getString(String id, String key) throws NoConfigurationException;

}
