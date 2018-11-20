package main;

import main.exceptions.BadPuzzleException;
import main.exceptions.InvalidSizeException;
import main.exceptions.InvalidSymbolException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

public class Game implements Cloneable, Observer{
	//possible characters in this game
	private HashSet<String> characters;
	//rows of the game
	private Row[] rows;
	//columns of the game
	private Column[] columns;
	//blocks of the game
	private Block[][] blocks;
	//size of the game
	private int size;
	//the original, unmodified puzzle
	private ArrayList<String> originalPuzzle;

	//counter for the number of times one possibility was applied
	private int onePossibility;

	/**
	 * Constructor for a game
	 * @param in input stream to the game
	 * @throws Exception
	 */
	public Game(InputStream in) throws Exception
	{
		//initialize variables
		characters = new HashSet<>();
		originalPuzzle = new ArrayList<>();
		onePossibility = 0;

		//read in the game
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String str;
		while((str = reader.readLine()) != null){
			originalPuzzle.add(str);
		}
		in.close();
		reader.close();

		//set the size and blocksize variables
		size = Integer.parseInt(originalPuzzle.get(0));

		//get the characters
		String[] stringCharacters = originalPuzzle.get(1).split("\\s");
		for(String character : stringCharacters){
			characters.add(character);
		}

		//validate the puzzle
		validatePuzzle();

		//start creating the different houses
		rows = new Row[size];
		columns = new Column[size];
		blocks = new Block[size/getBlockSize()][size/getBlockSize()];

		//construct a two dimensional cell array first
		Cell[][] board = new Cell[size][size];

		// read all cells into the board array
		for(int j = 0; j < size; j++){
			String[] boardLine = originalPuzzle.get(j+2).split("\\s");
			for(int i = 0; i < size; i++){
				board[i][j] = new Cell(boardLine[i], stringCharacters, i, j);
				board[i][j].Attach(this);
			}
		}

		//create the individual rows and columns
		for(int i = 0; i < size; i++){
			Cell[] row = new Cell[size];
			Cell[] column = new Cell[size];
			for(int j = 0; j < size; j++){
				row[j] = board[j][i];
				column[j] = board[i][j];
			}
			Row r = new Row(row);
			Column c = new Column(column);

			rows[i] = r;
			columns[i] = c;
		}

		//create the blocks
		for(int blockX = 0; blockX < size/getBlockSize(); blockX++){
			for(int blockY = 0; blockY < size/getBlockSize(); blockY++){
				Cell[][] block = new Cell[getBlockSize()][getBlockSize()];
				for(int i = 0; i < getBlockSize(); i++){
					for(int j = 0; j < getBlockSize(); j++){
						block[i][j] = board[(blockX * getBlockSize()) + i][(blockY * getBlockSize()) + j];
						block[i][j].Attach(this);
					}
				}
				blocks[blockX][blockY] = new Block(block);
			}
		}
	}

	/**
	 * Getter for size of the game
	 * @return the size
	 */
	public int getSize(){
		return this.size;
	}

	/**
	 * Getter for blockSize of the game
	 * @return the blocksize
	 */
	public int getBlockSize() { return (int)Math.sqrt(this.size); }

	/**
	 * Get a certain row
	 * @param row the row to get (0 indexed)
	 * @return the row
	 * @throws Exception
	 */
	public Row getRow(int row) throws Exception{
		if(row >= size)
			throw new Exception("Index out of bounds");

		return rows[row];
	}

	/**
	 * Get a certain column
	 * @param column the column to get (0 indexed)
	 * @return the column
	 * @throws Exception
	 */
	public Column getColumn(int column) throws Exception{
		if(column >= size)
			throw new Exception("Index out of bounds");

		return columns[column];
	}

	/**
	 * Get a certain block
	 * @param blockX the desired block's x index (0 indexed)
	 * @param blockY the desired block's y index (0 indexed)
	 * @return the desired block
	 * @throws Exception
	 */
	public Block getBlock(int blockX, int blockY) throws Exception{
		if(blockX > getBlockSize() || blockY > getBlockSize())
			throw new Exception("Index out of bounds");

		return blocks[blockX][blockY];
	}

