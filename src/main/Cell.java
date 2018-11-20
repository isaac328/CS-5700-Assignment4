package main;

import org.apache.commons.lang3.time.StopWatch;

import java.util.ArrayList;
import java.util.Objects;

public class Cell implements Subject, Comparable, Cloneable{
	//flag for if the cell is set
	private boolean set;
	//current value of the cell
	private String value;
	//possible values for the cell
	private ArrayList<String> possibleValues;

	//coordinates
	private int xCoord;
	private int yCoord;

	//the cells observers
	private ArrayList<Observer> observers;

	//stopwatch for measuring how much time was taken one one possible value
	private static StopWatch watch = new StopWatch();


	public Cell(String value, String[] allPossibleValues, int xCoord, int yCoord){
		//initialize values
		observers = new ArrayList<>(1);
		this.value = value;
		this.possibleValues = new ArrayList<>(allPossibleValues.length);
		this.set = true;

		this.xCoord = xCoord;
		this.yCoord = yCoord;

		//if the value is unset
		if(value.equals("-")){
			this.set = false;
			for(String s : allPossibleValues){
				possibleValues.add(s);
			}
		}
	}

	/**
	 * Remove a possible value from this cell
	 * @param value the value to remove
	 * @throws Exception
	 */
	public void removePossibleValue(String value) throws Exception{
		//dont do anything if cell is already set
		if(set) return;
		//remove the value
		possibleValues.remove(value);
		//if there is only one value remaining, that has to be the value of the cell
		if(possibleValues.size() == 1){
			startWatch();
			this.value = possibleValues.get(0);
			this.set = true;
			watch.suspend();
			Notify();
		}
	}

	/**
	 * Manually set the value of a cell
	 * @param value the value
	 * @throws Exception
	 */
	public void setValue(String value) throws Exception{
		//if the value is not a possible value, throw an exception
		if(!possibleValues.contains(value)){
			throw new Exception("Invalid Value");
		}

		//set value and notify observers
		this.value = value;
		set = true;
		Notify();

	}

	/**
	 * Find out if the cell is set
	 * @return if the cell is set
	 */
	public boolean isSet(){ return this.set; }

	/**
	 * Get the possible values for the cell
	 * @return the possible values
	 */
	public ArrayList<String> getPossibleValues(){ return this.possibleValues; }

	/**
	 * Attach an observer to the cell
	 * @param o the observer to add
	 */
	@Override
	public void Attach(Observer o) {
		observers.add(o);
	}

	/**
	 * Remove an observer from the cell
	 * @param o the observer to remove
	 */
	@Override
	public void Detach(Observer o) {
		observers.remove(o);
	}

	/**
	 * Notify all Observers
	 * @throws Exception
	 */
	@Override
	public void Notify() throws Exception {
		for(Observer o : observers){
			o.Update(this);
		}
	}

	/**
	 * Get the string value of the cell
	 * @return
	 */
	@Override
	public String toString(){
		return value;
	}

	/**
	 * Override equals. An object is defined as equal if it has the same coordinates
	 * @param o the other cell
	 * @return
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Cell cell = (Cell) o;
		return xCoord == cell.xCoord &&
				yCoord == cell.yCoord;
	}

	@Override
	public int hashCode() {
		return Objects.hash(xCoord, yCoord);
	}

	@Override
	public int compareTo(Object o) {
		return this.getPossibleValues().size() - ((Cell)o).getPossibleValues().size();
	}

	/**
	 * Clone the cell
	 * @return
	 * @throws CloneNotSupportedException
	 */
	@Override
	public Object clone() throws CloneNotSupportedException{
		Cell cell = (Cell) super.clone();

		ArrayList<String> possibleValues = new ArrayList<>(this.possibleValues.size());
		for(String s : this.getPossibleValues())
			possibleValues.add(s);

		cell.possibleValues = possibleValues;
		cell.observers = new ArrayList<>();
		return cell;
	}

	/**
	 * Start the timer for one possibility
	 */
	private static void startWatch(){
		if(watch.isSuspended())
			watch.resume();
		else
			watch.start();
	}

	/**
	 * Get the time on the watch
	 * @return
	 */
	public static String getTime(){
		return watch.toString();
	}
}