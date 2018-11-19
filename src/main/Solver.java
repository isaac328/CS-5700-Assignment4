package main;

import main.exceptions.BadPuzzleException;
import main.exceptions.InvalidSizeException;
import main.exceptions.InvalidSymbolException;
import main.techniques.*;

import java.io.*;
import java.util.Scanner;

import org.apache.commons.lang3.time.StopWatch;


public class Solver{

	private static int guess;
	private static StopWatch guessClock = new StopWatch();
	private static Game solvedGame;

	public static boolean solve(Game game) throws Exception{
		if(game.isSolved()){
			solvedGame = game;
			return true;
		}

		HiddenNumbers hiddenSingle = new HiddenSingle();
		HiddenNumbers hiddenDouble = new HiddenDouble();
		NakedNumbers nakedDouble = new NakedDouble();
		NakedNumbers nakedTriple = new NakedTriple();

		while( nakedDouble.execute(game) || nakedTriple.execute(game) || hiddenSingle.execute(game) || hiddenDouble.execute(game)){
			continue;
		}

		if(game.isSolved()){
			solvedGame = game;
			return true;
		}


		for(int i = 0; i < game.getSize(); i++){
			Cell[] cells = game.getRow(i).getCells();
			for(int j = 0; j < cells.length; j++){
				Cell c = cells[j];
				if(c.isSet()) continue;
				for(String s : c.getPossibleValues()){
					startGuessWatch();
					try{
						Game g2 = (Game)game.clone();
						g2.getRow(i).getCells()[j].setValue(s);
						guess += 1;
						try{guessClock.suspend();}catch (Exception ex){}
						boolean solution = solve(g2);
						if(solution){
							return true;
						}
					}
					catch(CloneNotSupportedException ex1){
						System.out.println("Cloning Error");
					}
					catch (Exception ex){
						ex.printStackTrace();
					}
					finally {
						if(!guessClock.isSuspended())
							guessClock.suspend();
					}
				}
				return false;
			}
		}
		return false;
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
				try{
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
								watch.start();
								game = new Game(new FileInputStream(new File(input[0])));
								watch.suspend();
								validInput = true;
							}
							break;
						case 2:
							writeToFile = true;
							outputFile = input[1];
							watch.start();
							game = new Game(new FileInputStream(new File(input[0])));
							watch.suspend();
							validInput = true;
							break;
						default:
							System.out.println("Invalid command");
					}
				}catch(InvalidSizeException ex){
					printError("Invalid: Incorrect Board Size", ex.getOriginalPuzzle(), writeToFile, outputFile);
					validInput = false;
				}
				catch(InvalidSymbolException ex){
					printError("Invalid: Invalid Symbol", ex.getOriginalPuzzle(), writeToFile, outputFile);
					validInput = false;
				}
				catch(BadPuzzleException ex){
					printError("Invalid: Bad Puzzle", ex.getOriginalPuzzle(), writeToFile, outputFile);
					validInput = false;
				}
				catch(Exception ex){
					System.out.println("Error");
				}
			}

			try{
				//reset variables
				guess = 0;
				HiddenNumbers.resetCounter();
				NakedNumbers.resetCounter();

				watch.resume();
				boolean solution = Solver.solve(game);
				watch.suspend();

				if(!solution)
					throw new Exception("Puzzle Could not be solved");

				if(writeToFile){
					try{
						PrintWriter writer = new PrintWriter(outputFile, "UTF-8");
						writer.println(game.getOriginalPuzzle());
						writer.println("Solution:");
						writer.println(solvedGame.toString());
						writer.println();
						writer.println(String.format("Strategy %20s %20s", "Uses", "Time"));
						writer.println(String.format("One Possibility %13s %40s", String.valueOf(game.getOnePossibility()),
								Cell.getTime()));
						writer.println(String.format("Naked Numbers %13s %40s", String.valueOf(NakedNumbers.getCounter()), "Time"));
						writer.println(String.format("Hidden Numbers %13s %40s", String.valueOf(HiddenNumbers.getCounter()),
								"Time"));
						writer.println(String.format("Guess %20s %40s", String.valueOf(guess), "Time"));
						writer.close();
					}catch (Exception ex2){ System.out.println(""); }
				}
				else{
					System.out.println(game.getOriginalPuzzle());
					System.out.println("Solution:");
					solvedGame.printPuzzle();
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
						writer.println("Invalid: Bad Puzzle");
						writer.close();
					}catch (Exception ex2){System.out.println("Invalid: Bad Puzzle");}
				}
				else{
					System.out.println(game.getOriginalPuzzle());
					System.out.println();
					game.printPuzzle();
					System.out.println("Invalid: Bad Puzzle");
				}
			}
		}
	}

	private static void printError(String message, String originalPuzzle, boolean writeToFile, String file){
		if(writeToFile){
			try{
				PrintWriter writer = new PrintWriter(file, "UTF-8");
				writer.println(originalPuzzle);
				writer.println();
				writer.println(message);
			}catch(Exception ex1) {}
		}
		else{
			System.out.println(originalPuzzle);
			System.out.println();
			System.out.println(message);
		}
	}


	private static void startGuessWatch(){
		try{
			if(guessClock.isSuspended())
				guessClock.resume();
			else
				guessClock.start();
		}catch (Exception ex){ System.out.println("Error starting watch"); }
	}

	public static String getGuessTime(){
		try{
			return guessClock.toString();
		}catch (Exception ex){ ex.printStackTrace(); }
		return "Guess clock error";
	}
}
