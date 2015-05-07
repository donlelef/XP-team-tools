package server.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import string.formatter.Formatter;

/**
 * A testable server with no message recovery function
 * 
 * @author alberto
 *
 */
public class TestableServerNoRecover extends AbstractServer {

	private List<Socket> clientSocketList = new LinkedList<Socket>();
	private IChatStorer chatStorer;
	private Socket clientSocket;
	public static final int NUM_OF_MESSAGES = 10;

	public TestableServerNoRecover(IChatStorer messageStorer) {
		super();
		this.chatStorer = messageStorer;
	}

	public TestableServerNoRecover() {
		super();
	}

	@Override
	public void listenClients() {

		try {

			while (true) {
				clientSocket = serverSocket.accept();
				clientSocketList.add(clientSocket);
				Runnable runnable = getRunnable();
				Thread thread = new Thread(runnable);
				thread.start();
			}
		} catch (IOException e) {

		}
	}

	private Runnable getRunnable() {
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {

						String line = getLine();

						if (line != null) {
							System.out.println(line); // TODO
							chatStorer.storeMessage(null, line);
							propagateMessageToAllClients(line);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}

		};
		return runnable;
	}

	private String getLine() throws IOException {
		String line;
		BufferedReader in = new BufferedReader(new InputStreamReader(
				clientSocket.getInputStream()));
		line = in.readLine();
		return line;
	}

	private void propagateMessageToAllClients(String message)
			throws IOException {

		for (Socket socket : clientSocketList) {
			propagateMessage(message, socket);
		}
	}

	private void propagateMessage(String message, Socket socket)
			throws IOException {
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
				socket.getOutputStream()));
		out.write(Formatter.appendNewLine(message));
		out.flush();
	}

	/**
	 * obtains the entire map from chatstorer and extracts all and only messages
	 * in natural order (!)
	 * 
	 * @return
	 */

	public String getLastMessage() {

		Map<String, ArrayList<String>> mapMessages = chatStorer.getMessages();
		Set<String> keys = mapMessages.keySet();
		ArrayList<String> messages = new ArrayList<String>();
		for (String key : keys) {
			messages.addAll(mapMessages.get(key));
		}

		return messages.get(messages.size() - 1);
	}

}