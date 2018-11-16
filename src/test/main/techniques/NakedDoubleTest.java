package test.main.techniques; 

import main.Cell;
import main.techniques.HiddenDouble;
import main.techniques.HiddenNumbers;
import main.techniques.NakedDouble;
import main.techniques.NakedNumbers;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.util.HashMap;

import static org.junit.Assert.assertTrue;

/** 
* NakedDouble Tester. 
* 
* @author <Authors name> 
* @since <pre>Nov 16, 2018</pre> 
* @version 1.0 
*/ 
public class NakedDoubleTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: findNumbers(Cell[] cells, HashMap<String, Integer> freq) 
* 
*/ 
@Test
public void testFindNumbers() throws Exception {
	String[] cellValues = new String[]{"5", "6", "7"};
	Cell c1 = new Cell("-", cellValues, 0,0);

	cellValues = new String[]{"8", "9"};
	Cell c2 = new Cell("-", cellValues, 1,0);

	cellValues = new String[]{"7"};
	Cell c3 = new Cell("7", cellValues, 2,0);

	cellValues = new String[]{"1","2","4","8","9"};
	Cell c4 = new Cell("-", cellValues, 3,0);

	cellValues = new String[]{"1","2","4","8","9"};
	Cell c5 = new Cell("-", cellValues, 4,0);

	cellValues = new String[]{"8","9"};
	Cell c6 = new Cell("-", cellValues, 5,0);

	cellValues = new String[]{"4","5","6","8","9"};
	Cell c7 = new Cell("-", cellValues, 6,0);

	cellValues = new String[]{"3","4","6","8","9"};
	Cell c8 = new Cell("-", cellValues, 7,0);

	cellValues = new String[]{"3","5","8","9"};
	Cell c9 = new Cell("-", cellValues, 8,0);

	Cell[] cells = new Cell[]{c1, c2, c3, c4, c5, c6, c7, c8, c9};

	NakedNumbers nakedDouble = new NakedDouble();

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

	nakedDouble.findNumbers(cells, freq);

	assertTrue(c1.getPossibleValues().size() == 3);
	assertTrue(c2.getPossibleValues().size() == 2);
	assertTrue(c4.getPossibleValues().size() == 3);
	assertTrue(c5.getPossibleValues().size() == 3);
	assertTrue(c6.getPossibleValues().size() == 2);
	assertTrue(c7.getPossibleValues().size() == 3);
	assertTrue(c8.getPossibleValues().size() == 3);
	assertTrue(c9.getPossibleValues().size() == 2);

	assertTrue(c1.getPossibleValues().get(0) == "5");
	assertTrue(c1.getPossibleValues().get(1) == "6");
	assertTrue(c1.getPossibleValues().get(2) == "7");

	assertTrue(c2.getPossibleValues().get(0) == "8");
	assertTrue(c2.getPossibleValues().get(1) == "9");

	assertTrue(c4.getPossibleValues().get(0) == "1");
	assertTrue(c4.getPossibleValues().get(1) == "2");
	assertTrue(c4.getPossibleValues().get(2) == "4");

	assertTrue(c5.getPossibleValues().get(0) == "1");
	assertTrue(c5.getPossibleValues().get(1) == "2");
	assertTrue(c5.getPossibleValues().get(2) == "4");

	assertTrue(c6.getPossibleValues().get(0) == "8");
	assertTrue(c6.getPossibleValues().get(1) == "9");

	assertTrue(c7.getPossibleValues().get(0) == "4");
	assertTrue(c7.getPossibleValues().get(1) == "5");
	assertTrue(c7.getPossibleValues().get(2) == "6");

	assertTrue(c8.getPossibleValues().get(0) == "3");
	assertTrue(c8.getPossibleValues().get(1) == "4");
	assertTrue(c8.getPossibleValues().get(2) == "6");

	assertTrue(c9.getPossibleValues().get(0) == "3");
	assertTrue(c9.getPossibleValues().get(1) == "5");
//
//	assertTrue(c2.getPossibleValues().get(0) == "4");
//	assertTrue(c2.getPossibleValues().get(1) == "7");
}


} 
