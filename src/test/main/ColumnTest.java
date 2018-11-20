package test.main; 

import main.Block;
import main.Cell;
import main.Column;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import static org.junit.Assert.assertSame;

/** 
* Column Tester. 
* 
* @author <Authors name> 
* @since <pre>Nov 19, 2018</pre> 
* @version 1.0 
*/ 
public class ColumnTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: getCells() 
* 
*/ 
@Test
public void testGetCells() throws Exception {
	String[] cellValues = new String[]{"2", "3", "4", "5", "7", "8", "9"};
	Cell c1 = new Cell("-", cellValues, 0,0);

	cellValues = new String[]{"1","2","3","4","5","7","8"};
	Cell c2 = new Cell("-", cellValues, 1,0);

	cellValues = new String[]{"1","2","3","8","9"};
	Cell c3 = new Cell("-", cellValues, 2,0);

	cellValues = new String[]{"1","5","8","9"};
	Cell c4 = new Cell("-", cellValues, 3,0);

	cellValues = new String[]{"1","2","3","4","5","7","8"};
	Cell c5 = new Cell("6", cellValues, 4,0);

	cellValues = new String[]{"1","5","8","9"};
	Cell c6 = new Cell("-", cellValues, 5,0);

	cellValues = new String[]{"1","5","8","9"};
	Cell c7 = new Cell("-", cellValues, 6,0);

	cellValues = new String[]{"1","5"};
	Cell c8 = new Cell("-", cellValues, 7,0);

	cellValues = new String[]{"2","9"};
	Cell c9 = new Cell("-", cellValues, 8,0);

	Cell[] cells = new Cell[]{c1, c2, c3, c4, c5, c6, c7, c8, c9};

	Column col = new Column(cells);

	Cell[] returnedCells = col.getCells();
	assertSame(c1, returnedCells[0]);
	assertSame(c2, returnedCells[1]);
	assertSame(c3, returnedCells[2]);
	assertSame(c4, returnedCells[3]);
	assertSame(c5, returnedCells[4]);
	assertSame(c6, returnedCells[5]);
	assertSame(c7, returnedCells[6]);
	assertSame(c8, returnedCells[7]);
	assertSame(c9, returnedCells[8]);
}



} 
