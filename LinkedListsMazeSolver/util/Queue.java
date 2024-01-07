package util;
import java.util.NoSuchElementException;

public class Queue<E> {

	private class Node{
		
		private E data;
		private Node next;
		
		private Node(E data, Node next) {
			this.data = data;
			this.next = next;
		}
	}
	
	//Instance variables for the head and tail of the stack
	
	private Node head;
	private Node tail;
	
	public Queue() {
		
	}
	
	//Adds to the head of the Queue
	
	public boolean add(E data) { 
		
		if (head == null) {
			
			head = new Node(data, head); //Sets both the tail and the head
			tail = head;
			
		}else {
			
			tail.next = new Node(data, null); //Makes a new node at the end of the line
			tail = tail.next; //Sets the tail to the end of the line
			
		}
		return true;
	}
	
	public boolean offer(E data) {
		
		if (head == null) {
			head = new Node(data, head);
			tail = head;
			
		}else {
			
			tail.next = new Node(data, null);
			tail = tail.next;
			
		}
		return true;
	}
	
	public E element() { 
		
		if (head == null) {
			throw new NoSuchElementException(); //If the head is null, throws an exception
		}
		
		return head.data;
	}
	
	public E peek() { 
		
		if (head == null) { 
			return null; //If the head is null, returns null
		}
		
		return head.data;
	}
	
	public E poll() {
		
		if (head ==  null) {
			return null; //If head is null, return null
		}
		
		E lastElement = head.data;
		head = head.next;
		return lastElement;
		
	}
	
	public E remove() {
		
		if (head == null) {
			throw new NoSuchElementException(); //If head is null throw an exception
		}
		
		E lastElement = head.data;
		head = head.next;
		return lastElement;
		
	}
	
}
