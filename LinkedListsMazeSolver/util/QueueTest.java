package util;

import static org.junit.jupiter.api.Assertions.*;


import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

class QueueTest {
	
	private int queueSize = 500; 
	Queue<Integer> testQueue = new Queue<Integer>();
	ArrayList<Integer> values = new ArrayList<Integer>();
	
	@Test
	void testAdd() {
		
		populateValues();
		newQueue();
		
		for (int i = 0; i < queueSize; i++) {
			testQueue.add(values.get(i));
		}
		
		for (int i = 0; i < queueSize; i++) {
			assertEquals(values.get(i), testQueue.remove());
		}
		
	}
	
	@Test
	void testOffer() {
			
		populateValues();
		newQueue();
		
		for (int i = 0; i < queueSize; i++) {
			testQueue.offer(values.get(i));
		}
			
		for (int i = 0; i < queueSize; i++) {
			assertEquals(values.get(i), testQueue.remove());	
		}
		
	}
	
	@Test
	void testPoll(){ 
		
		populateValues();
		newQueue();
		
		for (int i = 0; i < queueSize; i++) {	
			testQueue.add(values.get(i));
		}
			
		for (int i = 0; i < queueSize; i++) {
			
			assertEquals(values.get(i), testQueue.poll());
			
		}
		
		assertEquals(null, testQueue.poll());
		
	}
	
	@Test
	void testRemove() { //Remove was tested in Add and Offer. Testing 1 remove here
		
		newQueue();
		int randomNum = (int) (Math.random() * 1000);
		testQueue.add(randomNum);
		assertEquals(randomNum, testQueue.remove());
		
		Throwable exception = assertThrows(NoSuchElementException.class, () -> testQueue.remove());
	    assertEquals(null, exception.getMessage());
		
	}
	
	@Test
	void testPeek() {
		
		newQueue();
		populateValues();
		
	    assertEquals(null, testQueue.peek());
	    
		for (int i = 0; i < queueSize; i++) {
			testQueue.add(values.get(i));
		}
		
		assertEquals(values.get(0), testQueue.peek());
		
	}
	
	@Test
	void testElement() {
		
		newQueue();
		populateValues();
		
		Throwable exception = assertThrows(NoSuchElementException.class, () -> testQueue.element());
	    assertEquals(null, exception.getMessage());
	    
		for (int i = 0; i < queueSize; i++) {
			testQueue.add(values.get(i));
		}
		
		assertEquals(values.get(0), testQueue.peek());
		
	}
	
	private void newQueue(){
		Queue<Integer> testQueue = new Queue<Integer>();
	}
	
	private void populateValues() {
		
		values.clear();
		
		for (int i = 0; i < queueSize; i++) {	

			int randomNum = (int) (Math.random() * 1000);
			values.add(randomNum);
				
		}
	}
}
