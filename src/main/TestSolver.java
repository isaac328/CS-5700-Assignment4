package main;

import java.io.File;
import java.io.FileInputStream;

public class TestSolver {
	public static void main(String[] args){
		try{
			Game game = new Game(new FileInputStream(new File("puzzle1.txt")));
			game.printPuzzle();
			Game solution = Solver.solve(game, 20);
			System.out.println("");
			solution.printPuzzle();
		}catch (Exception ex){ex.printStackTrace();}
	}
}
