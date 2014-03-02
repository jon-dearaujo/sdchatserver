package server;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
}
