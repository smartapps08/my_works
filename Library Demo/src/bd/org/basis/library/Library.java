package bd.org.basis.library;

import java.util.ArrayList;

public class Library {
	ArrayList<Book> books;

	public Library() {
		books = new ArrayList<Book>();
	}

	public void addBook(Book book) {
		books.add(book);
	}

	public ArrayList<Book> search(String keyword) {
		ArrayList<Book> result = new ArrayList<Book>();

		for (Book book : this.books) {
			// iterate and match
			if (book.getTitle().contains(keyword)
					|| book.getAuthorName().contains(keyword)
					|| book.getIsbn().contains(keyword)
					|| book.getCategory().contains(keyword)) {
				result.add(book);
			}
		}

		return result;
	}

	public ArrayList<Book> search(double lower, double higher) {
		ArrayList<Book> result = new ArrayList<Book>();
		for (Book book : this.books) {
			// iterate and match
			if(book.getPrice()>=lower && book.getPrice()<=higher)
			{
				result.add(book);
			}
		}
		return result;
	}

	public ArrayList<Book> getAllBooks() {
		return this.books;
	}

}
