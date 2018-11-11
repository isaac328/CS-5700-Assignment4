package main;

import java.io.File;
import java.io.FileInputStream;

public class Solver {

	Game game;

	public Solver(Game game){
		this.game = game;
	}

	public void solve(){
		try{
			for(int i = 0; i < game.getSize(); i++){
				for(Cell c : game.getRow(i)){
					if(c.isSet()){
						for(Cell c2 : game.getRow(i)){
							c2.removePossibleValue(c.toString());
						}
					}
				}
			}

			for(int i = 0; i < game.getSize(); i++){
				for(Cell c : game.getColumn(i)){
					if(c.isSet()){
						for(Cell c2 : game.getColumn(i)){
							c2.removePossibleValue(c.toString());
						}
					}
				}
			}
		}
		catch (Exception ex) { System.out.println(ex.toString()); }
	}


	public static void main(String[] args){
		try{
			Game game = new Game(new FileInputStream(new File("puzzle5.txt")));
			Solver solver = new Solver(game);

			solver.solve();
			game.printPuzzle();
		}
		catch (Exception ex) { System.out.print(ex.toString());}
	}

}
