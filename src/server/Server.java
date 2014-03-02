package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import server.exception.StartException;

public class Server
{
	private static final String MESSAGE_START_ERROR = "Falha ao iniciar o Servidor de chat";
	private static final int SERVER_PORT = 2031;
	private ServerSocket serverSocket;
	private boolean alive;
	public Server()
	{}
	
	private void startServerSocket()
	{
		try
		{
			this.serverSocket = new ServerSocket(SERVER_PORT);
			this.alive = true;
		} catch (IOException e)
		{
			throw new StartException(MESSAGE_START_ERROR);
		}
	}
	
	public void start()
	{
		startServerSocket();
		waitForConnections();
	}
	
	private void waitForConnections()
	{
		while (isAlive())
		{
			try
			{
				Socket connectedClientsocket = getNewConnectedClientSocket();
				BufferedReader clientIn = 
						new BufferedReader( 
								new InputStreamReader(connectedClientsocket.getInputStream()));
				String clientName = clientIn.readLine();
				ConnectedClientManager.getInstance()
					.addAndStart(new ConnectedClient(clientName, connectedClientsocket));
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	private Socket getNewConnectedClientSocket()
	{
		Socket client = null;
		try
		{
			client = this.serverSocket.accept();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
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
	
	
}
