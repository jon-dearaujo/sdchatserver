package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import program.ServerLog;

public class Server
{
	public static final int SERVER_PORT = 2031;
	private static Server instance;
	private static final String MESSAGE_START_ERROR = "Falha ao iniciar o Servidor de chat\n";
	private static final String MESSAGE_START_SUCCESS = "server started successfully\n";
	private static final String MESSAGE_STOP_SERVER = "Server will shut down\n";
	private ServerSocket serverSocket;
	private boolean alive;
	
	public static Server getInstance()
	{
		if(instance == null)
		{
			instance = new Server();
		}
		return instance;
	}
	
	private void startServerSocket()
	{
		try
		{
			this.serverSocket = new ServerSocket(SERVER_PORT);
			this.alive = true;
		} catch (IOException e)
		{
			ServerLog.getDefaultLog().error(MESSAGE_START_ERROR);
		}
	}
	
	public void start()
	{
		startServerSocket();
		run();
		
	}
	
	private Socket getNewConnectedClientSocket() throws IOException
	{
		Socket client = null;
		client = this.serverSocket.accept();
		return client;
	}

	public boolean isAlive()
	{
		return alive;
	}

	public void setAlive(boolean isAlive)
	{
		this.alive = isAlive;
	}

	public void run()
	{
		System.out.println(MESSAGE_START_SUCCESS);
		while (isAlive())
		{
			try
			{
				Socket connectedClientsocket = getNewConnectedClientSocket();
				ConnectedClientManager.getInstance()
					.addAndStart(new ConnectedClient(connectedClientsocket));
			} catch (IOException e)
			{
				ServerLog.getDefaultLog().error(e.getMessage()+"\n");
			}
		}
	}
	
	public void stop()
	{
		this.setAlive(false);
		try
		{
			this.serverSocket.close();
			ConnectedClientManager.getInstance().shutDownAllClients();
			ServerLog.getDefaultLog().info(MESSAGE_STOP_SERVER);
		} catch (IOException e)
		{
			ServerLog.getDefaultLog().error("An error occurred\n");
			ServerLog.getDefaultLog().info(MESSAGE_STOP_SERVER);
		}
		
	}
}
