package program;

import server.Server;
import gui.ServerFrame;

public class Program
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		new Server().getInstance().start();
	}

}
