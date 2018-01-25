
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private Item[] arr;
	private int n;
	public RandomizedQueue() {
		arr = (Item[]) new Object[2]; // the init size is 2, and maintain 1/4-1/2 is used.
		n = 0;
	}

	public boolean isEmpty() {
		return n == 0;
	}
	
	public int size() {
		return n;
	}
	
	private void resize(int length) {
		Item[] temp = (Item[]) new Object[length];
		for (int i = 0; i < n; i++) {
			temp[i] = arr[i];
		}
		arr = temp;
	}
	
	public void enqueue(Item item) {
		if (item == null)
			throw new java.lang.IllegalArgumentException();
		if (n == arr.length)
			resize(2 * arr.length);
		arr[n++] = item;
	}
	
	public Item dequeue() {
		if (isEmpty())
			throw new java.util.NoSuchElementException();
		int targetId = StdRandom.uniform(0, n);
		Item targetItem = arr[targetId];
		if (targetId != n - 1) 
			arr[targetId] = arr[n - 1];
		arr[n - 1] = null;
		n--;
		if (n > 0 && n == arr.length/4)
			resize(arr.length/2);
		return targetItem;
	}
	
	public Item sample() {
		if (isEmpty())
			throw new java.util.NoSuchElementException();
		int targetId = StdRandom.uniform(0, n);
		Item targetItem = arr[targetId];
		return targetItem;
	}
	
	@Override
	public Iterator<Item> iterator() {
		return new RQIterator();
	}
	
	private class RQIterator implements Iterator<Item> {
		private int index;
		private Item[] a;
		
		public RQIterator() {
			index = 0;
			a = (Item[]) new Object[n];
			for (int i = 0; i < n; i++) {
				a[i] = arr[i];
			}
			StdRandom.shuffle(a); // Knuth shuffle
		}
		
		@Override
		public boolean hasNext() {
			return index < n;
		}
		
		@Override
		public void remove() {
			throw new java.lang.UnsupportedOperationException();
		}
		
		@Override
		public Item next() {
			if (!hasNext()) {
				throw new java.util.NoSuchElementException();
			}
			Item item = a[index++];
			return item;
		}
		
	}
	
	public static void main(String[] args) {
		RandomizedQueue<String> RQ = new RandomizedQueue<String>();
		while (!StdIn.isEmpty()) {
			String str = StdIn.readString();
			if (str.equals("exit"))
				break;
			RQ.enqueue(str);
		}
		for (String s : RQ) {
			System.out.println(s);
		}
	}
}
