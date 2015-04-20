package server.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import server.utils.FilesWriter;

/**
 * A class that
 * 
 * @author alberto
 *
 */
public class CacheMap implements IChatStorer, IMessageRecover {

	private Map<String, ArrayList<String>> mapMessageList = new HashMap<String, ArrayList<String>>();
	private FilesWriter database = new FilesWriter(this);
	
	@Override
	public void storeMessage(String teamName, String message) {
		if (mapMessageList.containsKey(teamName)) {
			addTeamMessage(teamName, message);
		} else {
			initMapMessageList(teamName, message);
		}
	}

	/**
	 * It initializes the Message List for a team, adding the teamName as key
	 * of the map
	 * 
	 * @param teamName
	 * @param message
	 */
	private void initMapMessageList(String teamName, String message) {
		mapMessageList.put(teamName, new ArrayList<String>());
		addTeamMessage(teamName, message);
	}

	/**
	 * It adds a message to the team's database
	 */
	private void addTeamMessage(String teamName, String message) {
		mapMessageList.get(teamName).add(message);
		writeData();
	}

	@Override
	public Map<String, ArrayList<String>> getMessages() {

		return mapMessageList;
	}

	@Override
	public String[] recoverLastMessages(String teamName, int numOfMessages)
			throws NoMessagesException {

		// TODO

		String[] messages = new String[numOfMessages];

		if (mapMessageList.containsKey(teamName)) {
			ArrayList<String> messageList = mapMessageList.get(teamName);

			for (int i = 0; i < numOfMessages; i++) {
				int size = messageList.size();
				messages[i] = messageList.get(size - numOfMessages + i);
			}
		}
		return messages;
	}

	@Override
	public int getNumOfMessages(String teamName) throws NoMessagesException {
		return mapMessageList.get(teamName).size();
	}
	
	/**
	 * It writes messages on team's Database
	 */
	public void writeData(){
		database.writeDatabase();
	}
}
