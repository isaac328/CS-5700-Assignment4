package main;

import main.techniques.*;

import java.io.*;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import org.apache.commons.lang3.time.StopWatch;


public class Solver{

	private static int guess;
	private static StopWatch guessClock = new StopWatch();

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

		while( nakedDouble.execute(game) || nakedTriple.execute(game) || hiddenSingle.execute(game) || hiddenDouble.execute(game)){
			continue;
		}

		if(game.isSolved()) return game;


		getGuessTime();
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
						guess += 1;
						guessClock.suspend();
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
		StopWatch watch;
		System.out.println("Welcome to the Sudoku Solver!\n");
		System.out.println("Valid inputs:");
		System.out.println(String.format("-h  %53s\n<input file name> %80s\n" +
						"<input file name> <output file name> %63s\nexit %56s\n",
				"Help message",
				"Takes a puzzle and displays the answer in the console",
				"Reads a puzzle and writes the answer in the output file", "Exit the program"));

		while(true){
			watch = new StopWatch();
			Game game = null;
			boolean writeToFile = false;
			String outputFile = null;

			Scanner in = new Scanner(System.in);

			boolean validInput = false;
			while(!validInput){
				watch = new StopWatch();
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
								watch.start();
								game = new Game(new FileInputStream(new File(input[0])));
								watch.suspend();
								validInput = true;
							}catch (Exception ex){System.out.println("Invalid Input File");}
						}
						break;
					case 2:
						try{
							watch.start();
							game = new Game(new FileInputStream(new File(input[0])));
							watch.suspend();
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
				//reset variables
				guess = 0;
				HiddenNumbers.resetCounter();
				NakedNumbers.resetCounter();

				watch.resume();
				Game solution = Solver.solve(game, 20);
				watch.suspend();

				if(writeToFile){
					try{
						PrintWriter writer = new PrintWriter(outputFile, "UTF-8");
						writer.println(game.getOriginalPuzzle());
						writer.println("Solution:");
						writer.println(solution.toString());
						writer.println();
						writer.println(String.format("Strategy %20s %20s", "Uses", "Time"));
						writer.println(String.format("One Possibility %13s %40s", String.valueOf(game.getOnePossibility()),
								Cell.getTime()));
						writer.println(String.format("Naked Numbers %13s %40s", String.valueOf(NakedNumbers.getCounter()), "Time"));
						writer.println(String.format("Hidden Numbers %13s %40s", String.valueOf(HiddenNumbers.getCounter()),
								"Time"));
						writer.println(String.format("Guess %20s %40s", String.valueOf(guess), "Time"));
						writer.close();
					}catch (Exception ex2){System.out.println("");}
				}
				else{
					System.out.println(game.getOriginalPuzzle());
					System.out.println("Solution:");
					solution.printPuzzle();
					System.out.println();
					System.out.print(String.format("Total Time:  %s\n\n", watch.toString()));
					System.out.println(String.format("Strategy %20s %20s", "Uses", "Time"));
					System.out.println(String.format("One Possibility %13s %20s", String.valueOf(game.getOnePossibility()),
							Cell.getTime()));
					System.out.println(String.format("Naked Numbers %15s %20s", String.valueOf(NakedNumbers.getCounter()),
							NakedNumbers.getTime()));
					System.out.println(String.format("Hidden Numbers %14s %20s", String.valueOf(HiddenNumbers.getCounter()),
							HiddenNumbers.getTime()));
					System.out.println(String.format("Guess %23s %20s", String.valueOf(guess), Solver.getGuessTime()));
					System.out.println();
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

	private void startGuessWatch(){
		try{
			if(guessClock.isSuspended())
				guessClock.resume();
			else
				guessClock.start();
		}catch (Exception ex){ ex.printStackTrace(); }
	}

	public static String getGuessTime(){
		try{
			return guessClock.toString();
		}catch (Exception ex){ ex.printStackTrace(); }
		return "Error";
	}
}
