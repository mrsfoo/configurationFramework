package com.zwb.config.impl;

import java.util.Arrays;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import com.zwb.config.api.NoConfigurationException;

public class RegistryAccessor implements IConfigurationAccessor
{
	@Override
	public boolean hasKey(String id, String key)
	{
		try
		{
			if (!Preferences.userRoot().nodeExists(id))
			{
				return false;
			}
			Preferences prefs = Preferences.userRoot().node(id);
			return Arrays.asList(prefs.childrenNames()).contains(key);
		}
		catch (BackingStoreException e)
		{
			return false;
		}
	}

	@Override
	public String getString(String id, String key) throws NoConfigurationException
	{
		try
		{
			if (!Preferences.userRoot().nodeExists(id))
			{
				NoConfigurationException.throwNoConfigurationException(id, key);
			}
			Preferences prefs = Preferences.userRoot().node(id);
			String pref = prefs.get(key, null);
			if (pref == null)
			{
				NoConfigurationException.throwNoConfigurationException(id, key);
			}
			return new String(pref);
		}
		catch (BackingStoreException e)
		{
			NoConfigurationException.throwNoConfigurationException(id, key);
		}
		// will never be reached, just to satisfy compiler!
		return null;
	}
}
