package alda.tree;
public class TestTree {

	public static void main(String[] args) {

		BinarySearchTree<Integer> testTree = new BinarySearchTree<>();

		
		
		testTree.add(5);
		testTree.add(4);
		testTree.add(2);
		testTree.add(3);
		testTree.add(6);
		testTree.add(1);
		System.out.println("Storleken �r " + testTree.size() + " innan borttagning.");
		System.out.println("Tr�det �r " + testTree.toString());
		testTree.remove(2);
		System.out.println("Storleken �r " + testTree.size() + " efter borttagning.");
		System.out.println("Tr�det �r " + testTree.toString());
		//		testTree.add(7);
//		testTree.add(5);
//		testTree.add(3);
//		testTree.add(4);
//		testTree.add(9);
//		testTree.add(8);
//		testTree.add(10);
//		testTree.add(11);
//		testTree.add(13);
//		System.out.println("H�r �r tr�dets siffor: " + testTree.size());
//		System.out.println("Det finns minst: " + testTree.contains(7) + testTree.contains(11));
//		System.out.println(testTree.toString());
//		System.out.println(testTree.depth());
		
	}

}
