package main;

import main.techniques.*;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Solver{

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
						game.getBlockSize())).get2DCells();
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

		HiddenNumbers hiddenSingle = new HiddenSingle();
		HiddenNumbers hiddenDouble = new HiddenDouble();
		NakedNumbers nakedDouble = new NakedDouble();
		NakedNumbers nakedTriple = new NakedTriple();

		while(nakedDouble.execute(game) || nakedTriple.execute(game) || hiddenSingle.execute(game) || hiddenDouble.execute(game)){
			continue;
		}

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

		return null;
	}




	public static void main(String[] args){
		System.out.println("Welcome to the Sudoku Solver!\n");
		System.out.println("Valid inputs:");
		System.out.println(String.format("-h  %53s\n<input file name> %80s\n" +
						"<input file name> <output file name> %63s\nexit %56s\n",
				"Help message",
				"Takes a puzzle and displays the answer in the console",
				"Reads a puzzle and writes the answer in the output file", "Exit the program"));

		while(true){
			Game game = null;
			boolean writeToFile = false;
			String outputFile = null;

			Scanner in = new Scanner(System.in);

			boolean validInput = false;
			while(!validInput){
				System.out.print("[Solve] ");
				String[] input = in.nextLine().split("\\s");
				System.out.println();


				switch(input.length){
					case 1:
						if(input[0].equals("-h")){
							System.out.println("Valid inputs:");
							System.out.println(String.format("-h  %53s\n<input file name> %80s\n" +
											"<input file name> <output file name> %63s\nexit %56s\n",
									"Help message",
									"Takes a puzzle and displays the answer in the console",
									"Reads a puzzle and writes the answer in the output file", "Exit the program"));
						}
						else if(input[0].equals("exit")) System.exit(1);
						else{
							try{
								game = new Game(new FileInputStream(new File(input[0])));
								validInput = true;
							}catch (Exception ex){System.out.println("Invalid Input File");}
						}
						break;
					case 2:
						try{
							game = new Game(new FileInputStream(new File(input[0])));
							outputFile = input[1];
							writeToFile = true;
							validInput = true;
						}catch (Exception ex){System.out.println("Invalid Input File");}
						break;
					default:
						System.out.println("Invalid command");
				}
			}

			try{
				Game solution = Solver.solve(game, 20);
				if(writeToFile){
					try{
						PrintWriter writer = new PrintWriter(outputFile, "UTF-8");
						writer.println(solution.toString());
						writer.println("");
						writer.close();
					}catch (Exception ex2){System.out.println("");}
				}
				else{
					solution.printPuzzle();
					System.out.println("");
				}
			}catch (Exception ex){
				if(writeToFile){
					try{
						PrintWriter writer = new PrintWriter(outputFile, "UTF-8");
						writer.println(game.toString());
						writer.println("Puzzle Could not be solved");
						writer.close();
					}catch (Exception ex2){System.out.println("Puzzle Could" +
							" not be solved");}
				}
				else{
					game.printPuzzle();
					System.out.println("Puzzle Could" +
							" not be solved");
				}
			}
		}
	}
}
