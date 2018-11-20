package main;

import java.util.HashSet;

public class Block extends House{

	Cell[][] cells;

	public Block(Cell[][] cells) throws Exception{
		super(cells);
		this.cells = cells;
		for(int i = 0; i < cells.length; i++){
			for(int j = 0; j < cells.length; j++){
				cells[i][j].Attach(this);
			}
		}

		for(int i = 0; i < cells.length; i++){
			for(int j = 0; j < cells.length; j++){
				if(cells[i][j].isSet()){
					getUsedValues().add(cells[i][j].toString());
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
}