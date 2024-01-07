package util;

import static org.junit.jupiter.api.Assertions.*;


import java.util.ArrayList;
import java.util.EmptyStackException;

import org.junit.jupiter.api.Test;

class StackTest {
	
	private int stackSize = 1000; 
	Stack<Integer> testStack = new Stack<Integer>();
	ArrayList<Integer> values = new ArrayList<Integer>();

	@Test
	void testPush() {
		
		populateValues();
		
		for (int i = 0; i < stackSize; i++) {
			testStack.push(values.get(i));
		}
		
		for (int i = 0; i < stackSize; i++) {
			assertEquals(values.get(stackSize - i - 1), testStack.pop());
		}
		
	}
	
	@Test
	void testPeek() {
		
		newStack();
		populateValues();
		
		Throwable exception = assertThrows(EmptyStackException.class, () -> testStack.peek());
	    assertEquals(null, exception.getMessage());
	    
		for (int i = 0; i < stackSize; i++) {
			testStack.push(values.get(i));
		}
		
		assertEquals(values.get(stackSize - 1), testStack.peek());
	}
	
	@Test
	void testEmpty() {
		
		newStack();
		assertTrue(testStack.empty());
		
		testStack.push((int)(Math.random() * 1000));
		assertTrue(!testStack.empty());
		
	}
	
	@Test
	void testPop() { //Pop was tested in Push. Just verifying for 1 number here
		
		newStack();
		
		Throwable exception = assertThrows(EmptyStackException.class, () -> testStack.pop());
	    assertEquals(null, exception.getMessage());
		
		newStack();
		int randomNum = (int) (Math.random() * 1000);
		testStack.push(randomNum);
		assertEquals(randomNum, testStack.pop());
		
	}
	
	@Test
	void testSize() {
		newStack();
		populateValues();
		
		assertEquals(0, testStack.size());
		
		for (int i = 0; i < stackSize; i++) {
			testStack.push(values.get(i));
		}
		
		assertEquals(values.size(), testStack.size());	
	}
	
	@Test
	void testSearch() {
		
		newStack();
		populateValues();
		
		for (int i = 0; i < stackSize; i++) {
			testStack.push(i);
		}
		
		for (int i = 0; i < stackSize; i++) {
			assertEquals(stackSize - i, testStack.search(i));
		}
		
		assertEquals(-1, testStack.search(100000)); 
		
	}
	
	private void populateValues() {
		
		values.clear();
		
		for (int i = 0; i < stackSize; i++) {
			int randomNum = (int) (Math.random() * 1000);
			values.add(randomNum);
		}
	}
	
	private void newStack() {
		Stack<Integer> testStack = new Stack<Integer>();
	}

}
