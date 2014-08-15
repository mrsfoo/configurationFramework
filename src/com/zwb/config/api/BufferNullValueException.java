package com.zwb.config.api;

public class BufferNullValueException extends RuntimeException
{
	private static final long serialVersionUID = 7934362264012759513L;

	public BufferNullValueException(String msg)
	{
		super(msg);
	}

	public static void throwBufferNullValueException(String id, String key)
	{
		throw new BufferNullValueException("fatal error for configuration id <" + id + "> and for key <" + key + ">: NULL value; this error should never occurr under any runtime conditions; however, obviously it did, which points to a severe error in source code which must be fixed immediately");
	}

}
