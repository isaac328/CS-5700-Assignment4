package main;

import java.util.HashSet;

public abstract class House implements Observer {
	Cell[] cells;
	private HashSet<String> usedValues;


	protected House(Cell[] cells){
		this.cells = cells;
		usedValues = new HashSet<>(2 * cells.length);
	}

	protected House(Cell[][] cells){
		this.cells = new Cell[cells.length * cells.length];
		for(int i = 0; i < cells.length; i++){
			for(int j = 0; j < cells.length; j++){
				this.cells[i*cells.length + j] = cells[i][j];
			}
		}
		usedValues = new HashSet<>(2 * cells.length);
	}

	public Cell[] getCells(){ return this.cells; }

	public HashSet<String> getUsedValues(){return this.usedValues;}

	public int getNumSetValues(){return this.usedValues.size();}


	@Override
	public void Update(Cell c) throws Exception {
		//make sure this value hasn't already been used in the column
		if(usedValues.contains(c.toString())){
			throw new Exception("Invalid Move");
		}
		//add it
		usedValues.add(c.toString());

		//remove from all other cells
		for(Cell otherCells : cells)
			otherCells.removePossibleValue(c.toString());
	}
}
