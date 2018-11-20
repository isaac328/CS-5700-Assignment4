package main;

import java.util.HashSet;

public class Row implements Observer{
	//cells in this row
	private Cell[] cells;
	//all values that have been used in this row
	private HashSet<String> usedValues;

	public Row(Cell[] cells) throws Exception{
		this.cells = cells;
		usedValues = new HashSet<>();
		//attach this row to all the cells
		for(Cell c : cells){
			c.Attach(this);
		}

		//go through all the cells again
		for(Cell c : cells){
			if(c.isSet()){
				//make sure this value hasnt been used before
//				if(usedValues.contains(c.toString()))
//					throw new Exception("Invalid: Unsolvable");

				//add it to the list and remove from all other cells
				usedValues.add(c.toString());
				for(Cell c2 : cells){
					if(c2.isSet()) continue;
					c2.removePossibleValue(c.toString());
				}
			}
		}
	}

	/**
	 * getter for used values
	 * @return
	 */
	public int getUsedValues(){
		return this.usedValues.size();
	}

	/**
	 * getter for cells
	 * @return the cells
	 */
	public Cell[] getCells(){
		return this.cells;
	}

	/**
	 * Updates all the cells in this row when a cells value is set
	 * @param c the cell
	 * @throws Exception
	 */
	@Override
	public void Update(Cell c) throws Exception {

		//check if value has been used already
		if(usedValues.contains(c.toString())){
			throw new Exception("Invalid Move");
		}
		//add it to the used list
		usedValues.add(c.toString());

		//remove it from all other cells
		for(Cell otherCells : cells){
			otherCells.removePossibleValue(c.toString());
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
