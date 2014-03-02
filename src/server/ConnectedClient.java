package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ConnectedClient implements Runnable
{
	private Socket clientSocket;
	private String clientName;
	private BufferedReader clientIn;
	private BufferedWriter clientOut;
	private boolean connected;
	
	public String getClientName()
	{
		return clientName;
	}

	public ConnectedClient(String clientName, Socket clientSocket)
	{
		this.clientSocket = clientSocket;
		this.clientName = clientName;
		configureIO();
		this.connected = true;
	}
	
	private void configureIO()
	{
		try
		{
			this.clientIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			this.clientOut = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void run()
	{
		while (isConnected())
		{
			try
			{
				String submittedMessage = this.clientIn.readLine();
				if (submittedMessage != null && !submittedMessage.isEmpty())
				{
					sendMessageForAll(submittedMessage);
				}
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	private void sendMessageForAll(String submittedMessage)
	{
		sendMessageForAll(submittedMessage);
	}
	
	public void writeMessage(String message)
	{
		try
		{
			this.clientOut.write(message);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public boolean isConnected()
	{
		return connected && this.clientSocket.isConnected();
	}

	public void setConnected(boolean connected)
	{
		this.connected = connected;
	}
	
	
}
