package main;

import main.exceptions.BadPuzzleException;
import main.exceptions.InvalidSizeException;
import main.exceptions.InvalidSymbolException;
import main.exceptions.MultipleSolutionException;
import main.techniques.*;

import java.io.*;
import java.util.Scanner;

import org.apache.commons.lang3.time.StopWatch;


public class Solver{
	//number of guesses taken
	private static int guess;
	//clock for keeping track of guessing time
	private static StopWatch guessClock = new StopWatch();
	//solved game
	private static Game solvedGame = null;
	//extra game if there are multiple solutions
	private static Game extraGame = null;

	/**
	 * Recursive solving algorithm
 	 * @param game the current game
	 * @return if the game has been solved
	 * @throws Exception
	 */
	public static boolean solve(Game game) throws Exception{
		//if the game is solved
		if(game.isSolved()){
			//if a solved game has not been found this is the solution
			if(solvedGame == null)
				solvedGame = game;
			//if a solved game has already been found, we have multiple solutions
			else{
				extraGame = game;
				throw new MultipleSolutionException("Invalid: Multiple Solutions", game.getOriginalPuzzle());
			}
			return true;
		}

		//Algorithms for narrowing down cells
		HiddenNumbers hiddenSingle = new HiddenSingle();
		HiddenNumbers hiddenDouble = new HiddenDouble();
		NakedNumbers nakedDouble = new NakedDouble();
		NakedNumbers nakedTriple = new NakedTriple();

		//Continue to execute the algorithms while they find solutions
		while( nakedDouble.execute(game) || nakedTriple.execute(game) || hiddenSingle.execute(game) || hiddenDouble.execute(game)){
			continue;
		}

		//if the game has been solved, do the same as above
		if(game.isSolved()){
			if(solvedGame == null)
				solvedGame = game;
			else{
				extraGame = game;
				throw new MultipleSolutionException("Invalid: Multiple Solutions", game.getOriginalPuzzle());
			}
			return true;
		}


		//If the other algorithms have not found a solution, its time to start guessing
		//go through every row
		for(int i = 0; i < game.getSize(); i++){
			Cell[] cells = game.getRow(i).getCells();
			//go through every cell
			for(int j = 0; j < cells.length; j++){
				Cell c = cells[j];
				if(c.isSet()) continue;
				//keeps track of whether a solution has been found
				boolean previouslySolved = false;
				//go through every possible value for this cell
				for(String s : c.getPossibleValues()){
					//start the guess watch
					startGuessWatch();
					try{
						//clone the game and add a guess to the counter
						Game g2 = (Game)game.clone();
						guess += 1;
						//set the guess
						g2.getRow(i).getCells()[j].setValue(s);
						//stop the guess clock
						try{guessClock.suspend();}catch (Exception ex){}

						//try to solve this new puzzle
						boolean solution = solve(g2);

						//if a solution has been found to this puzzle, set the flag
						//keep going so we can check for multiple solutions
						if(solution)
							previouslySolved = true;
					}
					catch(CloneNotSupportedException ex1){
						System.out.println("Cloning Error");
					}
					catch(MultipleSolutionException ex2){
						throw new MultipleSolutionException(ex2.getMessage(), ex2.getOriginalPuzzle());
					}
					catch (Exception ex){
					}
					finally {
						if(!guessClock.isSuspended())
							guessClock.suspend();
					}
				}
				return previouslySolved;
			}
		}
		//if we guess every possible value and there's still not a solution then there must be no solution
		return false;
	}

