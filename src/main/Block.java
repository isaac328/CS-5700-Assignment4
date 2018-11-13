package main;

import java.util.ArrayList;
import java.util.HashSet;

public class Block implements Observer, Subject{
	private Cell[][] cells;
	private HashSet<String> usedValues;
	private ArrayList<Observer> observers;

	public Block(Cell[][] cells) throws Exception{
		this.cells = cells;
		usedValues = new HashSet<>();
		observers = new ArrayList<>();
		for(int i = 0; i < cells.length; i++){
			for(int j = 0; j < cells.length; j++){
				cells[i][j].Attach(this);
			}
		}

		for(int i = 0; i < cells.length; i++){
			for(int j = 0; j < cells.length; j++){
				if(cells[i][j].isSet()){
					Notify();
					usedValues.add(cells[i][j].toString());
					for(int x = 0; x < cells.length; x++){
						for(int y = 0; y < cells.length; y++){
							cells[x][y].removePossibleValue(cells[i][j].toString());
						}
					}
				}
			}
		}
	}

	public Cell[][] getCells(){
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

			for(int i = 0; i < cells.length; i++){
				for(int j = 0; j < cells.length; j++){
					cells[i][j].removePossibleValue(c.toString());
				}
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
		for(Observer o : observers)
			o.Update(this);
	}
	
}