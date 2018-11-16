package test.main.techniques; 

import main.Cell;
import main.techniques.NakedDouble;
import main.techniques.NakedNumbers;
import main.techniques.NakedTriple;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.util.HashMap;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/** 
* NakedTriple Tester. 
* 
* @author <Authors name> 
* @since <pre>Nov 16, 2018</pre> 
* @version 1.0 
*/ 
public class NakedTripleTest { 

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

	cellValues = new String[]{"1","2","6"};
	Cell c2 = new Cell("-", cellValues, 1,0);

	cellValues = new String[]{"1","2","5","6","8"};
	Cell c3 = new Cell("-", cellValues, 2,0);

	cellValues = new String[]{"1","2","4","7"};
	Cell c4 = new Cell("-", cellValues, 3,0);

	cellValues = new String[]{"1","2","3","7","9"};
	Cell c5 = new Cell("-", cellValues, 4,0);

	cellValues = new String[]{"1","2","3","7","8","9"};
	Cell c6 = new Cell("-", cellValues, 5,0);

	cellValues = new String[]{"1","2","6"};
	Cell c7 = new Cell("-", cellValues, 6,0);

	cellValues = new String[]{"1","2","6"};
	Cell c8 = new Cell("-", cellValues, 7,0);

	cellValues = new String[]{"1","2","6","8"};
	Cell c9 = new Cell("-", cellValues, 8,0);

	Cell[] cells = new Cell[]{c1, c2, c3, c4, c5, c6, c7, c8, c9};

	NakedNumbers nakedTriple = new NakedTriple();

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

	nakedTriple.findNumbers(cells, freq);

	assertSame(2,c1.getPossibleValues().size());
	assertSame(3,c2.getPossibleValues().size());
	assertSame(2,c3.getPossibleValues().size());
	assertSame(2,c4.getPossibleValues().size());
	assertSame(3,c5.getPossibleValues().size());
	assertSame(4,c6.getPossibleValues().size());
	assertSame(3,c7.getPossibleValues().size());
	assertSame(3,c8.getPossibleValues().size());
	assertSame(1,c9.getPossibleValues().size());

	assertSame("5",c1.getPossibleValues().get(0));
	assertSame("7",c1.getPossibleValues().get(1));

	assertSame("1",c2.getPossibleValues().get(0));
	assertSame("2",c2.getPossibleValues().get(1));
	assertSame("6",c2.getPossibleValues().get(2));

	assertSame("5",c3.getPossibleValues().get(0));
	assertSame("8",c3.getPossibleValues().get(1));

	assertSame("4", c4.getPossibleValues().get(0));
	assertSame("7",c4.getPossibleValues().get(1));

	assertSame("3",c5.getPossibleValues().get(0));
	assertSame("7",c5.getPossibleValues().get(1));
	assertSame("9",c5.getPossibleValues().get(2));

	assertSame("3",c6.getPossibleValues().get(0));
	assertSame("7",c6.getPossibleValues().get(1));
	assertSame("8",c6.getPossibleValues().get(2));
	assertSame("9",c6.getPossibleValues().get(3));

	assertSame("1",c7.getPossibleValues().get(0));
	assertSame("2",c7.getPossibleValues().get(1));
	assertSame("6",c7.getPossibleValues().get(2));

	assertSame("1",c8.getPossibleValues().get(0));
	assertSame("2",c8.getPossibleValues().get(1));
	assertSame("6",c8.getPossibleValues().get(2));

	assertSame("8",c9.getPossibleValues().get(0));
}


} 
