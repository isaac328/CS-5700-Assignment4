package main;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class Solver {

	public static void checkNakedPair(Game game) throws Exception{
		for(int row = 0; row < game.getSize(); row++){
			Cell[] currentRow = game.getRow(row).getCells();
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
			Cell[] currentColumn = game.getColumn(column).getCells();
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


	public static void checkNakedTriple(Game game) throws Exception{
		//start with rows
		for(int row = 0; row < game.getSize(); row++){

			Cell[] currentRow = game.getRow(row).getCells();
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
	}


	public static void checkHiddenSingle(Game game) throws Exception{

		// check rows first
		for(int row = 0; row < game.getSize(); row++){
			//frequency list to keep track of how many times a possibility has occurred
			HashMap<String, Integer> freq = new HashMap<>(2*game.getSize());
			//get current row
			Cell[] currentRow = game.getRow(row).getCells();

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
			Cell[] currentColumn = game.getColumn(column).getCells();

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
		int size = game.getSize();
		int blockSize = game.getBlockSize();

		//go through every block
		for(int blockX = 0; blockX < size/blockSize; blockX++){
			for(int blockY = 0; blockY < size/blockSize; blockY++){
				//frequency list to keep track of how many times a possibility has occurred
				HashMap<String, Integer> freq = new HashMap<>(2*game.getSize());

				Cell[][] block = game.getBlock(blockX, blockY).getCells();
				//go through all the cells in this block
				for(int i = 0; i < blockSize; i++){
					for(int j = 0; j < blockSize; j++){
						// skip the cell if it is already set
						if(!block[i][j].isSet()){
							//get the current cell
							Cell c = block[i][j];
							//increment the frequency count of every possible value of c
							for(String s : c.getPossibleValues()){
								int count = freq.containsKey(s) ? freq.get(s) : 0;
								freq.put(s, count + 1);
							}
						}
					}
				}

				//go through every cell in this block again
				for(int i = 0; i < blockSize; i++){
					for(int j = 0; j < blockSize; j++){
						//skip cells that are already set
						if(!block[i][j].isSet()){
							//get the current cell
							Cell c = block[i][j];
							//go through all possible values for this cell
							for(String s : c.getPossibleValues()){
								//if the possible value has only occurred once in the block, then this cell must be the value
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
									j = 0;
									break;
								}
							}
						}
					}
				}
			}
		}
	}

	public static void guess(Game game) throws Exception{
		Random rand = new Random();
		int randomOrientation = rand.nextInt(3);
		int randomElement = rand.nextInt(game.getSize());

		Cell[] cells = new Cell[game.getSize()];
		switch(randomOrientation){
			case 0:
				cells = game.getRow(randomElement).getCells();
				break;
			case 1:
				cells = game.getColumn(randomElement).getCells();
				break;
			case 3:
				Cell[][] cell2 = game.getBlock(randomElement % game.getBlockSize(), Math.floorDiv(randomElement,
						game.getBlockSize())).getCells();
				for(int i = 0; i < game.getBlockSize(); i++){
					for(int j = 0; j < game.getBlockSize(); j++){
						cells[i*game.getBlockSize() + j] = cell2[j][i];
					}
				}
				break;
		}
		Arrays.sort(cells);
		cells = Arrays.stream(cells).filter(x -> !x.isSet()).toArray(Cell[]::new);

		//the lucky winner is the cell with the least number of possible values (thus the highest chance of guessing correctly)
		Cell luckyWinner = cells[0];
		luckyWinner.setValue(luckyWinner.getPossibleValues().get(rand.nextInt(luckyWinner.getPossibleValues().size())));
	}

	public static Game solve(Game game, int counter) throws Exception{
		if(counter < 0) return game;

		if(game.isSolved()) return game;

		checkNakedPair(game);
		if(game.isSolved()) return game;

		checkHiddenSingle(game);
		if(game.isSolved()) return game;


		for(int i = 0; i < game.getSize(); i++){
			Cell[] cells = game.getRow(i).getCells();
			Arrays.sort(cells);
			for(int j = 0; j < cells.length; j++){
				Cell c = cells[j];
				if(c.isSet()) continue;
				for(String s : c.getPossibleValues()){
					try{
						Game g2 = (Game)game.clone();
						g2.getRow(i).getCells()[j].setValue(s);
						Game solution = solve(g2, counter-1);
						if(solution != null)
							return solution;
					}
					catch (Exception ex){ continue; }

				}
			}
		}

//		while(!game.isSolved()){
//			try{
//				Game g2 = (Game)game.clone();
//				guess(g2);
//				Game solution = solve(g2, counter-1);
//				if(solution != null)
//					return solution;
//			}
//			catch(Exception ex){ continue; }
//		}

		return null;
	}




	public static void main(String[] args){
		try{
			Game game = new Game(new FileInputStream(new File("puzzle11-0301.txt")));
			//System.out.println(game.getRemainingValues());
//			Game game2 = (Game) game.clone();
//			System.out.println(game.getRemainingValues());
//			//solver.guess();
//			checkHiddenSingle(game);
//			System.out.println(game.getRemainingValues());
//
//			checkNakedPair(game);
//			System.out.println(game.getRemainingValues());
//
//			checkHiddenSingle(game);
//			System.out.println(game.getRemainingValues());
//
//			checkNakedPair(game);
//			System.out.println(game.getRemainingValues());
//
//			checkHiddenSingle(game);
//			System.out.println(game.getRemainingValues());
//
//			game.printPuzzle();
//			System.out.println("");
			//game2.printPuzzle();
			//System.out.println(game2.getRemainingValues());

			game.printPuzzle();
			Game solution = Solver.solve(game, 10);
			System.out.println("");
			solution.printPuzzle();

		}
		catch (Exception ex) { ex.printStackTrace();}
	}

}
