package alda.hash;

//Namn: Joel Larsson. Anv�ndernamn: jola0630
//Samarbetat med Erik �kerberg, anv�ndarnamn erae1372

public class LinearProbingHashTable<T> extends ProbingHashTable<T> {

	/*
	 * Denna metod ska skrivas klart. Den ska anv�nda linj�r sondering och hela
	 * tiden �ka med ett.
	 */
	@Override
	protected int findPos(T x) {
		int currentPos = myhash(x);
		while (continueProbing(currentPos, x)) {
			currentPos++;
			if (currentPos >= capacity()) {
				currentPos -= capacity() ;
			}
		}
		return currentPos;
	}

}
