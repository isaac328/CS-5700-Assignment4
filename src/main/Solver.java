package main;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.HashMap;

public class Solver {

	Game game;

	public Solver(Game game){
		this.game = game;
	}

	public void checkNakedPair(){
		try{
			for(int row = 0; row < game.getSize(); row++){
				Cell[] currentRow = game.getRow(row);
				for(Cell c : currentRow){
					if(!c.isSet()){
						for(Cell c1 : currentRow){
							if(c1.equals(c) || c1.isSet())
								continue;

							if(c1.getPossibleValues().equals(c.getPossibleValues()) && c1.getPossibleValues().size() == 2){
								for(Cell c2 : currentRow){

									if(c2.equals(c) || c2.equals(c1) || c2.isSet()) continue;

									for(String value : c.getPossibleValues()){
										c2.removePossibleValue(value);
									}
								}
							}
						}
					}
				}
			}

			for(int column = 0; column < game.getSize(); column++){
				Cell[] currentColumn = game.getColumn(column);
				for(Cell c : currentColumn){
					if(!c.isSet()){
						for(Cell c1 : currentColumn){
							if(c1.equals(c) || c1.isSet())
								continue;

							if(c1.getPossibleValues().equals(c.getPossibleValues()) && c1.getPossibleValues().size() == 2){
								for(Cell c2 : currentColumn){

									if(c2.equals(c) || c2.equals(c1) || c2.isSet()) continue;

									for(String value : c.getPossibleValues()){
										c2.removePossibleValue(value);
									}
								}
							}
						}
					}
				}
			}
		}
		catch(Exception ex) { System.out.print(ex.toString());}
	}

	public void checkNakedTriple(){
		try{
			//start with rows
			for(int row = 0; row < game.getSize(); row++){

				Cell[] currentRow = game.getRow(row);
				Arrays.sort(currentRow);
				currentRow = Arrays.stream(currentRow).filter(x -> !x.isSet()).toArray(Cell[]::new);

				HashMap<String, Integer> freq = new HashMap<>(currentRow.length *2);

				for(Cell c : currentRow){
					int count = freq.containsKey(c.getPossibleValues().toString()) ?
							freq.get(c.getPossibleValues().toString()) : 0;
					freq.put(c.getPossibleValues().toString(), count + 1);
				}

				for(String s : freq.keySet()){
					if(freq.get(s) == 3){
						for(Cell c : currentRow){
							if(!c.getPossibleValues().toString().equals(s)){

							}
						}
					}
				}

			}
		}catch(Exception ex){ System.out.println(ex.toString());}
	}