	/**
	 * Main method for the program
	 * @param args
	 */
	public static void main(String[] args){
		//set stopwatch
		StopWatch watch = new StopWatch();
		//opening prompt and info
		System.out.println("Welcome to the Sudoku Solver!\n");
		System.out.println("Valid inputs:");
		System.out.println(String.format("-h  %53s\n<input file name> %80s\n" +
						"<input file name> <output file name> %63s\nexit %56s\n",
				"Help message",
				"Takes a puzzle and displays the answer in the console",
				"Reads a puzzle and writes the answer in the output file", "Exit the program"));

		// start the main loop of the program
		while(true){
			//reset the watch for the program
			watch.reset();
			//initial variables
			Game game = null;
			boolean writeToFile = false;
			String outputFile = null;

			Scanner in = new Scanner(System.in);
			/***************************************
			 *
			 * Read in and Create the puzzle
			 *
			 ***************************************/
			//keep looping until we get valid input
			boolean validInput = false;
			while(!validInput){
				try{
					watch.reset();
					//get request
					System.out.print("[Solve] ");
					String[] input = in.nextLine().split("\\s");
					System.out.println();

					//switch for request length
					switch(input.length){
						//one item means solve in console
						case 1:
							//help
							if(input[0].equals("-h")){
								System.out.println("Valid inputs:");
								System.out.println(String.format("-h  %53s\n<input file name> %80s\n" +
												"<input file name> <output file name> %63s\nexit %56s\n",
										"Help message",
										"Takes a puzzle and displays the answer in the console",
										"Reads a puzzle and writes the answer in the output file", "Exit the program"));
							}
							//exit
							else if(input[0].equals("exit")) System.exit(1);
							//file request
							else{
								try{watch.start();}catch (IllegalStateException ex){}
								game = new Game(new FileInputStream(new File(input[0])));
								watch.suspend();
								validInput = true;
							}
							break;
						case 2:
							//if they want to write to a file
							writeToFile = true;
							outputFile = input[1];
							try{watch.start();}catch (IllegalStateException ex){}
							game = new Game(new FileInputStream(new File(input[0])));
							watch.suspend();
							validInput = true;
							break;
						default:
							System.out.println("Invalid command");
					}
				}catch(InvalidSizeException ex){
					printError(ex.getMessage(), ex.getOriginalPuzzle(), writeToFile, outputFile);
					validInput = false;
				}
				catch(InvalidSymbolException ex){
					printError(ex.getMessage(), ex.getOriginalPuzzle(), writeToFile, outputFile);
					validInput = false;
				}
				catch(BadPuzzleException ex){
					printError(ex.getMessage(), ex.getOriginalPuzzle(), writeToFile, outputFile);
					validInput = false;
				}
				catch(Exception ex){
					ex.printStackTrace();
					System.out.println("Error Reading Puzzle");
				}
				finally {
					if(watch.isStarted() && !watch.isSuspended())
						watch.suspend();
				}
			}


			/***********************************
			 *
			 * Start solving the puzzle
			 *
			 ***********************************/
			try{
				//reset variables
				guess = 0;
				solvedGame = null;
				extraGame = null;
				guessClock.reset();
				HiddenNumbers.resetCounter();
				NakedNumbers.resetCounter();

				//solve the puzzle
				watch.resume();
				boolean solution = Solver.solve(game);
				watch.suspend();

				//if there was no solution found, throw and error
				if(!solution)
					throw new Exception("Puzzle Could not be solved");

				//if they want to write to a file
				if(writeToFile){
					try{
						PrintWriter writer = new PrintWriter(outputFile, "UTF-8");
						writer.println(game.getOriginalPuzzle());
						writer.println("Solution:");
						writer.println(solvedGame.toString());
						writer.println();
						writer.println(String.format("Strategy %20s %20s", "Uses", "Time"));
						writer.println(String.format("One Possibility %13s %20s", String.valueOf(game.getOnePossibility()),
								Cell.getTime()));
						writer.println(String.format("Naked Numbers %15s %20s", String.valueOf(NakedNumbers.getCounter()), "Time"));
						writer.println(String.format("Hidden Numbers %14s %20s", String.valueOf(HiddenNumbers.getCounter()),
								"Time"));
						writer.println(String.format("Guess %23s %20s", String.valueOf(guess), "Time"));
						writer.close();
					}catch (Exception ex2){ System.out.println(""); }
				}
				//else print to console
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
			}
			//catch for multiple solutions
			catch(MultipleSolutionException ex1){
				if(writeToFile){
					try{
						PrintWriter writer = new PrintWriter(outputFile, "UTF-8");
						writer.println(game.getOriginalPuzzle() + "\n\n" + ex1.getMessage());
						writer.println(solvedGame.toString());
						writer.println(extraGame.toString());
						writer.close();
					}catch (Exception ex2){System.out.println("Print Writer Error");}
				}
				else{
					System.out.println(game.getOriginalPuzzle() + "\n\n" + ex1.getMessage());
					System.out.println(solvedGame.toString());
					System.out.println(extraGame.toString());
				}
			}
			//catch for any other exception
			catch (Exception ex){
				if(writeToFile){
					try{
						PrintWriter writer = new PrintWriter(outputFile, "UTF-8");
						writer.println(game.getOriginalPuzzle());
						writer.println("Invalid: Bad Puzzle");
						writer.close();
					}catch (Exception ex2){System.out.println("Invalid: Bad Puzzle");}
				}
				else{
					System.out.println(game.getOriginalPuzzle());
					System.out.println();
					System.out.println("Invalid: Bad Puzzle");
				}
			}
		}
	}

	/**
	 * Prints error messages
	 * @param message error message
	 * @param originalPuzzle the original puzzle
	 * @param writeToFile if this should be written to a file
	 * @param file the file to write to
	 */
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
				if(!guessClock.isStarted())
					guessClock.start();
		}catch (Exception ex){ }
	}

	public static String getGuessTime(){
		try{
			return guessClock.toString();
		}catch (Exception ex){ ex.printStackTrace(); }
		return "Guess clock error";
	}
}
