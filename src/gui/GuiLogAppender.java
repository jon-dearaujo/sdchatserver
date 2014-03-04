package gui;

import javax.swing.JTextArea;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

public class GuiLogAppender extends AppenderSkeleton
{
	private JTextArea logArea;
	public GuiLogAppender(JTextArea logArea)
	{
		this.logArea = logArea;
	}
	@Override
	public void close()
	{
		if(!this.closed)
		{
			this.closed = true;
		}
	}

	@Override
	public boolean requiresLayout()
	{
		return true;
	}

	@Override
	protected void append(LoggingEvent arg0)
	{
		String message = arg0.getMessage().toString();
		logArea.append(message);
	}

}
