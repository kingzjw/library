package Person;

import java.util.ArrayList;

import Book.Book;
import Book.BorrowBooks;
import Book.CheckBooks;
import Book.ListBooks;
import Book.ReturnBooks;

/**
 * 图书馆学生类
 * 
 * @author king
 *
 */
public class Student {
	public ListBooks myListBooks = new ListBooks(true); // 学生的借书的清单
	private String stuName;
	private int idCard;

	public Student(String stuName, int idCard) {
		this.stuName = stuName;
		this.idCard = idCard;
	}

	/**
	 * 接口借书
	 * 
	 * @param book
	 *            需要借阅的书本
	 * @return 成功借书，返回true，否则返回false；
	 */
	public boolean borrowBook(Book book) {
		BorrowBooks borrowBooks = new BorrowBooks();
		CheckBooks checkBooks = new CheckBooks();
		
		Book bookInLb = checkBooks.checkBySameName(book);
		myListBooks.addListBook(bookInLb);
		return borrowBooks.borrowBooks(bookInLb);
	}

	/**
	 * 按照名字查阅书本
	 * 
	 * @param book
	 *            需要查阅的书本
	 * @return 成功借书，返回true，否则返回false；
	 */

	public boolean checkBooks(Book book) {
		CheckBooks checkBooks = new CheckBooks();
		ArrayList<Book> showList = new ArrayList<Book>();
		Book mbook = null;
		boolean flag = checkBooks.checkBksByName(book, showList);
		
		for(int i = 0; i < showList.size(); i++) {
			mbook = showList.get(i);
			mbook.toString();
		}
		return flag;
	}

	/**
	 * 还书
	 * 
	 * @param book
	 *            需要还的书
	 * @return 成功借书，返回true，否则返回false；
	 */
	public boolean returnBooks(Book book) {
		ReturnBooks myReturnBooks = new ReturnBooks();
		CheckBooks checkBooks = new CheckBooks();
		Book bookInLb = checkBooks.checkBySameName(book);
		
		myListBooks.removeListBook(bookInLb);
		return myReturnBooks.returnBooks(bookInLb);
	}

}
