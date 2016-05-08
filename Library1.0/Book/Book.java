package Book;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Connection;

/**
 * 包装书本的信息
 * @author king
 *
 */
public class Book {
	
	private int id;
	private String bookName;
	private String author;
	private int bookPrice;
	private int totalNmb;
	private int borrowedNmb;
	private int leftNmb;
	
	public int getLeftNmb() {
		return leftNmb;
	}

	public void setLeftNmb(int leftNmb) {
		this.leftNmb = leftNmb;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getBookPrice() {
		return bookPrice;
	}

	public void setBookPrice(int bookPrice) {
		this.bookPrice = bookPrice;
	}

	public int getTotalNmb() {
		return totalNmb;
	}

	public void setTotalNmb(int totalNmb) {
		this.totalNmb = totalNmb;
	}

	public int getBorrowedNmb() {
		return borrowedNmb;
	}

	public void setBorrowedNmb(int borrowedNmb) {
		this.borrowedNmb = borrowedNmb;
	}
	
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getBookName() {
		return bookName;
	}
	
	
	
	public Book() {
		
	}

	public Book(int id, String bookName, String author, int bookPrice,
			int totalNmb, int borrowedNmb, int leftNmb) {
		super();
		this.id = id;
		this.bookName = bookName;
		this.author = author;
		this.bookPrice = bookPrice;
		this.totalNmb = totalNmb;
		this.borrowedNmb = borrowedNmb;
		this.leftNmb = leftNmb;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", bookName=" + bookName + ", author="
				+ author + ", bookPrice=" + bookPrice + ", totalNmb="
				+ totalNmb + ", borrowedNmb=" + borrowedNmb + ", leftNmb="
				+ leftNmb + "]" +"\n";
	}

	public void info(){
		System.out.println("Book [id=" + id + ", bookName=" + bookName + ", author="
				+ author + ", bookPrice=" + bookPrice + "]");
	}
}
