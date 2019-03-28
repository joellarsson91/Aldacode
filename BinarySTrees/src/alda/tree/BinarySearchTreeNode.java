package alda.tree;

// Användarnamn jola0630. Namn Joel Larsson.
// Samarbetat med Erik Åkerberg, användarnamn erae1372.
/**
 * 
 * Detta är den enda av de tre klasserna ni ska göra några ändringar i. (Om ni
 * inte vill lägga till fler testfall.) Det är också den enda av klasserna ni
 * ska lämna in. Glöm inte att namn och användarnamn ska stå i en kommentar
 * högst upp, och att paketdeklarationen måste plockas bort vid inlämningen för
 * att koden ska gå igenom de automatiska testerna.
 * 
 * De ändringar som är tillåtna är begränsade av följande:
 * <ul>
 * <li>Ni får INTE byta namn på klassen.
 * <li>Ni får INTE lägga till några fler instansvariabler.
 * <li>Ni får INTE lägga till några statiska variabler.
 * <li>Ni får INTE använda några loopar någonstans. Detta gäller också alterntiv
 * till loopar, så som strömmar.
 * <li>Ni FÅR lägga till fler metoder, dessa ska då vara privata.
 * <li>Ni får INTE låta NÅGON metod ta en parameter av typen
 * BinarySearchTreeNode. Enbart den generiska typen (T eller vad ni väljer att
 * kalla den), String, StringBuilder, StringBuffer, samt primitiva typer är
 * tillåtna.
 * </ul>
 * 
 * @author henrikbe
 * 
 * @param <T>
 */

public class BinarySearchTreeNode<T extends Comparable<T>> {

	private T data;
	private BinarySearchTreeNode<T> left;
	private BinarySearchTreeNode<T> right;

	public BinarySearchTreeNode(T data) {
		this.data = data;
	}

	public boolean add(T data) {

		int compared = data.compareTo(this.data);

		if (compared < 0) {
			if (left == null) {
				left = new BinarySearchTreeNode<T>(data);
				return true;
			} else
				return left.add(data);

		} else if (compared > 0) {

			if (right == null) {
				right = new BinarySearchTreeNode<T>(data);
				return true;
			}

			else {
				return right.add(data);
			}
		}
		else
			return false;
	}

	private T findMin() {

		if (left == null) {
			return data;
		} else {
			return left.findMin();
		}

	}

	private T findMax() {
		if (right == null) {
			return data;
		} else {
			return right.findMax();
		}
	}

	public BinarySearchTreeNode<T> remove(T data) {

		int compare = data.compareTo(this.data);

		if (compare == 0) {

			if (left != null && right != null) {
				this.data = right.findMin();

				right = right.remove(this.data);
				return this;

			} else if (left != null) {
				this.data = left.findMax();

				left = left.remove(this.data);
				return this;

			} else if (right != null) {
				this.data = right.findMin();
				right = right.remove(this.data);
				return this;
			}

			else {
				return null;
			}

		} else if (compare > 0) {
			if (right == null) {
				return this;
			} else {
				right = right.remove(data);
				return this;
			}
		}

		else {

			if (left == null) {
				return this;
			} else {
				left = left.remove(data);
				return this;
			}
		}

	}

	public boolean contains(T data) {

		int compared = data.compareTo(this.data);

		if (compared > 0) {

			if (right == null) {
				return false;
			}
			return right.contains(data);
		}

		else if (compared < 0) {
			if (left == null) {
				return false;
			}
			return left.contains(data);
		}

		else {
			return true;
		}

	}

	public int size() {

		if (right != null) {
			if (left != null) {
				return 1 + right.size() + left.size();
			} else
				return 1 + right.size();
		} else {
			if (left != null) {
				return 1 + left.size();
			} else {
				return 1;
			}
		}

	}

	public int depth() {

		if (left != null && right != null) {

			return 1 + (right.depth() > left.depth() ? right.depth() : left.depth());
		}

		else if (left != null) {
			return 1 + left.depth();
		}

		else if (right != null) {
			return 1 + right.depth();
		}

		else {
			return 0;
		}

	}

	public String toString() {

		if (left == null) {
			if (right == null) {
				return data.toString();
			} else {
				return data.toString() + ", " + right.toString();
			}
		} else {
			if (right == null) {
				return left.toString() + ", " + data.toString();
			} else {
				return left.toString() + ", " + data.toString() + ", " + right.toString();
			}
		}

	}

}