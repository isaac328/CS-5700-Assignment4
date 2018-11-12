package main;

import java.util.HashSet;

public class Block implements Observer{
	Cell[][] cells;
	HashSet<String> usedValues;

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

			for(int i = 0; i < cells.length; i++){
				for(int j = 0; j < cells.length; j++){
					cells[i][j].removePossibleValue(c.toString());
				}
			}
		}
	}
	
}