package main.techniques;

import main.Cell;

import java.util.HashMap;

public class HiddenDouble extends HiddenNumbers {

	@Override
	public boolean findHidden(Cell[] cells, HashMap<String, Integer> freq) throws Exception {
		boolean changes = false;
		for(int i = 0; i < cells.length; i++){
			Cell c = cells[i];
			//skip cells that are already set
			if(!c.isSet()){
				String val1 = null;
				//get the cells possible values
				for(int k = 0; k < c.getPossibleValues().size(); k++){
					String s = c.getPossibleValues().get(k);
					//if the possible value has only occurred twice in the house
					if(freq.get(s) == 2){
						//if a first value hasn't been found yet
						//make this the first value
						if(val1 == null){
							val1 = s;
							continue;
						}
						//if no then we found two values that occur only twice
						else{
							//set the second value
							String val2 = s;
							//search the cells for the other place these two values possible occur
							for(Cell c1 : cells){
								//make sure the new cell is not set and is not equal to the current cell
								if(!c1.isSet() && !c1.equals(c)){
									//if the new cell contains both of the possible values, then we have found a hidden double
									if(c1.getPossibleValues().contains(val1) && c1.getPossibleValues().contains(val2)){

										//go through all the candidates of Cell c1
										for(int j = 0; j < c1.getPossibleValues().size(); j++){
											String s1 = c1.getPossibleValues().get(j);
											//remove all possibilities other than val1 and val2
											if(!s1.equals(val1) && !s1.equals(val2)){
												c1.removePossibleValue(s1);
												changes = true;
											}
										}

										//go through all the candidates of Cell c
										for(int j = 0; j < c.getPossibleValues().size(); j++){
											String s1 = c.getPossibleValues().get(j);
											//remove all possibilities other than val1 and val2
											if(!s1.equals(val1) && !s1.equals(val2)){
												c.removePossibleValue(s1);
												changes = true;
											}
										}
										break;
									}
								}
							}
						}
					}
				}
			}
		}
		return changes;
	}

	@Override
	public void setCells(Cell[] cells, HashMap<String, Integer> freq) throws Exception {
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
