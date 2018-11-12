package main;

import java.io.*;
import java.util.HashSet;

public class Game{
	private HashSet<String> characters;
	private Row[] rows;
	private Column[] columns;
	private Block[][] blocks;
	private int size;
	private int blockSize;
	private int unsetValues;

	public Game(InputStream in) throws Exception
	{
		characters = new HashSet<>();
		String[] stringCharacters;
		unsetValues = 0;

			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			size = Integer.parseInt(reader.readLine());
			blockSize = (int)Math.sqrt(size);

			rows = new Row[size];
			columns = new Column[size];
			blocks = new Block[size/blockSize][size/blockSize];

			//construct a two dimensional cell array first
			Cell[][] board = new Cell[size][size];

			stringCharacters = reader.readLine().split("\\s");
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
				Column c = new Column(column);

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

	public int getUnsetValues(){ return this.unsetValues; }


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

	public static void main(String[] args){
		try{
			FileInputStream in = new FileInputStream(new File("puzzle3.txt"));
			Game game = new Game(in);
			game.printPuzzle();


		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
}