package test.main.techniques; 

import main.Cell;
import main.techniques.HiddenNumbers;
import main.techniques.HiddenSingle;
import main.techniques.NakedDouble;
import main.techniques.NakedNumbers;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.util.HashMap;

import static org.junit.Assert.*;

/** 
* HiddenSingle Tester. 
* 
* @author <Authors name> 
* @since <pre>Nov 19, 2018</pre> 
* @version 1.0 
*/ 
public class HiddenSingleTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: findHidden(Cell[] cells, HashMap<String, Integer> freq) 
* 
*/ 
@Test
public void testFindHidden() throws Exception {
	String[] cellValues = new String[]{"2", "6", "8", "9"};
	Cell c1 = new Cell("-", cellValues, 0,0);

	cellValues = new String[]{"2","7","8","9"};
	Cell c2 = new Cell("-", cellValues, 1,0);

	cellValues = new String[]{"1","6","7","8"};
	Cell c3 = new Cell("-", cellValues, 2,0);

	cellValues = new String[]{"3"};
	Cell c4 = new Cell("3", cellValues, 3,0);

	cellValues = new String[]{"1","4","7","9"};
	Cell c5 = new Cell("-", cellValues, 4,0);

	cellValues = new String[]{"1","6","7","8"};
	Cell c6 = new Cell("-", cellValues, 5,0);

	cellValues = new String[]{"2","6","7","8","9"};
	Cell c7 = new Cell("-", cellValues, 6,0);

	cellValues = new String[]{"5"};
	Cell c8 = new Cell("5", cellValues, 7,0);

	cellValues = new String[]{"2","6","7","8"};
	Cell c9 = new Cell("-", cellValues, 8,0);

	Cell[] cells = new Cell[]{c1, c2, c3, c4, c5, c6, c7, c8, c9};

	HiddenNumbers hiddenSingle = new HiddenSingle();

	HashMap<String, Integer> freq = new HashMap<>(2*cells.length);

	hiddenSingle.setCells(cells, freq);

	hiddenSingle.findHidden(cells, freq);

	assertSame(4,c1.getPossibleValues().size());
	assertSame(4,c2.getPossibleValues().size());
	assertSame(4,c3.getPossibleValues().size());
	assertSame(4,c5.getPossibleValues().size());
	assertSame(4,c6.getPossibleValues().size());
	assertSame(5,c7.getPossibleValues().size());
	assertSame(4,c9.getPossibleValues().size());

	assertEquals("4", c5.toString());

}



} 
