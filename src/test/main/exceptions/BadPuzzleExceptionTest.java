package test.main.exceptions; 

import main.exceptions.BadPuzzleException;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import static org.junit.Assert.*;


/** 
* BadPuzzleException Tester. 
* 
* @author <Authors name> 
* @since <pre>Nov 19, 2018</pre> 
* @version 1.0 
*/ 
public class BadPuzzleExceptionTest { 

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
	BadPuzzleException ex = new BadPuzzleException("Bad Puzzle", "Test Message");
	assertEquals("Test Message", ex.getOriginalPuzzle());

	ex = new BadPuzzleException("Bad Puzzle", "Another Test Message");
	assertEquals("Another Test Message", ex.getOriginalPuzzle());

}

} 
