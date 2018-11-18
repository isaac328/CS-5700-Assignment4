package main.techniques;

import main.Cell;
import main.Game;

import java.util.HashMap;

public abstract class HiddenNumbers extends Technique {

	private static int counter;

	public final boolean execute(Game game) throws Exception{
		boolean rowChanges = false;
		boolean columnChanges = false;
		boolean blockChanges = false;

		// check rows first
		for(int row = 0; row < game.getSize(); row++){
			//frequency list to keep track of how many times a possibility has occurred
			HashMap<String, Integer> freq = new HashMap<>(2*game.getSize());
			//get current row
			Cell[] currentRow = game.getRow(row).getCells();

			setCells(currentRow, freq);

			boolean changed = findHidden(currentRow, freq);
			rowChanges = (changed) ? true : rowChanges;
		}

		// check columns second
		for(int column = 0; column < game.getSize(); column++){
			//frequency list to keep track of how many times a possibility has occurred
			HashMap<String, Integer> freq = new HashMap<>(2*game.getSize());
			//get current column
			Cell[] currentColumn = game.getColumn(column).getCells();

			setCells(currentColumn, freq);

			boolean changed = findHidden(currentColumn, freq);
			columnChanges = (changed) ? true : columnChanges;
		}

		//go through blocks last
		//go through every block
		int size = game.getSize();
		int blockSize = game.getBlockSize();

		//go through every block
		for(int blockX = 0; blockX < size/blockSize; blockX++){
			for(int blockY = 0; blockY < size/blockSize; blockY++){
				//frequency list to keep track of how many times a possibility has occurred
				HashMap<String, Integer> freq = new HashMap<>(2*game.getSize());

				Cell[] block = game.getBlock(blockX, blockY).get1DCells();

				setCells(block, freq);

				boolean changed = findHidden(block, freq);
				blockChanges = (changed) ? true : blockChanges;			}
		}
		if(rowChanges || columnChanges || blockChanges){
			counter += 1;
		}

		return (rowChanges || columnChanges || blockChanges);
	}

	public static int getCounter(){ return counter; }

	public static void resetCounter(){ counter = 0; }

	public abstract boolean findHidden(Cell[] cells, HashMap<String, Integer> freq) throws Exception;
	public abstract void setCells(Cell[] cells, HashMap<String, Integer> freq) throws Exception;
}
