package tests;

import server.model.AbstractServer;
import server.model.CacheList;
import server.model.TestableServer;

public class ServerMain {
	public static void main(String[] args) {
		AbstractServer server = new TestableServer(new CacheList());
		
		try {
			server.openPort(9999);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		server.listenClients();
	}
	
}