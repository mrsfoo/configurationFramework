package com.zwb.config.api;

public class BufferTypeException extends RuntimeException
{
	private static final long serialVersionUID = 7934362264012759513L;

	public BufferTypeException(String msg, Throwable nested)
	{
		super(msg, nested);
	}

	public static void throwBufferTypeException(String id, String key, Throwable t)
	{
		throw new BufferTypeException("fatal error for configuration id <" + id + "> and for key <" + key + ">: unknown exception type <" + t.getClass() + ">; this error should never occurr under any runtime conditions; however, obviously it did, which points to a severe error in source code which must be fixed immediately", t);
	}

}
