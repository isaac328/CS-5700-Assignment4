package test.main; 

import main.Cell;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import static org.junit.Assert.*;


/** 
* Cell Tester. 
* 
* @author <Authors name> 
* @since <pre>Nov 19, 2018</pre> 
* @version 1.0 
*/ 
public class CellTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: removePossibleValue(String value) 
* 
*/ 
@Test
public void testRemovePossibleValue() throws Exception {
	String[] allValues = {"1", "2", "3", "4"};
	Cell c = new Cell("-", allValues, 0, 0);
	c.removePossibleValue("1");
	assertEquals(3, c.getPossibleValues().size());
	assertFalse(c.getPossibleValues().contains("1"));
}

/** 
* 
* Method: setValue(String value) 
* 
*/ 
@Test
public void testSetValue() throws Exception {
	String[] allValues = {"1", "2", "3", "4"};
	Cell c = new Cell("-", allValues, 0, 0);
	assertEquals("-", c.toString());
	c.setValue("3");
	assertEquals("3", c.toString());
}

/** 
* 
* Method: isSet() 
* 
*/ 
@Test
public void testIsSet() throws Exception {
	String[] allValues = {"1", "2", "3", "4"};
	Cell c = new Cell("-", allValues, 0, 0);
	assertFalse(c.isSet());
	c.setValue("3");
	assertTrue(c.isSet());
}

/** 
* 
* Method: getPossibleValues() 
* 
*/ 
@Test
public void testGetPossibleValues() throws Exception {
	String[] allValues = {"1", "2", "3", "4"};
	Cell c = new Cell("-", allValues, 0, 0);
	for(int i = 0; i < allValues.length; i++)
		assertEquals(allValues[i], c.getPossibleValues().get(i));
}

/** 
* 
* Method: Attach(Observer o) 
* 
*/ 
@Test
public void testAttach() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: Detach(Observer o) 
* 
*/ 
@Test
public void testDetach() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: Notify() 
* 
*/ 
@Test
public void testNotify() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: toString() 
* 
*/ 
@Test
public void testToString() throws Exception {
	String[] allValues = {"1", "2", "3", "4"};
	Cell c = new Cell("-", allValues, 0, 0);
	assertEquals("-", c.toString());

	c = new Cell("2", allValues, 0, 0);
	assertEquals("2", c.toString());
}

/** 
* 
* Method: equals(Object o) 
* 
*/ 
@Test
public void testEquals() throws Exception {
	String[] allValues = {"1", "2", "3", "4"};
	Cell c = new Cell("-", allValues, 0, 0);

	Cell c2 = new Cell("-", allValues, 0, 0);

	assertTrue(c.equals(c2));

	c2 = new Cell("-", allValues, 1, 0);

	assertFalse(c.equals(c2));
}

/** 
* 
* Method: compareTo(Object o) 
* 
*/ 
@Test
public void testCompareTo() throws Exception {
	String[] allValues = {"1", "2", "3", "4"};
	Cell c = new Cell("-", allValues, 0, 0);

	String[] allValues2 = {"1", "2", "3"};
	Cell c2 = new Cell("-", allValues, 0, 0);

	assertTrue(c.compareTo(c2) == 0);

	c2 = new Cell("-", allValues2, 0, 0);

	assertTrue(c.compareTo(c2) == 1);

}



/** 
* 
* Method: clone() 
* 
*/ 
@Test
public void testClone() throws Exception {
	String[] allValues = {"1", "2", "3", "4"};
	Cell c = new Cell("-", allValues, 0, 0);

	Cell c2 = (Cell) c.clone();

	assertTrue(c2.equals(c));
} 

/** 
* 
* Method: getTime() 
* 
*/ 
@Test
public void testGetTime() throws Exception { 
//TODO: Test goes here... 
} 


/** 
* 
* Method: startWatch() 
* 
*/ 
@Test
public void testStartWatch() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = Cell.getClass().getMethod("startWatch"); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

} 
