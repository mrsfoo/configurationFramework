package com.zwb.config.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.zwb.config.api.NoConfigurationException;

public class PropertiesFromResourceAccessor extends PropertiesFileAccessor
{
	String subfolder = "";
	
	public PropertiesFromResourceAccessor(String subfolder)
	{
		this.subfolder = subfolder;
	}

	@Override
	protected Properties getProps(String id, String key) throws NoConfigurationException
	{
		if(!this.props.containsKey(id))
		{
			String path;
			if(this.subfolder.isEmpty())
			{
				path = id+".properties";
			}
			else
			{
				path = subfolder+"/"+id+".properties";
			}
			InputStream in= PropertiesFromResourceAccessor.class.getClassLoader().getResourceAsStream(path);
			
			if(in==null)
			{
				NoConfigurationException.throwNoConfigurationException(id, key);
			}
			try
			{
				Properties p = new Properties();
				p.load(in);
				in.close();
				this.props.put(id, p);
				return p;
			}
			catch (IOException e)
			{
				NoConfigurationException.throwNoConfigurationException(id, key);
			}
		}
		return this.props.get(id);
	}

}
