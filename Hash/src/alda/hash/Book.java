package alda.hash;

//Namn: Joel Larsson. Användernamn: jola0630
//Samarbetat med Erik Åkerberg, användarnamn erae1372

/*
 * Denna klass ska förberedas för att kunna användas som nyckel i en hashtabell. 
 * Du får göra nödvändiga ändringar även i klasserna MyString och ISBN10.
 * 
 * Hashkoden ska räknas ut på ett effektivt sätt och följa de regler och 
 * rekommendationer som finns för hur en hashkod ska konstrueras. Notera i en 
 * kommentar i koden hur du har tänkt när du konstruerat din hashkod.
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
//  Title är inte speciellt bra att jämföra med. För det första kan flera böcker ha samma titel potentiellt, men vara andra
//  upplagor av samma bok. Finns många exempel av Bibeln tex. Även om så inte var fallet så finns flertalet böcker med
//  mindre ord där titelns bokstäver omkastade hade kunnat bli en annan boktitel med samma ASCII värde (hashCode vill ha en int).
//	Författare är uppenbart inte heller optimal eftersom samma författare kan ha skrivit fler olika böcker som 
//  då alla mappas till samma nyckel.
//	Content blir en orimligt lång nyckel som visserligen skulle bli så unik den nästan kan bli, men att ta ut
//  värdet för en sådan lång sträng verkar vara mycket onödigt jobb. Dessutom verkar det galet att föra in hela
//	bokens innehåll via en lång sträng i databasen som en parameter för ett bokobjekt.
//	Priset kan inte vara detsamma för många böcker, men kan både kastas om siffermässigt för att få'
//  priset på många andra böcker, som resulterar i samma ASCII värde, men priset är också rörligt vilket inte är
//  optimalt för en nyckel.
//	Slutligen har vi ISBN10 som är en 10 siffrig kod som är unik för alla böcker som används internationellt.
//  Om vi bortser från privata utgivare har alla böcker från förlag detta nummer och hålls unikt samt är relativt
//  enkelt att knappa in samt att räkna ut, genom att räkna på dess ASCII värde vilket också löser
//  problemet om X är på slutet.  Ett problem som kan uppkomma är hur vi jämför ISBN10 med varandra i databasen
//  där om någon som skriver in fel ISBN10 när den registrerar boken inte får ett felmeddelande
//  (om det även stämmer under ISBN10 nummerkonvention) och därför kan olika böcker mappas till samma plats.
//  Ett sätt att lösa det hade kunnat vara att dubbelkolla med den som försöker hämta ett objekt i listan eller
//  registrera ett objekt om det stämmer överrens med ett objekt som är där. Registrerar man en bok så undrar
//  databasen ”Är det XX-titeln som du vill registrera” om det redan finns ett objekt med samma hashkod i listan.
//  Om det är samma bok och är bara en till är det bara att klicka ok. Om inte så kan det vara en bra ide för den
//  som registrerar boken att dubbelkolla att man skrivit in rätt ISBN10 och om det stämmer vet man att det
//  är en bok med titelnamn XX som är inskriven med fel ISBN10. Samma sak gäller för om man hämtar ett objekt.
//  ”Är det XX titel du söker efter?”.
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
