package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

public abstract class SudukuSolver {

	public abstract ArrayList<Function<Game, Boolean>> getFunctions();

	public final Game solve(Game game) throws Exception{

		boolean changed;
		do{
			changed = false;
			for(Function<Game, Boolean> f : getFunctions()){
				boolean functionWorked = f.apply(game);
				if(functionWorked)
					changed = true;
				if(game.isSolved())
					return game;
			}

		}while(changed == true);

		//guess
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
						Game solution = solve(g2);
						if(solution != null)
							return solution;
					}
					catch (Exception ex){ continue; }
				}
			}
		}
		return null;
	}
}
