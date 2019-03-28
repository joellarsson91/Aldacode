package alda.linear;
//Användarnamn: Joel Larsson - jola0630
//Samarbets - & diskussionspartner: Erik Åkerberg
//Fungerar i JUnits ALDAQueueTest, men får fel i evaluation, ClassNotFoundException.
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyQueue<T> implements ALDAQueue<T> {

	private int totalquecapacity;
	private Node<T> first;
	private Node<T> last;
	private int totalquesize;

	public MyQueue(int i) {
		if (i <= 0) {
			throw new IllegalArgumentException();
		}
		this.totalquecapacity = i;
	}
	
	public void add(T data) {
		if (currentCapacity() == 0) {
			throw new IllegalStateException();
		}

		if (data == null) {
			throw new NullPointerException();
		}

		if (first == null) {
			first = new Node<T>(data);
			last = first;
		} else {
			last.next = new Node<T>(data);
			last = last.next;
		}
		totalquesize++;

	}

	public Iterator<T> iterator() {

		return new MyQueueIterator<T>(first);
	}

	@Override
	public String toString() {
		String s = "[";
		boolean first = true;
		for (Object o : this) {

			if (first) {
				first = false;
			} else {
				s += ", ";
			}
			s += o.toString();
		}

		return s + "]";
	}

	private static class Node<T> { // nodklassen
		T data;
		Node next;

		public Node(T data) {
			this.data = data;
		}
	}

	private class MyQueueIterator<T> implements Iterator<T> {
		private Node<T> current;

		public MyQueueIterator(Node<T> t) {
			this.head = t;
			this.current = t;
		}

		private Node<T> head;

		public boolean hasNext() {
			return current != null;
		}

		public T next() {
			if (current == null) {
				throw new NoSuchElementException();
			}
			T returnedData = current.data;
			current = current.next;
			return returnedData;

		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

	public int discriminate(T t) {
		if (t == null) {
			throw new NullPointerException();
		}
		Iterator<T> iterator = iterator();
		int movedCount = 0;
		MyQueue<T> tempListOne = new MyQueue<>(totalquecapacity);
		MyQueue<T> tempListTwo = new MyQueue<>(totalquecapacity);
		while (iterator.hasNext()) {
			T current = iterator.next();
			if (current.equals(t)) {
				tempListOne.add(current);
				movedCount++;
			} else {
				tempListTwo.add(current);

			}
		}

		for (T element : tempListOne) {
			tempListTwo.add(element);
		}
		this.clear();
		for (T element : tempListTwo) {
			this.add(element);
		}
		return movedCount;
	}



	public void addAll(Collection<? extends T> c) {
		for (T element : c) {
			this.add(element);
		}
	}

	public T remove() {
		if (first == null) {
			throw new NoSuchElementException();
		}
		Node<T> RemoveNode = first;
		first = RemoveNode.next;
		totalquesize--;
		return RemoveNode.data;
	}

	public T peek() {
		if (first == null) {
			return null;
		} else {
			return first.data;

		}
	}

	public int size() {
		return totalquesize;
	}

	public boolean isEmpty() {
		if (totalquesize == 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isFull() {
		if (totalquesize == totalquecapacity) {
			return true;
		} else {
			return false;
		}
	}

	public int totalCapacity() {
		return totalquecapacity;
	}

	public int currentCapacity() {
		int totalcapacity = (totalquecapacity - totalquesize);
		return totalcapacity;
	}

	public void clear() {
		totalquesize = 0;
		first = null;
	}

}