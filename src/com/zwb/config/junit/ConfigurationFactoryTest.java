package com.zwb.config.junit;

import junit.framework.TestCase;

import com.zwb.config.api.ConfigurationFactory;

public class ConfigurationFactoryTest extends TestCase
{
	String id = "my.config.test";
	
	
	public void testConfigurationId()
	{
		assertEquals(id, ConfigurationFactory.getConfiguration(id).getId());
		assertEquals(id, ConfigurationFactory.getBufferedConfiguration(id).getId());
		assertEquals(id, ConfigurationFactory.getConfiguration(id, true).getId());
		assertEquals(id, ConfigurationFactory.getConfiguration(id, false).getId());
	}
	
	public void testConfigurationBuffer()
	{
		assertTrue(ConfigurationFactory.getConfiguration(id).equals(ConfigurationFactory.getConfiguration(id)));
		assertTrue(ConfigurationFactory.getConfiguration(id)==ConfigurationFactory.getConfiguration(id));

		assertTrue(ConfigurationFactory.getBufferedConfiguration(id).equals(ConfigurationFactory.getBufferedConfiguration(id)));
		assertTrue(ConfigurationFactory.getBufferedConfiguration(id)==ConfigurationFactory.getBufferedConfiguration(id));

		assertTrue(ConfigurationFactory.getConfiguration(id,true).equals(ConfigurationFactory.getConfiguration(id,true)));
		assertTrue(ConfigurationFactory.getConfiguration(id,true)==ConfigurationFactory.getConfiguration(id,true));

		assertTrue(ConfigurationFactory.getConfiguration(id,false).equals(ConfigurationFactory.getConfiguration(id,false)));
		assertTrue(ConfigurationFactory.getConfiguration(id,false)==ConfigurationFactory.getConfiguration(id,false));
		
		assertFalse(ConfigurationFactory.getConfiguration(id).equals(ConfigurationFactory.getBufferedConfiguration(id)));
		assertFalse(ConfigurationFactory.getConfiguration(id)==ConfigurationFactory.getBufferedConfiguration(id));
		
		assertFalse(ConfigurationFactory.getConfiguration(id,true).equals(ConfigurationFactory.getConfiguration(id,false)));
		assertFalse(ConfigurationFactory.getConfiguration(id,true)==ConfigurationFactory.getConfiguration(id,false));
	}
	
}
