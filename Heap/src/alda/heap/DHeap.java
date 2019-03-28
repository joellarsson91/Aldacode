// Klassen i denna fil måste döpas om till DHeap för att testerna ska fungera. 
package alda.heap;

//BinaryHeap class
//
//CONSTRUCTION: with optional capacity (that defaults to 100)
//            or an array containing initial items
//
//******************PUBLIC OPERATIONS*********************
//void insert( x )       --> Insert x
//Comparable deleteMin( )--> Return and remove smallest item
//Comparable findMin( )  --> Return smallest item
//boolean isEmpty( )     --> Return true if empty; else false
//void makeEmpty( )      --> Remove all items
//******************ERRORS********************************
//Throws UnderflowException as appropriate

/**
 * Implements a binary heap. Note that all "matching" is based on the compareTo
 * method.
 * 
 * @author Mark Allen Weiss
 */
public class DHeap<AnyType extends Comparable<? super AnyType>> {

//	private static final int DEFAULT_CAPACITY = 100;
	private int currentSize; // Number of elements in heap
	private AnyType[] array; // The heap array
	private int dimension;

	/**
	 * Construct the binary heap.
	 */
	public DHeap() {
		this(2);

	}

	/**
	 * Construct the binary heap.
	 * 
	 * @param capacity the capacity of the binary heap.
	 */
	public DHeap(int dimension) {

		if (dimension < 2) {
			throw new IllegalArgumentException();
		}
		this.dimension = dimension;
		currentSize = 0;
		array = (AnyType[]) new Comparable[1]; // +1 räknar inte med nollan

	}

	AnyType get(int index) {
		return array[index];
	}

	public int size() {
		return currentSize;
	}

	/**
	 * Construct the binary heap given an array of items.
	 */
//	public DHeap(AnyType[] items, int dimension) {
//		this.dimension = dimension;
//		currentSize = items.length;
//		array = (AnyType[]) new Comparable[(currentSize + 2) * 11 / 10];
//
//		int i = 1;
//		for (AnyType item : items)
//			array[i++] = item;
//		buildHeap();
//	}

	/**
	 * Insert into the priority queue, maintaining heap order. Duplicates are
	 * allowed.
	 * 
	 * @param x the item to insert.
	 */
	public void insert(AnyType x) {
		if (currentSize == array.length - 1)
			enlargeArray(array.length * 2 + 1);

		// Percolate up
		int hole = ++currentSize;
		for (array[0] = x; hole > 1 && x.compareTo(array[parentIndex(hole)]) < 0; hole = parentIndex(hole)) {
			
			array[hole] = array[parentIndex(hole)];
			
		}
		array[hole] = x;
	}

	public int parentIndex(int child) {
		if (child < 2) {
			throw new IllegalArgumentException();
		}
//		int algoTwo = (child - (dimension-2))/dimension;
		int start = child - 1;
		for (int i = 0; i < dimension; i++) {
			if (start % dimension == 0) {
				return start / dimension;
			}
			start++;
		}
		return 0;
	}

	private void enlargeArray(int newSize) {
		AnyType[] old = array;
		array = (AnyType[]) new Comparable[newSize];
		for (int i = 0; i < old.length; i++)
			array[i] = old[i];
	}

	/**
	 * Find the smallest item in the priority queue.
	 * 
	 * @return the smallest item, or throw an UnderflowException if empty.
	 */
	public AnyType findMin() {
		if (isEmpty())
			throw new UnderflowException();
		return array[1];
	}

	/**
	 * Remove the smallest item from the priority queue.
	 * 
	 * @return the smallest item, or throw an UnderflowException if empty.
	 */
	public AnyType deleteMin() {
		if (isEmpty())
			throw new UnderflowException();

		AnyType minItem = findMin();
		array[1] = array[currentSize--];
		percolateDown(1);

		return minItem;
	}

	/**
	 * Establish heap order property from an arbitrary arrangement of items. Runs in
	 * linear time.
	 */
	private void buildHeap() {
		for (int i = currentSize / dimension; i > 0; i--)
			percolateDown(i);
	}

	/**
	 * Test if the priority queue is logically empty.
	 * 
	 * @return true if empty, false otherwise.
	 */
	public boolean isEmpty() {
		return currentSize == 0;
	}

	/**
	 * Make the priority queue logically empty.
	 */
	public void makeEmpty() {
		currentSize = 0;
	}

	/**
	 * Internal method to percolate down in the heap.
	 * 
	 * @param hole the index at which the percolate begins.
	 */
	public int firstChildIndex(int parentIndex) {
		if (parentIndex <= 0) {
			throw new IllegalArgumentException();
		}

		return parentIndex * dimension + (2 - dimension);
	}

	private void percolateDown(int hole) {

		int child;
		int minChild = 0;
		AnyType tmp = array[hole];

		for (; firstChildIndex(hole) <= currentSize; hole = child) {
			child = firstChildIndex(hole);
			for (int i = 0; i < dimension - 1; i++) {
				if (child != currentSize && array[child + 1].compareTo(array[child]) < 0) {

					if (array[child + 1].compareTo(array[minChild]) < 0) {
						minChild = child + 1;
					}

					child++;
				}
			}
			if (array[minChild].compareTo(tmp) < 0)
				array[hole] = array[minChild];
			else
				break;
		}
		array[hole] = tmp;
	}

	// Test program
	public static void main(String[] args) {
		int numItems = 10000;
		DHeap<Integer> h = new DHeap<>();
		int i = 37;

		for (i = 37; i != 0; i = (i + 37) % numItems)
			h.insert(i);
		for (i = 1; i < numItems; i++)
			if (h.deleteMin() != i)
				System.out.println("Oops! " + i);
	}
}