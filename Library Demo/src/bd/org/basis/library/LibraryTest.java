package bd.org.basis.library;

import java.util.ArrayList;

public class LibraryTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Library library = new Library();

		Book b1 = new Book("Teach Yourself C", "Herbert Schildt", "123456",
				"programming", 225.50);
		Book b2 = new Book("Teach Yourself Java", "Herbert Schildt", "345678",
				"programming", 350.50);
		Book b3 = new Book("Android Google Maps Essentials", "Ahsanul Karim",
				"567890", "programming", 550.99);
		Book b4 = new Book("Best Recipe", "John Doe", "0987654", "recipe",
				120.50);

		library.addBook(b1);
		library.addBook(b2);
		library.addBook(b3);
		library.addBook(b4);

		ArrayList<Book> searchResult = library.search("Teach");
		for (Book book : searchResult) {
			System.out.println(book.toString());
		}

		searchResult = library.search(250.0, 400.0);
		for (Book book : searchResult) {
			System.out.println(book.toString());
		}

	}

}
