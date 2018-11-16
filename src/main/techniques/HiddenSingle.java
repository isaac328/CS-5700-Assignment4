package main.techniques;

import main.Cell;

import java.util.HashMap;

public class HiddenSingle extends HiddenNumbers {

	@Override
	public boolean findHidden(Cell[] cells, HashMap<String, Integer> freq) throws Exception{
		boolean changes = false;
		for(int i = 0; i < cells.length; i++){
			Cell c = cells[i];
			//skip cells that are already set
			if(!c.isSet()){
				//get the cells possible values
				for(String s : c.getPossibleValues()){
					//if the possible value has only occurred once in the column, then this cell must be the value
					if(freq.get(s) == 1){
						changes = true;
						//set it
						c.setValue(s);
						//subtract this cells possibilities from the rest of the frequency list since its now set
						for(String s1 : c.getPossibleValues()){
							int count = freq.get(s1);
							freq.put(s1, count - 1);
						}
						i = 0;
						break;
					}
				}
			}
		}

		return changes;
	}

	@Override
	public void setCells(Cell[] cells, HashMap<String, Integer> freq) {
		//go through the current Column
		for(Cell c : cells){
			//skip cells that are already set
			if(!c.isSet()){
				//set the frequency of each possibility
				for(String s : c.getPossibleValues()){
					int count = freq.containsKey(s) ? freq.get(s) : 0;
					freq.put(s, count + 1);
				}
			}
		}
	}
}
