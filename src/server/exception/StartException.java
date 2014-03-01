package server.exception;

public class StartException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	private String message;
	
	public StartException(String message)
	{
		this.message = message;
	}
	@Override
	public String getMessage()
	{
		return this.message;
	}
}
