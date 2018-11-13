package main;

import java.io.*;
import java.util.HashSet;

public class Game implements Cloneable, Observer{
	private HashSet<String> characters;
	private Row[] rows;
	private Column[] columns;
	private Block[][] blocks;
	private int size;
	private int blockSize;
	private int remainingValues;

	public Game(InputStream in) throws Exception
	{
		characters = new HashSet<>();

		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		size = Integer.parseInt(reader.readLine());
		remainingValues = size*size;
		blockSize = (int)Math.sqrt(size);

		rows = new Row[size];
		columns = new Column[size];
		blocks = new Block[size/blockSize][size/blockSize];

		//construct a two dimensional cell array first
		Cell[][] board = new Cell[size][size];

		String[] stringCharacters = reader.readLine().split("\\s");
		for(String character : stringCharacters){
			characters.add(character);
		}

		// read all cells into the board array
		for(int j = 0; j < size; j++){
			String[] boardLine = reader.readLine().split("\\s");
			validateCharacters(boardLine);

			for(int i = 0; i < size; i++){
				board[i][j] = new Cell(boardLine[i], stringCharacters, i, j);
			}
		}

		for(int i = 0; i < size; i++){
			Cell[] row = new Cell[size];
			Cell[] column = new Cell[size];
			for(int j = 0; j < size; j++){
				row[j] = board[j][i];
				column[j] = board[i][j];
			}
			Row r = new Row(row);
			r.Attach(this);
			Column c = new Column(column);
			c.Attach(this);

			rows[i] = r;
			columns[i] = c;
		}

		//go through every block
		for(int blockX = 0; blockX < size/blockSize; blockX++){
			for(int blockY = 0; blockY < size/blockSize; blockY++){
				Cell[][] block = new Cell[blockSize][blockSize];
				for(int i = 0; i < blockSize; i++){
					for(int j = 0; j < blockSize; j++){
						block[i][j] = board[(blockX * blockSize) + i][(blockY * blockSize) + j];
						block[i][j].Attach(this);
					}
				}
				blocks[blockX][blockY] = new Block(block);
			}
		}

		in.close();
	}


	public int getSize(){
		return this.size;
	}

	public int getBlockSize() { return this.blockSize; }

	public Row getRow(int row) throws Exception{
		if(row >= size)
			throw new Exception("Index out of bounds");

		return rows[row];
	}

	public Column getColumn(int column) throws Exception{
		if(column >= size)
			throw new Exception("Index out of bounds");

		return columns[column];
	}

	public Block getBlock(int blockX, int blockY) throws Exception{
		if(blockX > blockSize || blockY > blockSize)
			throw new Exception("Index out of bounds");

		return blocks[blockX][blockY];
	}

	public int getRemainingValues(){ return this.remainingValues; }

	public boolean isSolved(){ return remainingValues == 0; }


	public boolean validateCharacters(String[] line) throws Exception{
		for(String character : line){
			if(!characters.contains(character) && !character.equals("-")){
				throw new Exception("Invalid Characters");
			}
		}
		return true;
	}

	public void printPuzzle(){
		for(Row r : rows){
			System.out.println(r.toString());
		}
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		Game newGame = (Game) super.clone();

		try{
			Cell[][] newBoard = new Cell[size][size];

			for(int i = 0; i < size; i++){
				Cell[] col = getColumn(i).getCells();
				for(int j = 0; j < col.length; j++)
					newBoard[i][j] = (Cell) col[j].clone();
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
				r.Attach(newGame);
				Column c = new Column(column);
				c.Attach(newGame);

				newRows[i] = r;
				newColumns[i] = c;
			}

			//go through every block
			for(int blockX = 0; blockX < size/blockSize; blockX++){
				for(int blockY = 0; blockY < size/blockSize; blockY++){
					Cell[][] block = new Cell[blockSize][blockSize];
					for(int i = 0; i < blockSize; i++){
						for(int j = 0; j < blockSize; j++){
							block[i][j] = newBoard[(blockX * blockSize) + i][(blockY * blockSize) + j];
						}
					}
					newBlocks[blockX][blockY] = new Block(block);
					newBlocks[blockX][blockY].Attach(newGame);
				}
			}

			newGame.columns = newColumns;
			newGame.rows = newRows;
			newGame.blocks = newBlocks;
			newGame.characters = (HashSet<String>) characters.clone();
			return newGame;
		}
		catch (Exception ex){
			ex.printStackTrace();
		}

		return null;
	}

	@Override
	public void Update(Object obj) throws Exception {
		remainingValues -= 1;
	}

	public static void main(String[] args){
		Game game = null;
		try{
			FileInputStream in = new FileInputStream(new File("puzzle6.txt"));
			game = new Game(in);
			game.printPuzzle();


		}
		catch(Exception ex){
			ex.printStackTrace();
			game.printPuzzle();

		}
	}
}