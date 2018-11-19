package main;

import java.util.HashSet;

public class Block implements Observer{
	private Cell[][] cells;
	private HashSet<String> usedValues;

	public Block(Cell[][] cells) throws Exception{
		this.cells = cells;
		usedValues = new HashSet<>();
		for(int i = 0; i < cells.length; i++){
			for(int j = 0; j < cells.length; j++){
				cells[i][j].Attach(this);
			}
		}

		for(int i = 0; i < cells.length; i++){
			for(int j = 0; j < cells.length; j++){
				if(cells[i][j].isSet()){
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

	public Cell[][] get2DCells(){
		return this.cells;
	}

	public Cell[] get1DCells(){
		Cell[] cells = new Cell[this.cells.length * this.cells.length];
		for(int i = 0; i < this.cells.length; i++){
			for(int j = 0; j < this.cells.length; j++){
				cells[i*this.cells.length + j] = this.cells[i][j];
			}
		}
		return cells;
	}

	@Override
	public void Update(Object obj) throws Exception {
		if(obj instanceof Cell){
			Cell c = (Cell)obj;

			if(usedValues.contains(c.toString())){
				throw new Exception("Invalid Move");
			}
			usedValues.add(c.toString());

			for(int i = 0; i < cells.length; i++){
				for(int j = 0; j < cells.length; j++){
					cells[i][j].removePossibleValue(c.toString());
				}
			}
		}
	}
}