	/**
	 * Get the remaining number of unset values in the game
	 * @return
	 */
	public int getRemainingValues(){
		int total = 0;
		for(Row r : rows){
			total += (size - r.getUsedValues());
		}
		return total;
	}

	/**
	 * get if the puzzle is solved
	 * @return
	 */
	public boolean isSolved(){ return getRemainingValues() == 0; }

	private void validatePuzzle() throws Exception{
		if(originalPuzzle.size() != size + 2)
			throw new InvalidSizeException("Invalid: Game is not a valid size", getOriginalPuzzle());

		if(!(this.size == 4 || this.size == 9 || this.size == 16 || this.size == 25 || this.size == 36))
			throw new InvalidSizeException("Invalid: Game is not a valid size", getOriginalPuzzle());

		for(int i = 2; i < originalPuzzle.size(); i++){
			String[] line = originalPuzzle.get(i).split("\\s");
			if(line.length != size) {
				System.out.println(size + " " + line.length);
				throw new BadPuzzleException("Invalid: Bad Puzzle", getOriginalPuzzle());
			}

			for(String character : line){
				if(!characters.contains(character) && !character.equals("-"))
					throw new InvalidSymbolException("Invalid: Invalid Symbol", getOriginalPuzzle());
			}
		}
	}

	public void printPuzzle(){
		for(Row r : rows){
			System.out.println(r.toString());
		}
	}

	public String getOriginalPuzzle(){
		StringBuilder s = new StringBuilder();
		for(String s1 : originalPuzzle)
			s.append(s1 + "\n");
		return s.toString();
	}

	public int getOnePossibility(){ return onePossibility; }

	@Override
	public Object clone() throws CloneNotSupportedException {
		Game newGame = (Game) super.clone();

		try{
			Cell[][] newBoard = new Cell[size][size];

			for(int i = 0; i < size; i++){
				Cell[] col = getColumn(i).getCells();
				for(int j = 0; j < col.length; j++){
					newBoard[i][j] = (Cell) col[j].clone();
					newBoard[i][j].Attach(newGame);
				}

			}

			Row[] newRows = new Row[size];
			Column[] newColumns = new Column[size];
			Block[][] newBlocks = new Block[size][size];

			for(int i = 0; i < size; i++){
				Cell[] row = new Cell[size];
				Cell[] column = new Cell[size];
				for(int j = 0; j < size; j++){
					row[j] = newBoard[j][i];
					column[j] = newBoard[i][j];
				}
				Row r = new Row(row);
				Column c = new Column(column);

				newRows[i] = r;
				newColumns[i] = c;
			}

			//go through every block
			for(int blockX = 0; blockX < size/getBlockSize(); blockX++){
				for(int blockY = 0; blockY < size/getBlockSize(); blockY++){
					Cell[][] block = new Cell[getBlockSize()][getBlockSize()];
					for(int i = 0; i < getBlockSize(); i++){
						for(int j = 0; j < getBlockSize(); j++){
							block[i][j] = newBoard[(blockX * getBlockSize()) + i][(blockY * getBlockSize()) + j];
						}
					}
					newBlocks[blockX][blockY] = new Block(block);
				}
			}

			newGame.columns = newColumns;
			newGame.rows = newRows;
			newGame.blocks = newBlocks;
			newGame.characters = (HashSet<String>) characters.clone();
			return newGame;
		}
		catch (Exception ex){
			throw new CloneNotSupportedException(ex.getMessage());
		}
	}

	@Override
	public void Update(Cell c) throws Exception {
		if(c.getPossibleValues().size() == 1)
			onePossibility += 1;
	}

	@Override
	public String toString(){
		StringBuilder s = new StringBuilder();
		for(Row r : rows){
			s.append(r.toString() + "\n");
		}
		return s.toString();
	}
}