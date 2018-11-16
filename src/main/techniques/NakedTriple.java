package main.techniques;

import main.Cell;

import java.util.HashMap;

public class NakedTriple extends NakedNumbers {
	@Override
	public boolean findNumbers(Cell[] cells, HashMap<String, Integer> freq) throws Exception {
		boolean changed = false;
		// go through all the cells
		for(int i = 0; i < cells.length; i++){
			Cell c = cells[i];

			if(!c.isSet()){

				if(freq.get(c.getPossibleValues().toString()) == 3 && c.getPossibleValues().size() == 3)
				{
					//try to find cell 2
					for(int j = i+1; j < cells.length; j++){
						Cell c1 = cells[j];
						if(c1.isSet())
							continue;

						if(c1.getPossibleValues().equals(c.getPossibleValues())){
							//find cell 3
							for(int k = j+1; k < cells.length; k++){
								Cell c2 = cells[k];
								if(c2.isSet())
									continue;

								//if we found all 3 cells
								if(c2.getPossibleValues().equals(c.getPossibleValues())){
									//go through all the cells again
									for(Cell c3 : cells) {
										//skip our three cells that are the same
										if (c3.equals(c) || c3.equals(c1) || c3.equals(c2) || c3.isSet())
											continue;

										//store the old list
										String oldList = c3.getPossibleValues().toString();

										//delete the values from all other cells
										for (String value : c.getPossibleValues()) {
											if (c3.getPossibleValues().contains(value)) {
												changed = true;
												c3.removePossibleValue(value);
											}
										}

										//remove the old list from the frequency counter
										String newString = c3.getPossibleValues().toString();
										freq.put(oldList, freq.get(oldList) - 1);

										//add the new list to the frequency counter
										int count = freq.containsKey(newString) ? freq.get(newString) : 0;
										freq.put(newString, count + 1);
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
}
