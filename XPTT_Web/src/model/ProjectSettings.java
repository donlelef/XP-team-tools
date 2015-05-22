package model;

import java.util.List;
import java.util.ArrayList;

import boards.UserStoryBoard.UserStory;
import boards.taskBoard.Task;

/**
 * ProjectSettings interface provides simple ways to access every information
 * needed to perform data persistency over a certain project
 * 
 * @author simone, lele, incre, simo
 *
 */
public interface ProjectSettings {

	/**
	 * Returns the {@link TeamComponent} instances currently registered to this
	 * project
	 * 
	 * @return: an {@link ArrayList} containing each {@link TeamComponent}
	 * @see TeamComponent
	 */
	public List<TeamComponent> getTeamMembers();

	/**
	 * Returns the available states for a project task
	 * 
	 * @return: an {@link ArrayList} containing each available state
	 * 
	 * @see {@link Task#moveTaskToState(String)}
	 */
	public List<String> getPossibleTaskStates();

	/**
	 * Returns the available states for a development user story
	 * 
	 * @return: an {@link ArrayList} containing each available state
	 * 
	 * @see {@link UserStory#moveToState(String)}
	 */
	public List<String> getPossibleUserStoryStates();
}