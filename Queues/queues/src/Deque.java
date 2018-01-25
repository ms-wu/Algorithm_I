
import java.util.Iterator;

import edu.princeton.cs.algs4.StdIn;

public class Deque<Item> implements Iterable<Item> {
	// the number of queue
	private int n;
	private Node first; 
	private Node last;
	
	private class Node {
		Item item;
		Node pre;
		Node next;
	}
	
	// initialize the queue
	public Deque() {
		n = 0;
		first = null;
		last = null;
	}
	
	// judge the queue is empty?
	public boolean isEmpty() {
		return n == 0;
	}
	
	public int size() {
		return n;
	}
	
	public void addFirst(Item item) {
		if (item == null) {
			throw new java.lang.IllegalArgumentException();
		}
		Node oldfirst = first;
		first = new Node();
		first.item = item;
		first.next = oldfirst;
		if (isEmpty())
			last = first;
		else
			oldfirst.pre = first;
		n++;
	}
	
	public void addLast(Item item) {
		if (item == null)
			throw new java.lang.IllegalArgumentException();
		Node oldlast = last;
		last = new Node();
		last.item = item;
		last.pre = oldlast;
		if (isEmpty())
			first = last;
		else
			oldlast.next = last;
		n++;
	}
	
	public Item removeFirst() {
		if (isEmpty())
			throw new java.util.NoSuchElementException();
		Item item = first.item;
		first = first.next;
		n--;
		if (isEmpty())
			last = null;
		else
			first.pre = null;
		return item;
	}
	
	public Item removeLast() {
		if (isEmpty())
			throw new java.util.NoSuchElementException();
		Item item = last.item;
		last = last.pre;
		n--;
		if (isEmpty())
			first = null;
		else
			last.next = null;
		return item;
	}
	public Iterator<Item> iterator() {
		return new ListIterator();
	}
	
	private class ListIterator implements Iterator<Item> {
		private Node current;
		
		public ListIterator() {
			current = first;
		}
		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public void remove() {
			throw new java.lang.UnsupportedOperationException();
		}
		
		@Override
		public Item next() {
			if (!hasNext())
				throw new java.util.NoSuchElementException();
			Item item = current.item;
			current = current.next;
			return item;
		}	
	}
	
	public static void main(String[] args) {
		Deque<String> queue = new Deque<String>();
		while (!StdIn.isEmpty()) {
			String str = StdIn.readString();
			if (str.equals("exit")) // 'exit' and stop input
				break;
			queue.addFirst(str);
		}
		for (String s : queue) {
			System.out.println(s + " ");
		}
		System.out.println("size:" + queue.size());
	}
}