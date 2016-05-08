package Test;

import java.util.ArrayList;
import java.util.zip.Checksum;

import Book.AddBooks;
import Book.Book;
import Book.BorrowBooks;
import Book.CheckBooks;
import Book.ListBooks;
import Book.ReturnBooks;
import Person.Student;


/**
 * 测试图书馆的主函数
 * @author king
 *
 */
public class testLibrary {
	
	public static void main(String[] args) {
		Book book = new Book();
		book.setBookName("家");
		Book book_add = new Book(12937,"变形记","卡夫卡",50,3,0,3);
		
		
		CheckBooks myBooks = new CheckBooks();
		ArrayList<Book> arrlist = new ArrayList<Book>();
		
		
		ListBooks myListBooks = new ListBooks(true);
		
		myBooks.checkBksByName(book, arrlist);
		book = arrlist.get(0);
		
		
		myListBooks.addListBook(book);
		//myListBooks.removeListBook(book);
		myListBooks.showList();
		System.out.println(myListBooks.getBkNmb());
	}
}