	public void checkHiddenSingle(){
		try{
			// check rows first
			for(int row = 0; row < game.getSize(); row++){
				//frequency list to keep track of how many times a possibility has occurred
				HashMap<String, Integer> freq = new HashMap<>(2*game.getSize());
				//get current row
				Cell[] currentRow = game.getRow(row);

				//go through the current row
				for(Cell c : currentRow){
					//skip cells that are already set
					if(!c.isSet()){
						//set the frequency of each possibility
						for(String s : c.getPossibleValues()){
							int count = freq.containsKey(s) ? freq.get(s) : 0;
							freq.put(s, count + 1);
						}
					}
				}

				//go back through the current row
				for(int i = 0; i < currentRow.length; i++){
					//get the current cell
					Cell c = currentRow[i];
					//skip cells that are already set
					if(!c.isSet()){
						//get the cells possible values
						for(String s : c.getPossibleValues()){
							//if the possible value has only occurred once in the row, then this cell must be the value
							if(freq.get(s) == 1){
								//set it
								c.setValue(s);
								//subtract this cells possibilities from the rest of the frequency list since its now set
								for(String s1 : c.getPossibleValues()){
									int count = freq.get(s1);
									freq.put(s1, count - 1);
								}
								//start over
								i = 0;
								break;
							}
						}
					}
				}
			}

			// check columns second
			for(int column = 0; column < game.getSize(); column++){
				//frequency list to keep track of how many times a possibility has occurred
				HashMap<String, Integer> freq = new HashMap<>(2*game.getSize());
				//get current column
				Cell[] currentColumn = game.getColumn(column);

				//go through the current Column
				for(Cell c : currentColumn){
					//skip cells that are already set
					if(!c.isSet()){
						//set the frequency of each possibility
						for(String s : c.getPossibleValues()){
							int count = freq.containsKey(s) ? freq.get(s) : 0;
							freq.put(s, count + 1);
						}
					}
				}

				//go back through the current Column
				for(int i = 0; i < currentColumn.length; i++){
					//get the current cell
					Cell c = currentColumn[i];
					//skip cells that are already set
					if(!c.isSet()){
						//get the cells possible values
						for(String s : c.getPossibleValues()){
							//if the possible value has only occurred once in the column, then this cell must be the value
							if(freq.get(s) == 1){
								//set it
								c.setValue(s);
								//subtract this cells possibilities from the rest of the frequency list since its now set
								for(String s1 : c.getPossibleValues()){
									int count = freq.get(s1);
									freq.put(s1, count - 1);
								}
								//start over
								i = 0;
								break;
							}
						}
					}
				}
			}

			//go through blocks last
			//go through every block
//			int size = game.getSize();
//			int blockSize = game.getBlockSize();
//			Cell[][] board = game.getBoard();
//
//        	//go through every block
//			for(int blockX = 0; blockX < size/blockSize; blockX++){
//				for(int blockY = 0; blockY < size/blockSize; blockY++){
//					//frequency list to keep track of how many times a possibility has occurred
//					HashMap<String, Integer> freq = new HashMap<>(2*game.getSize());
//
//					//go through all the cells in this block
//					for(int i = 0; i < blockSize; i++){
//						for(int j = 0; j < blockSize; j++){
//							// skip the cell if it is already set
//							if(!board[(blockX * blockSize) + i][(blockY * blockSize) + j].isSet()){
//								//get the current cell
//								Cell c = board[(blockX * blockSize) + i][(blockY * blockSize) + j];
//								//increment the frequency count of every possible value of c
//								for(String s : c.getPossibleValues()){
//									int count = freq.containsKey(s) ? freq.get(s) : 0;
//									freq.put(s, count + 1);
//								}
//							}
//						}
//					}
//
//					//go through every cell in this block again
//					for(int i = 0; i < blockSize; i++){
//						for(int j = 0; j < blockSize; j++){
//							//skip cells that are already set
//							if(!board[(blockX * blockSize) + i][(blockY * blockSize) + j].isSet()){
//								//get the current cell
//								Cell c = board[(blockX * blockSize) + i][(blockY * blockSize) + j];
//								//go through all possible values for this cell
//								for(String s : c.getPossibleValues()){
//									//if the possible value has only occurred once in the block, then this cell must be the value
//									if(freq.get(s) == 1){
//										//set it
//										c.setValue(s);
//										//subtract this cells possibilities from the rest of the frequency list since its now set
//										for(String s1 : c.getPossibleValues()){
//											int count = freq.get(s1);
//											freq.put(s1, count - 1);
//										}
//										//start over
//										i = 0;
//										j = 0;
//										break;
//									}
//								}
//							}
//						}
//					}
//				}
//			}
		}
		catch(Exception ex){ System.out.println(ex.toString()); }
	}

	public void guess(){
		for(int i = 0; i < 1 && !game.isSolved(); i++){
			try{
				Cell[] row = game.getRow(i);
				Arrays.sort(row);
				row = Arrays.stream(row).filter(x -> !x.isSet()).toArray(Cell[]::new);

				for(Cell c : row){
					System.out.println(c.getPossibleValues().toString());
				}
			}
			catch(Exception ex) { System.out.print(ex.toString());}
		}
	}



	public static void main(String[] args){
		try{
			Game game = new Game(new FileInputStream(new File("puzzle3.txt")));
			Solver solver = new Solver(game);
			System.out.println(game.getUnsetValues());
			//solver.guess();
			solver.checkHiddenSingle();
			System.out.println(game.getUnsetValues());
//
			solver.checkNakedPair();
			System.out.println(game.getUnsetValues());
//
			solver.checkHiddenSingle();
			System.out.println(game.getUnsetValues());
//
//			solver.checkNakedPair();
//			System.out.println(game.getUnsetValues());

			game.printPuzzle();
		}
		catch (Exception ex) { System.out.print(ex.toString());}
	}

}
