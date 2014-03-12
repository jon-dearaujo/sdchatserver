package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import program.ServerLog;

public class ConnectedClient implements Runnable
{
	private static final String ERROR_CLIENT = "A problem occured with %s. He is disconnected\n";
	private static final String CLIENT_DISCONNECTED = "%s has disconnected\n";
	private static final String ERROR_CONFIGURE_CLIENT_IO = "An error occurred when configuring io parameters of the client %s\n";
	private Socket clientSocket;
	private String clientName;
	private BufferedReader clientIn;
	private PrintWriter clientOut;
	private boolean connected;
	
	public String getClientName()
	{
		return clientName;
	}
	
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public ConnectedClient(String clientName, Socket clientSocket)
	{
		this.clientSocket = clientSocket;
		this.clientName = clientName;
		configureIO();
		this.connected = true;
	}
	
	public ConnectedClient(Socket clientSocket)
	{
		this.clientSocket = clientSocket;
		configureIO();
		this.connected = true;
	}
	
	private void configureIO()
	{
		try
		{
			this.clientIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			this.clientOut = new PrintWriter(clientSocket.getOutputStream(), true);
		} catch (IOException e)
		{
			System.out.println(String.format(ERROR_CONFIGURE_CLIENT_IO, this.getClientName()));
		}
	}
	
	@Override
	public void run()
	{
		while (isConnected())
		{
			try
			{
				String submittedMessage = clientIn.readLine();
				if (submittedMessage != null)
				{
					if(!submittedMessage.isEmpty())
					{
						sendMessageForAll(submittedMessage);
					}
				}else
				{
					break;
				}
			} catch (IOException e)
			{
				System.out.println(String.format(ERROR_CLIENT, this.getClientName()).toString());
				close();
			}
		}
		close();
		
	}

	private void close()
	{
		if(this.clientSocket.isConnected())
		{
			try
			{
				this.clientSocket.close();
				this.clientIn.close();
				this.clientOut.close();
				ConnectedClientManager.getInstance().removeClient(this.getClientName());
				System.out.println(String.format(CLIENT_DISCONNECTED, this.getClientName()));
			} catch (IOException e)
			{
				System.out.println(String.format(ERROR_CLIENT, this.getClientName()).toString());
			}
		}
	}

	private void sendMessageForAll(String submittedMessage)
	{
		ConnectedClientManager.getInstance().sendMessageToAllConnectedClient(getClientName(), submittedMessage);
	}
	
	public BufferedReader getClientIn()
	{
	return this.clientIn;	
	}
	
	public void writeMessage(String message)
	{
		this.clientOut.println(message);
		this.clientOut.flush();
	}

	public boolean isConnected()
	{
		return connected && !this.clientSocket.isClosed();
	}

	public void setConnected(boolean connected)
	{
		this.connected = connected;
	}
	
	
}
