package client.model;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;

import client.model.service.IClientService;
import client.model.teams.IListService;
import protocol.JsonMaker;
import protocol.JsonParser;
import string.formatter.Formatter;

/**
 * A client of the chat system, it sends and receives messages of one or more
 * conversations (chats) It is configurable adding implementation of
 * @IClientService and @IListService
 * 
 * @author Alberto
 */
public class Client {

	private ClientConnectionDetails clientDetails;
	private DataOutputStream os;
	private DataInputStream is;
	private HashMap<Integer, IClientService> services = new HashMap<Integer, IClientService>();
	private HashMap<Integer, IListService> listServices = new HashMap<Integer, IListService>();


	public Client() {
		super();

	}

	public Client(ClientConnectionDetails clientDetails) {
		super();
		this.clientDetails = clientDetails;
	}

	/**
	 * Opens input and output stream to the given hostname at the given port
	 * 
	 * @param hostName
	 *            name of the host or ip address in the correct form (x.x.x.x)
	 * @param port
	 *            port of the server opened for the connection
	 */
	public void openStreams(String hostName, int port) {
		try {

			clientDetails.setRealTimeSocket(new Socket(hostName, port));
			clientDetails.setRequestSocket(new Socket(hostName, port));
			clientDetails.setOnline(true);
			os = new DataOutputStream(clientDetails.getRealTimeSocket()
					.getOutputStream());
			is = new DataInputStream(clientDetails.getRealTimeSocket().getInputStream());
			sendMessageToServer(JsonMaker.connectToServerRequest(clientDetails));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sends a string message to the server to which it is connected
	 * 
	 * @param message
	 *            Message to send
	 */
	public void sendMessageToServer(String message) {
		message = Formatter.appendNewLine(message);
		if (clientDetails.getRealTimeSocket() != null && os != null && is != null) {
			try {
				os.writeBytes(message);
				os.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Reads from the socket messages
	 * 
	 * @throws Exception
	 *             TODO
	 */
	public void readFromSocket() throws Exception {

		BufferedReader input;
		try {
			input = new BufferedReader(new InputStreamReader(clientDetails
					.getRealTimeSocket().getInputStream()));
			while (true) {
				String read = input.readLine();
				if (read != null) {
					int requestCode = JsonParser
							.getRequest(read);
					IClientService service = services.get(requestCode);
					if (service != null) {
						service.setAttribute(read);
					} else {
						IListService listService = listServices.get(requestCode);
						listService.setMembs(read);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Closes the input/output stream and the socket
	 */
	public void closeStream() {
		try {

			os.close();
			is.close();
			clientDetails.getRealTimeSocket().close();
		} catch (IOException e) {

		}
	}

	public String waitServerResponse() {

		BufferedReader input;
		String response = "";
		try {
			input = new BufferedReader(new InputStreamReader(clientDetails
					.getRequestSocket().getInputStream()));
			response = input.readLine();
			while (response == null){
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	public ClientDetails getClientDetails() {
		return clientDetails;
	}

	public String getNickname() {
		return clientDetails.getNickname();
	}

	public String getTeamName() {
		return clientDetails.getTeamName();
	}

	public void addService(int id, IClientService service) {
		services.put(id, service);
	}
	
	public void addListService(int id, IListService service) {
		listServices.put(id, service);
	}
}