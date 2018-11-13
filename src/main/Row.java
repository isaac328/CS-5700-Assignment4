package main;

import java.util.ArrayList;
import java.util.HashSet;

public class Row implements Observer, Subject{
	private Cell[] cells;
	private HashSet<String> usedValues;
	private ArrayList<Observer> observers;

	public Row(Cell[] cells) throws Exception{
		this.cells = cells;
		observers = new ArrayList<>();
		usedValues = new HashSet<>();
		for(Cell c : cells){
			c.Attach(this);
		}

		for(Cell c : cells){
			if(c.isSet()){
				Notify();
				usedValues.add(c.toString());
				for(Cell c2 : cells){
					if(c2.isSet()) continue;
					c2.removePossibleValue(c.toString());
				}
			}
		}
	}

	public Cell[] getCells(){
		return this.cells;
	}

	@Override
	public void Update(Object obj) throws Exception {
		if(obj instanceof Cell){
			Cell c = (Cell)obj;

			if(usedValues.contains(c.toString())){
				throw new Exception("Invalid Move");
			}
			usedValues.add(c.toString());
			Notify();

			for(Cell otherCells : cells){
				otherCells.removePossibleValue(c.toString());
			}
		}
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
	public void Notify() throws Exception {
		for(Observer o : observers){
			o.Update(this);
		}
	}


	@Override
	public String toString(){
		StringBuilder s = new StringBuilder();
		for(Cell c : cells)
			s.append(c.toString());
		return s.toString();
	}
}
