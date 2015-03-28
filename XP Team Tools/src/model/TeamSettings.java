package model;

import java.util.ArrayList;

public class TeamSettings {
	
	private ArrayList<String> teamMembers = new ArrayList<String>();
	private ArrayList<String> possibleTasksStates = new ArrayList<String>();
	private ArrayList<String> possibleUserStoriesStates = new ArrayList<String>();
	
	public void setPossibleTasksStates(String... possibleStates) {
		for (String state : possibleStates) {
			this.possibleTasksStates.add(state);
		}		
	}

	public void setPossibleUserStoriesStates(String... possibleStates) {
		for (String state : possibleStates) {
			this.possibleUserStoriesStates.add(state);
		}		
	}
	
	public ArrayList<String> getTeamMembers() {
		return teamMembers;
	}

	public ArrayList<String> getPossibleTasksStates() {
		return possibleTasksStates;
	}

	public ArrayList<String> getPossibleUserStoriesStates() {
		return possibleUserStoriesStates;
	}
	
	public void addTeamMember(String member) {
		this.teamMembers.add(member);
	}

	
}