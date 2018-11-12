package main;

import java.util.ArrayList;
import java.util.Objects;

public class Cell implements Subject, Comparable{
	private boolean set;
	private String value;
	private ArrayList<String> possibleValues;

	private int xCoord;
	private int yCoord;

	private ArrayList<Observer> observers;

	public Cell(String value, String[] allPossibleValues, int xCoord, int yCoord){
		observers = new ArrayList<>(1);
		this.value = value;
		this.possibleValues = new ArrayList<>(allPossibleValues.length);
		this.set = true;

		this.xCoord = xCoord;
		this.yCoord = yCoord;

		if(value.equals("-")){
			this.set = false;
			for(String s : allPossibleValues){
				possibleValues.add(s);
			}
		}
	}

	public void removePossibleValue(String value) throws Exception{
		possibleValues.remove(value);
		if(possibleValues.size() == 1){
			this.value = possibleValues.get(0);
			this.set = true;
			Notify();
		}
	}

	public void setValue(String value) throws Exception{
		if(!possibleValues.contains(value)){
			System.out.println("Invalid value");
			return;
		}

		this.value = value;
		set = true;
		Notify();

	}

	public boolean isSet(){ return this.set; }

	public ArrayList<String> getPossibleValues(){ return this.possibleValues; }

	@Override
	public void Attach(Observer o) {
		observers.add(o);
	}

	@Override
	public void Detach(Observer o) {
		observers.remove(o);
	}

	@Override
	public void Notify() throws Exception {
		for(Observer o : observers){
			o.Update(this);
		}
	}

	@Override
	public String toString(){
		return value;
	}

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
}