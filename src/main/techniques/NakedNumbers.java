package main.techniques;

import main.Cell;
import main.Game;

import java.util.HashMap;
import org.apache.commons.lang3.time.StopWatch;


public abstract class NakedNumbers extends Technique {

	private static int counter;
	private static StopWatch watch = new StopWatch();


	@Override
	public final boolean execute(Game game) throws Exception{
		startWatch();
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
				Cell[] currentBlock = game.getBlock(blockX, blockY).getCells();

				HashMap<String, Integer> freq = setup(currentBlock);

				boolean changed = findNumbers(currentBlock, freq);

				blockChanged = (changed) ? true : blockChanged;
			}
		}

		if(rowChanged || columnChanged || blockChanged){
			counter += 1;
		}

		watch.suspend();

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

	private static void startWatch(){
		try{
			if(watch.isSuspended())
				watch.resume();
			else
				watch.start();
		}catch (Exception ex){System.out.println("Error Starting Watch");}
	}

	public static String getTime(){
		return watch.toString();
	}

	public static int getCounter(){ return counter; }

	public static void resetCounter(){
		counter = 0;
		watch.reset();
	}

	public abstract boolean findNumbers(Cell[] cells, HashMap<String, Integer> freq) throws Exception;


}
