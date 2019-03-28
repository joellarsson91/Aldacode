package alda.hash;

public class MyTest {

	public static void main(String[] args) {
		ISBN10 isbn = new ISBN10("9239967893");

		System.out.println(isbn.hashCode());
	}

}
