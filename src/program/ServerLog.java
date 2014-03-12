package program;

import org.apache.log4j.Appender;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class ServerLog
{

	public static void addDefaultLogAppender(Appender appender)
	{
		getDefaultLog().addAppender(appender);
	}
	
	public static Logger getDefaultLog()
	{
		BasicConfigurator.configure();
		return Logger.getLogger(ServerLog.class);
	}
}
