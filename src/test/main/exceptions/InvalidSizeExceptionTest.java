package test.main.exceptions; 

import main.exceptions.BadPuzzleException;
import main.exceptions.InvalidSizeException;
import main.exceptions.InvalidSymbolException;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import static org.junit.Assert.assertEquals;

/** 
* InvalidSizeException Tester. 
* 
* @author <Authors name> 
* @since <pre>Nov 19, 2018</pre> 
* @version 1.0 
*/ 
public class InvalidSizeExceptionTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: getOriginalPuzzle() 
* 
*/ 
@Test
public void testGetOriginalPuzzle() throws Exception {
	InvalidSizeException ex = new InvalidSizeException("Bad Puzzle", "Test Message");
	assertEquals("Test Message", ex.getOriginalPuzzle());

	ex = new InvalidSizeException("Bad Puzzle", "Another Test Message");
	assertEquals("Another Test Message", ex.getOriginalPuzzle());
} 

/** 
* 
* Method: lock() 
* 
*/ 
@Test
public void testLock() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: println(Object var1) 
* 
*/ 
@Test
public void testPrintlnO() throws Exception { 
//TODO: Test goes here... 
} 


} 
