package com.zwb.config.api;

public enum ConfigurationValueType
{
	STRING,
	INT,
	DOUBLE,
	BOOLEAN,
	LIST_OF_STRINGS;
	
	public String toString(ConfigurationValueType type)
	{
		switch(type)
		{
		case BOOLEAN:
			return "BOOLEAN";
		case DOUBLE:
			return "DOUBLE";
		case INT:
			return "INT";
		case LIST_OF_STRINGS:
			return "LIST_OF_STRINGS";
		case STRING:
			return "STRING";
		default:
			return "UNKNOWN";
		}
	}
	
	public String toString()
	{
		return toString(this);
	}
}
