package alda.hash;

//Namn: Joel Larsson. Anv�ndernamn: jola0630
//Samarbetat med Erik �kerberg, anv�ndarnamn erae1372

/*
 * Denna klass ska f�rberedas f�r att kunna anv�ndas som nyckel i en hashtabell. 
 * Du f�r g�ra n�dv�ndiga �ndringar �ven i klasserna MyString och ISBN10.
 * 
 * Hashkoden ska r�knas ut p� ett effektivt s�tt och f�lja de regler och 
 * rekommendationer som finns f�r hur en hashkod ska konstrueras. Notera i en 
 * kommentar i koden hur du har t�nkt n�r du konstruerat din hashkod.
 */
public class Book {
	private MyString title;
	private MyString author;
	private ISBN10 isbn;
	private MyString content;
	private int price;

	public Book(String title, String author, String isbn, String content, int price) {
		this.title = new MyString(title);
		this.author = new MyString(author);
		this.isbn = new ISBN10(isbn);
		this.content = new MyString(content);
	}

	public MyString getTitle() {
		return title;
	}

	// Hashcode val
//  Title �r inte speciellt bra att j�mf�ra med. F�r det f�rsta kan flera b�cker ha samma titel potentiellt, men vara andra
//  upplagor av samma bok. Finns m�nga exempel av Bibeln tex. �ven om s� inte var fallet s� finns flertalet b�cker med
//  mindre ord d�r titelns bokst�ver omkastade hade kunnat bli en annan boktitel med samma ASCII v�rde (hashCode vill ha en int).
//	F�rfattare �r uppenbart inte heller optimal eftersom samma f�rfattare kan ha skrivit fler olika b�cker som 
//  d� alla mappas till samma nyckel.
//	Content blir en orimligt l�ng nyckel som visserligen skulle bli s� unik den n�stan kan bli, men att ta ut
//  v�rdet f�r en s�dan l�ng str�ng verkar vara mycket on�digt jobb. Dessutom verkar det galet att f�ra in hela
//	bokens inneh�ll via en l�ng str�ng i databasen som en parameter f�r ett bokobjekt.
//	Priset kan inte vara detsamma f�r m�nga b�cker, men kan b�de kastas om sifferm�ssigt f�r att f�'
//  priset p� m�nga andra b�cker, som resulterar i samma ASCII v�rde, men priset �r ocks� r�rligt vilket inte �r
//  optimalt f�r en nyckel.
//	Slutligen har vi ISBN10 som �r en 10 siffrig kod som �r unik f�r alla b�cker som anv�nds internationellt.
//  Om vi bortser fr�n privata utgivare har alla b�cker fr�n f�rlag detta nummer och h�lls unikt samt �r relativt
//  enkelt att knappa in samt att r�kna ut, genom att r�kna p� dess ASCII v�rde vilket ocks� l�ser
//  problemet om X �r p� slutet.  Ett problem som kan uppkomma �r hur vi j�mf�r ISBN10 med varandra i databasen
//  d�r om n�gon som skriver in fel ISBN10 n�r den registrerar boken inte f�r ett felmeddelande
//  (om det �ven st�mmer under ISBN10 nummerkonvention) och d�rf�r kan olika b�cker mappas till samma plats.
//  Ett s�tt att l�sa det hade kunnat vara att dubbelkolla med den som f�rs�ker h�mta ett objekt i listan eller
//  registrera ett objekt om det st�mmer �verrens med ett objekt som �r d�r. Registrerar man en bok s� undrar
//  databasen ��r det XX-titeln som du vill registrera� om det redan finns ett objekt med samma hashkod i listan.
//  Om det �r samma bok och �r bara en till �r det bara att klicka ok. Om inte s� kan det vara en bra ide f�r den
//  som registrerar boken att dubbelkolla att man skrivit in r�tt ISBN10 och om det st�mmer vet man att det
//  �r en bok med titelnamn XX som �r inskriven med fel ISBN10. Samma sak g�ller f�r om man h�mtar ett objekt.
//  ��r det XX titel du s�ker efter?�.
	//
	@Override
	public int hashCode() {
		char[] isbnArray = isbn.getISBN();
		int hashBuilder = 0;
		for (char c : isbnArray) {
			hashBuilder += c;
			hashBuilder = hashBuilder * 31;
		}

		return hashBuilder;
	}

	public MyString getAuthor() {
		return author;
	}

	public ISBN10 getIsbn() {
		return isbn;
	}

	public MyString getContent() {
		return content;
	}

	public int getPrice() {

		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof Book) {
			Book otherBook = (Book) other;

			char[] isbnArray = isbn.getISBN();
			char[] isbnArrayOther = otherBook.isbn.getISBN();
			for (int i = 0; i < isbnArray.length; i++) {
				if (isbnArray[i] != isbnArrayOther[i]) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}

	}

	@Override
	public String toString() {
		return String.format("\"%s\" by %s Price: %d ISBN: %s lenght: %s", title, author, price, isbn,
				content.length());
	}

}
