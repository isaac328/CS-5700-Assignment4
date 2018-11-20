package main;

import java.util.HashSet;

public class Row extends House{

	public Row(Cell[] cells) throws Exception{
		super(cells);
		//attach this column to every cell
		for(Cell c : cells){
			c.Attach(this);
		}

		//go through all the cells again
		for(Cell c : cells){
			//if the cell is set, make sure it hasn't occurred before
			if(c.isSet()){
//				if(usedValues.contains(c.toString()))
//					throw new Exception("Invalid: Unsolvable");

				//add it to the list of used values and remove possibilities from other cells
				getUsedValues().add(c.toString());
				for(Cell c2 : cells){
					c2.removePossibleValue(c.toString());
				}
			}
		}
	}

	/**
	 * to String
	 * @return string representation of this row
	 */
	@Override
	public String toString(){
		StringBuilder s = new StringBuilder();
		for(Cell c : cells)
			s.append(c.toString() + " ");
		return s.toString();
	}
}
