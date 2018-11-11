package main;

import java.io.File;
import java.io.FileInputStream;

public class Solver {

	Game game;

	public Solver(Game game){
		this.game = game;
	}

	public void checkLockedSets(){
		try{
			for(int row = 0; row < game.getSize(); row++){
				Cell[] currentRow = game.getRow(row);
				for(Cell c : currentRow){
					if(!c.isSet()){
						for(Cell c1 : currentRow){
							if(c1.equals(c) || c1.isSet())
								continue;

							if(c1.getPossibleValues().equals(c.getPossibleValues())){
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



	public static void main(String[] args){
		try{
			Game game = new Game(new FileInputStream(new File("puzzle3.txt")));
			Solver solver = new Solver(game);
			solver.checkLockedSets();

			game.printPuzzle();
		}
		catch (Exception ex) { System.out.print(ex.toString());}
	}

}
