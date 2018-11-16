package main.techniques;

import main.Cell;

import java.util.HashMap;

public class NakedDouble extends NakedNumbers {

	@Override
	public boolean findNumbers(Cell[] cells, HashMap<String, Integer> freq) throws Exception {
		boolean changed = false;
		for(int i = 0; i < cells.length; i++){
			Cell c = cells[i];

			if(!c.isSet()){
				if(freq.get(c.getPossibleValues().toString()) == 2 && c.getPossibleValues().size() == 2)
				{
					for(int j = i+1; j < cells.length; j++){
						Cell c1 = cells[j];
						if(c1.isSet())
							continue;

						if(c1.getPossibleValues().equals(c.getPossibleValues())){
							for(Cell c2 : cells){
								if(c2.equals(c) || c2.equals(c1) || c2.isSet()) continue;

								//store the old list
								String oldList = c2.getPossibleValues().toString();

								for(String value : c.getPossibleValues()){
									if(c2.getPossibleValues().contains(value)){
										changed = true;
										c2.removePossibleValue(value);
									}
								}

								//remove the old list from the frequency counter
								String newString = c2.getPossibleValues().toString();
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
		return changed;
	}
}
