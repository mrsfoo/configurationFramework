package com.zwb.config.api;

public class BufferReflectException extends RuntimeException
{
	private static final long serialVersionUID = 3665513972580618277L;

	public BufferReflectException(String msg, Throwable nested)
	{
		super(msg, nested);
	}

	public static void throwBufferReflectException(String id, String key, Throwable t)
	{
		throw new BufferReflectException("fatal error for configuration id <" + id + "> and for key <" + key + ">: reflection exception of type <" + t.getClass() + ">; this error should never occurr under any runtime conditions; however, obviously it did, which points to a severe error in source code which must be fixed immediately", t);
	}

}
