package main.techniques;

import main.Cell;
import main.Game;

import java.util.HashMap;

public abstract class NakedNumbers {
	public final boolean execute(Game game) throws Exception{
		boolean changed = false;
		for(int row = 0; row < game.getSize(); row++){
			Cell[] currentRow = game.getRow(row).getCells();

			HashMap<String, Integer> freq = setup(currentRow);

			changed = false;
			for(Cell c : currentRow){
				if(!c.isSet()){
					for(Cell c1 : currentRow){
						if(c1.equals(c) || c1.isSet())
							continue;

						if(c1.getPossibleValues().equals(c.getPossibleValues()) && c1.getPossibleValues().size() == 2){
							for(Cell c2 : currentRow){

								if(c2.equals(c) || c2.equals(c1) || c2.isSet()) continue;

								for(String value : c.getPossibleValues()){
									if(c2.getPossibleValues().contains(value)){
										changed = true;
										c2.removePossibleValue(value);
									}
								}
							}
						}
					}
				}
			}
		}
		return changed;
	}

	public HashMap<String, Integer> setup(Cell[] cells){
		HashMap<String, Integer> freq = new HashMap<>(2*cells.length);

		//go through the current Column
		for(Cell c : cells){
			//skip cells that are already set
			if(!c.isSet()){
				String s = c.getPossibleValues().toString();
				int count = freq.containsKey(s) ? freq.get(s) : 0;
				freq.put(s, count + 1);
			}
		}
		return freq;
	}

	public abstract boolean findNumbers(Cell[] cells, HashMap<String, Integer> freq) throws Exception;
}
