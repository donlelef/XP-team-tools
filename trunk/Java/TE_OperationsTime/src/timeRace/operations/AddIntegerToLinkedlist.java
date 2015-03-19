package timeRace.operations;

import java.util.LinkedList;

import timeRace.IOperation;

/**
 * This class is a concrete implementor of {@link IOperation}. It adds a 
 * variable number of integers to a Linkedlist.
 * @author lele
 * @since 1.0
 * @version 1.0
 */
public class AddIntegerToLinkedlist implements IOperation{
	
	private LinkedList<Integer> list = new LinkedList<Integer>();
	private int[] data;
	private String name = "Adding to a LinkedList";

	@Override
	public void doOperation() {
		
		for (int i = 0; i < data.length; i++) {
			this.list.add(data[i]);
		}
	}
	
	@Override
	public void initializeData(int[] randomValues) {
		this.data = randomValues;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
	@Override
	public void debug(){
		for (int i : list) {
			System.out.println(i);
		}
	}
}