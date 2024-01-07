package util;
import java.util.EmptyStackException;

public class Stack<E> {
	
	//Private Node class (linked list data structure) for the Stack
	private class Node{
		
		private E data;
		private Node next;
		
		private Node(E data, Node next) {
			this.data = data;
			this.next = next;
		}
	}
	
	//Instance variable for the head node
	
	private Node head;
	
	//Instance variable for the size of the stack
	
	private int size = 0;

	public Stack() {
		
	}

	//pushes a new node onto the stack, which makes it the new head
	//and points it at the old head
	
	public E push(E data) {
		head = new Node(data, head); 
		size++;
		return data;
	}
	
	//returns the data stored in head, the last node pushed onto the stack
	
	public E pop() {
		if (empty()) {
			throw new EmptyStackException();
		}
		E lastElement = head.data;
		head = head.next;
		size--;
		return lastElement;
	}
	
	//looks at the data stored in the head
	
	public E peek() {
		
		if (empty()) {
			throw new EmptyStackException();
		}
		
		return head.data;
		
	}
	
	//Checks if the stack is empty by seeing if the head is null
	
	public boolean empty() {
		return head == null;
	}
	
	//Search method, which looks starting from the head
	
	public int search(Object item) {
		return search(item, head);
	}
	
	//size of the stack
	
	public int size() { //Mostly used for the recursive search function
		
		return size;
	}
	
	//Helper method for the search method
	
	private int search (Object item, Node currentNode) {
		
		if (currentNode == null) {
			return -1 - size; 
		}
		if (currentNode.data.equals(item)) {
			return 1;
		}
		return 1 + search(item, currentNode.next);
	}
	
}
