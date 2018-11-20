package test.main.techniques; 

import main.Cell;
import main.techniques.HiddenDouble;
import main.techniques.HiddenNumbers;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.util.HashMap;

import static org.junit.Assert.*;

/** 
* HiddenDouble Tester. 
* 
* @author <Authors name> 
* @since <pre>Nov 15, 2018</pre> 
* @version 1.0 
*/ 
public class HiddenDoubleTest { 

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
	String[] cellValues = new String[]{"2", "3", "4", "5", "7", "8", "9"};
	Cell c1 = new Cell("-", cellValues, 0, 0);

	cellValues = new String[]{"1", "2", "3", "4", "5", "7", "8"};
	Cell c2 = new Cell("-", cellValues, 1, 0);

	cellValues = new String[]{"1", "2", "3", "8", "9"};
	Cell c3 = new Cell("-", cellValues, 2, 0);

	cellValues = new String[]{"1", "5", "8", "9"};
	Cell c4 = new Cell("-", cellValues, 3, 0);

	cellValues = new String[]{"1", "2", "3", "4", "5", "7", "8"};
	Cell c5 = new Cell("6", cellValues, 4, 0);

	cellValues = new String[]{"1", "5", "8", "9"};
	Cell c6 = new Cell("-", cellValues, 5, 0);

	cellValues = new String[]{"1", "5", "8", "9"};
	Cell c7 = new Cell("-", cellValues, 6, 0);

	cellValues = new String[]{"1", "5"};
	Cell c8 = new Cell("-", cellValues, 7, 0);

	cellValues = new String[]{"2", "9"};
	Cell c9 = new Cell("-", cellValues, 8, 0);

	Cell[] row = new Cell[]{c1, c2, c3, c4, c5, c6, c7, c8, c9};

	HiddenNumbers hiddenDouble = new HiddenDouble();

	HashMap<String, Integer> freq = hiddenDouble.setup(row);

	hiddenDouble.findNumbers(row, freq);

	assertTrue(c1.getPossibleValues().size() == 2);
	assertTrue(c2.getPossibleValues().size() == 2);
	assertTrue(c3.getPossibleValues().size() == 5);
	assertTrue(c4.getPossibleValues().size() == 4);
	assertTrue(c6.getPossibleValues().size() == 4);
	assertTrue(c7.getPossibleValues().size() == 4);
	assertTrue(c8.getPossibleValues().size() == 2);
	assertTrue(c9.getPossibleValues().size() == 2);

	assertTrue(c1.getPossibleValues().get(0) == "4");
	assertTrue(c1.getPossibleValues().get(1) == "7");

	assertTrue(c2.getPossibleValues().get(0) == "4");
	assertTrue(c2.getPossibleValues().get(1) == "7");


}
} 
