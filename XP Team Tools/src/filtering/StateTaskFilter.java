package filtering;

import boards.Task;

public class StateTaskFilter implements Checker<Task> {

	private String state;
	
	public StateTaskFilter(String state) {
		this.state=state;
	}
	
	@Override
	public boolean check(Task tobeChecked) {
		if(tobeChecked.getState().compareTo(state)==0){
			return true;
		}
		return false;
	}

	
}
