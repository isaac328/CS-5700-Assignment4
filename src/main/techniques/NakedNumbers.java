package main.techniques;

import main.Cell;
import main.Game;

import java.util.HashMap;

public abstract class NakedNumbers {

	public final boolean execute(Game game) throws Exception{
		boolean rowChanged = false;
		boolean columnChanged = false;
		boolean blockChanged = false;

		for(int row = 0; row < game.getSize(); row++){
			Cell[] currentRow = game.getRow(row).getCells();

			HashMap<String, Integer> freq = setup(currentRow);

			boolean changed = findNumbers(currentRow, freq);
			rowChanged = (changed) ? true : rowChanged;

		}

		for(int column = 0; column < game.getSize(); column++){
			Cell[] currentColumn = game.getColumn(column).getCells();

			HashMap<String, Integer> freq = setup(currentColumn);

			boolean changed = findNumbers(currentColumn, freq);
			columnChanged = (changed) ? true : columnChanged;

		}

		for(int blockX = 0; blockX < game.getBlockSize(); blockX++){
			for(int blockY = 0; blockY < game.getBlockSize(); blockY++){
				Cell[] currentBlock = game.getBlock(blockX, blockY).get1DCells();

				HashMap<String, Integer> freq = setup(currentBlock);

				boolean changed = findNumbers(currentBlock, freq);

				blockChanged = (changed) ? true : blockChanged;
			}
		}
		return (rowChanged || columnChanged || blockChanged);
	}


	private HashMap<String, Integer> setup(Cell[] cells){
		HashMap<String, Integer> freq = new HashMap<>(2*cells.length);

		//go through the current Column
		for(Cell c : cells){
			//skip cells that are already set
			if(!c.isSet()){
				String s = c.getPossibleValues().toString();
				int count = freq.containsKey(s) ? freq.get(s) : 0;
				freq.put(s, count + 1);
			}
		}
		return freq;
	}

	public abstract boolean findNumbers(Cell[] cells, HashMap<String, Integer> freq) throws Exception;
}
