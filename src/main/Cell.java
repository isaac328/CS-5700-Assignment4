package main;

import java.util.ArrayList;

public class Cell implements Subject{
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

	public void removePossibleValue(String value){
		possibleValues.remove(value);
		if(possibleValues.size() == 1){
			this.value = possibleValues.get(0);
			this.set = true;
			Notify();
		}
	}

	public boolean isSet(){ return this.set; }

	public int getxCoord(){ return this.xCoord; }

	public int getyCoord(){ return this.yCoord; }


	@Override
	public String toString(){
		return value;
	}

	@Override
	public void Attach(Observer o) {
		observers.add(o);
	}

	@Override
	public void Detach(Observer o) {
		observers.remove(o);
	}

	@Override
	public void Notify() {
		for(Observer o : observers){
			o.Update(this);
		}
	}
}