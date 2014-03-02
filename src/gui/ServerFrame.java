package gui;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import javax.swing.JFrame;


public class ServerFrame extends JFrame
{
	public ServerFrame()
	{
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Server Chat");
		this.setSize(300, 300);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		Logger log = Logger.getLogger(this.getClass().getName());
		log.warning("START!");
	}
}
