package alda.hash;
//Namn: Joel Larsson. Anv�ndernamn: jola0630
//Samarbetat med Erik �kerberg, anv�ndarnamn erae1372
public class DoubleHashingProbingHashTable<T> extends ProbingHashTable<T> {


	/*
	 * Denna metod ska skrivas klart. Den ska anv�nda bokens f�rslag p� andra
	 * hashalgoritm.
	 * 
	 * TODO: algoritmen m�ste finnas med h�r f�r dem som inte har boken.
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
	 * Denna metod ger ett primtal mindre �n tabellens storlek. Detta primtal ska
	 * anv�ndas i metoden ovan.
	 */
	protected int smallerPrimeThanCapacity() {
		int n = capacity() - 2;
		while (!isPrime(n)) {
			n -= 2;
		}
		return n;
	}

}
