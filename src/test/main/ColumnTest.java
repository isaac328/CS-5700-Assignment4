package test.main; 

import main.Block;
import main.Cell;
import main.Column;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

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

@Test
public void testConstructor() throws Exception{
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

	assertSame(cells, col.getCells());

	try{
		Column badCol = new Column(null);
		fail("Expected Exception Not Thrown");
	}catch (Exception ex){
		assertSame("Cells cannot be null", ex.getMessage());
	}
}


} 
