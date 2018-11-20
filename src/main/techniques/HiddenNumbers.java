package main.techniques;

import main.Cell;
import main.Game;
import org.apache.commons.lang3.time.StopWatch;

import java.util.HashMap;

public abstract class HiddenNumbers extends Technique {

	private static int counter = 0;
	private static StopWatch watch = new StopWatch();

	public final boolean execute(Game game) throws Exception{
		startWatch();

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

				Cell[] block = game.getBlock(blockX, blockY).getCells();

				setCells(block, freq);

				boolean changed = findHidden(block, freq);
				blockChanges = (changed) ? true : blockChanges;			}
		}
		if(rowChanges || columnChanges || blockChanges){
			counter += 1;
		}
		watch.suspend();

		return (rowChanges || columnChanges || blockChanges);
	}

	private void startWatch(){
		try{
			if(watch.isSuspended())
				watch.resume();
			else
				watch.start();
		}catch (Exception ex){ System.out.println("Error Starting Watch"); }
	}

	public static String getTime(){
		try{
			return watch.toString();
		}catch (Exception ex){ ex.printStackTrace(); }
		return "Error";
	}


	public static int getCounter(){ return counter; }

	public static void resetCounter(){
		counter = 0;
		watch.reset();
	}

	public void setCells(Cell[] cells, HashMap<String, Integer> freq) {
		//go through the current Column
		for(Cell c : cells){
			//skip cells that are already set
			if(!c.isSet()){
				//set the frequency of each possibility
				for(String s : c.getPossibleValues()){
					int count = freq.containsKey(s) ? freq.get(s) : 0;
					freq.put(s, count + 1);
				}
			}
		}
	}

	public abstract boolean findHidden(Cell[] cells, HashMap<String, Integer> freq) throws Exception;
}
