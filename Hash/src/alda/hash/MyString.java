package alda.hash;

//Namn: Joel Larsson. Anv�ndernamn: jola0630
//Samarbetat med Erik �kerberg, anv�ndarnamn erae1372

public class MyString {

	private char[] data;

	public MyString(String title) {
		data = title.toCharArray();
	}

	public Object length() {
		return data.length;
	}

	@Override
	public String toString() {
		return new String(data);
	}

}
