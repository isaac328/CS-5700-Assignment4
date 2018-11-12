package main;

import java.util.HashSet;

public class Row implements Observer{
	Cell[] cells;
	HashSet<String> usedValues;

	public Row(Cell[] cells) throws Exception{
		this.cells = cells;
		usedValues = new HashSet<>();
		for(Cell c : cells){
			c.Attach(this);
		}

		for(Cell c : cells){
			if(c.isSet()){
				usedValues.add(c.toString());
				for(Cell c2 : cells){
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

			for(Cell otherCells : cells)
				otherCells.removePossibleValue(c.toString());
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
