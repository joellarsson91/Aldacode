package alda.hash;
//Namn: Joel Larsson. Användernamn: jola0630
//Samarbetat med Erik Åkerberg, användarnamn erae1372
public class DoubleHashingProbingHashTable<T> extends ProbingHashTable<T> {


	/*
	 * Denna metod ska skrivas klart. Den ska använda bokens förslag på andra
	 * hashalgoritm.
	 * 
	 * TODO: algoritmen måste finnas med här för dem som inte har boken.
	 */
	private int hash2(T x) {

		int primeNumber = smallerPrimeThanCapacity();

		return primeNumber - (myhash(x) % primeNumber);
	}

	@Override
	protected int findPos(T x) {
		int currentPos = myhash(x);
		int i = 1;
		while (continueProbing(currentPos, x)) {

			currentPos = myhash(x) + (i * hash2(x)) % capacity();

			if (currentPos >= capacity()) {
				currentPos = currentPos - capacity();
			}
			i++;

		}
		return currentPos;
	}

	/*
	 * Denna metod ger ett primtal mindre än tabellens storlek. Detta primtal ska
	 * användas i metoden ovan.
	 */
	protected int smallerPrimeThanCapacity() {
		int n = capacity() - 2;
		while (!isPrime(n)) {
			n -= 2;
		}
		return n;
	}

}
