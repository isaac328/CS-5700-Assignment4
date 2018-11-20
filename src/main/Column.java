package main;
import java.util.HashSet;

public class Column implements Observer {
	//cells in the column
	private Cell[] cells;
	//used values in the column
	private HashSet<String> usedValues;

	public Column(Cell[] cells) throws Exception{
		//set cells
		this.cells = cells;
		usedValues = new HashSet<>();
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
				usedValues.add(c.toString());
				for(Cell c2 : cells){
					c2.removePossibleValue(c.toString());
				}
			}
		}
	}


	/**
	 * getter for cells
	 * @return the cells
	 */
	public Cell[] getCells(){
		return this.cells;
	}

	/**
	 * Updates all the cells in this column when a new value is set
	 * @param c the cell
	 * @throws Exception
	 */
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
