package bd.org.basis.onlinelibrary;

import java.io.Serializable;

public class Book implements Serializable {
	private String title;
	private String authorName;
	private String isbn;
	private String category;
	private double price;

	public Book() {

	}

	public Book(String title, String authorName, String isbn, String category,
			double price) {
		super();
		this.title = title;
		this.authorName = authorName;
		this.isbn = isbn;
		this.category = category;
		this.price = price;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String toString() {
		return "[Title: " + title + " Author: " + authorName + " ISBN: " + isbn
				+ " Category: " + category + " Price: " + price + "]";
	}
}
