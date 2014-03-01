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
	private ServerSocket serverSocket;
	private boolean isAlive;
	public Server()
	{}
	
	private void startServerSocket()
	{
		try
		{
			this.serverSocket = new ServerSocket(2031);
		} catch (IOException e)
		{
			throw new StartException(MESSAGE_START_ERROR);
		}
	}
	
	public void start()
	{
		//TODO mensagens de alerta server;
		startServerSocket();
		waitForConnections();
	}
	
	private void waitForConnections()
	{
		while (isAlive())
		{
			try
			{
				Socket connectedClientsocket = getNewConnectedClientsocket();
				BufferedReader clientIn = 
						new BufferedReader( 
								new InputStreamReader(connectedClientsocket.getInputStream()));
				String clientName = clientIn.readLine();
				ConnectedClientManager.getInstance()
					.addAndStart(new ConnectedClient(clientName, connectedClientsocket));
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private Socket getNewConnectedClientsocket()
	{
		Socket client = null;
		try
		{
			client = this.serverSocket.accept();
		} catch (IOException e)
		{
			// TODO LOG!
			e.printStackTrace();
		}
		return client;
	}

	public boolean isAlive()
	{
		return isAlive;
	}

	public void setAlive(boolean isAlive)
	{
		this.isAlive = isAlive;
	}
	
	
}
