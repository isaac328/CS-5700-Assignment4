package test.main; 

import main.Block;
import main.Column;
import main.Game;
import main.Row;
import main.exceptions.InvalidSizeException;
import main.exceptions.InvalidSymbolException;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static org.junit.Assert.*;

/** 
* Game Tester. 
* 
* @author <Authors name> 
* @since <pre>Nov 19, 2018</pre> 
* @version 1.0 
*/ 
public class GameTest {



@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: getSize() 
* 
*/ 
@Test
public void testGetSize() throws Exception {
	FileInputStream in = new FileInputStream(new File("puzzle1.txt"));
	Game game1 = new Game(in);
	assertEquals(4, game1.getSize());

	FileInputStream in2 = new FileInputStream(new File("puzzle5.txt"));
	Game game2 = new Game(in2);
	assertEquals(9, game2.getSize());}

/** 
* 
* Method: getBlockSize() 
* 
*/ 
@Test
public void testGetBlockSize() throws Exception {
	FileInputStream in = new FileInputStream(new File("puzzle1.txt"));
	Game game1 = new Game(in);
	assertEquals(2, game1.getBlockSize());

	FileInputStream in2 = new FileInputStream(new File("puzzle5.txt"));
	Game game2 = new Game(in2);
	assertEquals(3, game2.getBlockSize());
}

/** 
* 
* Method: getRow(int row) 
* 
*/ 
@Test
public void testGetRow() throws Exception {
	FileInputStream in = new FileInputStream(new File("puzzle1.txt"));
	Game game = new Game(in);

	Row row = game.getRow(0);
	assertEquals("4", row.getCells()[0].toString());
	assertEquals("2", row.getCells()[1].toString());
	assertEquals("3", row.getCells()[2].toString());
	assertEquals("1", row.getCells()[3].toString());

	row = game.getRow(1);
	assertEquals("1", row.getCells()[0].toString());
	assertEquals("3", row.getCells()[1].toString());
	assertEquals("4", row.getCells()[2].toString());
	assertEquals("2", row.getCells()[3].toString());

	row = game.getRow(2);
	assertEquals("3", row.getCells()[0].toString());
	assertEquals("1", row.getCells()[1].toString());
	assertEquals("2", row.getCells()[2].toString());
	assertEquals("4", row.getCells()[3].toString());

	row = game.getRow(3);
	assertEquals("2", row.getCells()[0].toString());
	assertEquals("4", row.getCells()[1].toString());
	assertEquals("1", row.getCells()[2].toString());
	assertEquals("3", row.getCells()[3].toString());

}

/** 
* 
* Method: getColumn(int column) 
* 
*/ 
@Test
public void testGetColumn() throws Exception {
	FileInputStream in = new FileInputStream(new File("puzzle1.txt"));
	Game game = new Game(in);

	Column col = game.getColumn(0);
	assertEquals("4", col.getCells()[0].toString());
	assertEquals("1", col.getCells()[1].toString());
	assertEquals("3", col.getCells()[2].toString());
	assertEquals("2", col.getCells()[3].toString());

	col = game.getColumn(1);
	assertEquals("2", col.getCells()[0].toString());
	assertEquals("3", col.getCells()[1].toString());
	assertEquals("1", col.getCells()[2].toString());
	assertEquals("4", col.getCells()[3].toString());

	col = game.getColumn(2);
	assertEquals("3", col.getCells()[0].toString());
	assertEquals("4", col.getCells()[1].toString());
	assertEquals("2", col.getCells()[2].toString());
	assertEquals("1", col.getCells()[3].toString());

	col = game.getColumn(3);
	assertEquals("1", col.getCells()[0].toString());
	assertEquals("2", col.getCells()[1].toString());
	assertEquals("4", col.getCells()[2].toString());
	assertEquals("3", col.getCells()[3].toString());
}

/** 
* 
* Method: getBlock(int blockX, int blockY) 
* 
*/ 
@Test
public void testGetBlock() throws Exception {
	FileInputStream in = new FileInputStream(new File("puzzle1.txt"));
	Game game = new Game(in);

	Block block = game.getBlock(0, 0);
	assertEquals("4", block.getCells()[0].toString());
	assertEquals("1", block.getCells()[1].toString());
	assertEquals("2", block.getCells()[2].toString());
	assertEquals("3", block.getCells()[3].toString());

	block = game.getBlock(1,0);
	assertEquals("3", block.getCells()[0].toString());
	assertEquals("4", block.getCells()[1].toString());
	assertEquals("1", block.getCells()[2].toString());
	assertEquals("2", block.getCells()[3].toString());

	block = game.getBlock(0,1);
	assertEquals("3", block.getCells()[0].toString());
	assertEquals("2", block.getCells()[1].toString());
	assertEquals("1", block.getCells()[2].toString());
	assertEquals("4", block.getCells()[3].toString());

	block = game.getBlock(1,1);
	assertEquals("2", block.getCells()[0].toString());
	assertEquals("1", block.getCells()[1].toString());
	assertEquals("4", block.getCells()[2].toString());
	assertEquals("3", block.getCells()[3].toString());}

/** 
* 
* Method: getRemainingValues() 
* 
*/ 
@Test
public void testGetRemainingValues() throws Exception {
	FileInputStream in = new FileInputStream(new File("puzzle1.txt"));
	Game game = new Game(in);

	assertEquals(0, game.getRemainingValues());

	FileInputStream in1 = new FileInputStream(new File("puzzle3-0401.txt"));
	Game game2 = new Game(in1);

	assertEquals(54, game2.getRemainingValues());


}

/** 
* 
* Method: isSolved() 
* 
*/ 
@Test
public void testIsSolved() throws Exception {
	FileInputStream in = new FileInputStream(new File("puzzle1.txt"));
	Game game = new Game(in);

	assertTrue(game.isSolved());

	FileInputStream in1 = new FileInputStream(new File("puzzle3-0401.txt"));
	Game game2 = new Game(in1);

	assertFalse(game2.isSolved());
}

/** 
* 
* Method: printPuzzle() 
* 
*/ 
@Test
public void testPrintPuzzle() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getOriginalPuzzle() 
* 
*/ 
@Test
public void testGetOriginalPuzzle() throws Exception {
	ArrayList<String> testPuzzle = new ArrayList<>();
	FileInputStream in = new FileInputStream(new File("puzzle1.txt"));
	BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	String str;
	while((str = reader.readLine()) != null){
		testPuzzle.add(str);
	}
	reader.close();

	in = new FileInputStream(new File("puzzle1.txt"));
	Game game = new Game(in);

	StringBuilder s = new StringBuilder();
	for(String s1 : testPuzzle)
		s.append(s1 + "\n");

	assertTrue(s.toString().equals(game.getOriginalPuzzle()));
} 

/** 
* 
* Method: getOnePossibility() 
* 
*/ 
@Test
public void testGetOnePossibility() throws Exception {
	FileInputStream in = new FileInputStream(new File("puzzle1.txt"));
	Game game = new Game(in);


}

/** 
* 
* Method: clone() 
* 
*/ 
@Test
public void testClone() throws Exception {
	FileInputStream in = new FileInputStream(new File("puzzle3-0401.txt"));
	Game game = new Game(in);

	Game game2 = (Game)game.clone();

	for(int i = 0; i < game.getSize(); i++)
		assertTrue(game.getRow(i).toString().equals(game2.getRow(i).toString()));

	game2.getRow(0).getCells()[0].setValue("9");

	assertFalse(game2.getRow(0).getCells()[0].toString().equals(game.getRow(0).getCells()[0].toString()));
}

/** 
* 
* Method: Update(Object obj) 
* 
*/ 
@Test
public void testUpdate() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: toString() 
* 
*/ 
@Test
public void testToString() throws Exception {
	FileInputStream in = new FileInputStream(new File("puzzle3-0401.txt"));
	Game game = new Game(in);

	StringBuilder s = new StringBuilder();
	for(int i = 0; i < game.getSize(); i++){
		Row r = game.getRow(i);
		s.append(r.toString() + "\n");
	}

	assertEquals(s.toString(), game.toString());
}


/** 
* 
* Method: validatePuzzle() 
* 
*/ 
@Test
public void testValidatePuzzle() throws Exception {
	FileInputStream in = new FileInputStream(new File("badpuzzle1.txt"));
	try{
		Game game = new Game(in);
		fail("Expected Exception not thrown");
	}
	catch(InvalidSizeException ex){
		assertEquals("Invalid: Game is not a valid size", ex.getMessage());
	}
	catch(Exception ex){
		fail("Expected Exception not thrown");
	}

	in = new FileInputStream(new File("badpuzzle2.txt"));
	try{
		Game game = new Game(in);
		fail("Expected Exception not thrown");
	}
	catch(InvalidSymbolException ex){
		assertEquals("Invalid: Invalid Symbol", ex.getMessage());
	}
	catch(Exception ex){
		fail("Expected Exception not thrown");
	}

} 

} 
