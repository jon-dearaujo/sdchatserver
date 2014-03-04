package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
			ServerLog.getDefaultLog()
				.error(String.format(ERROR_CONFIGURE_CLIENT_IO, this.getClientName()));
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
				ServerLog.getDefaultLog()
					.error(String.format(ERROR_CLIENT, this.getClientName()).toString());
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
			} catch (IOException e)
			{
				ServerLog.getDefaultLog().info(String.format(CLIENT_DISCONNECTED, this.getClientName()));
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
			ServerLog.getDefaultLog()
			.error(String.format(ERROR_CLIENT, this.getClientName()).toString());
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
