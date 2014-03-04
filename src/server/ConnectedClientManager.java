package server;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import program.ServerLog;

public class ConnectedClientManager
{
	private Map<String, ConnectedClient> clients;
	private static ConnectedClientManager instance;
	
	public static ConnectedClientManager getInstance()
	{
		if(instance == null)
		{
			instance = new ConnectedClientManager();
		}
		return instance;
	}
	
	private ConnectedClientManager()
	{
		this.clients = new HashMap<String, ConnectedClient>();
	}
	
	public void addAndStart(ConnectedClient client)
	{
		this.clients.put(client.getClientName(), client);
		new Thread(client).start();
		ServerLog.getDefaultLog().info(client.getClientName().concat(" connected"));
	}
	
	public void sendMessageToAllConnectedClient(String clientId, String message)
	{
		String formattedMessage = String.format("%s : %s", clientId, message);
		Set<String> keys = clients.keySet();
		for(String key : keys)
		{
			clients.get(key).writeMessage(formattedMessage);
		}
	}
	
	public void shutDownAllClients()
	{
		Set<String> keys = clients.keySet();
		for (String key : keys)
		{
			removeClient(key);
		}
	}

	public void removeClient(String clientName)
	{
		if(clients.containsKey(clientName))
		{
			ConnectedClient client = clients.get(clientName);
			client.setConnected(false);
			
			this.clients.remove(clientName);
		}
	}
}
