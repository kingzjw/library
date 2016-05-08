package Person;

import java.util.ArrayList;

import Book.Book;
import Book.BorrowBooks;
import Book.CheckBooks;
import Book.ListBooks;
import Book.ReturnBooks;

/**
 * ͼ���ѧ����
 * 
 * @author king
 *
 */
public class Student {
	public ListBooks myListBooks = new ListBooks(true); // ѧ���Ľ�����嵥
	private String stuName;
	private int idCard;

	public Student(String stuName, int idCard) {
		this.stuName = stuName;
		this.idCard = idCard;
	}

	/**
	 * �ӿڽ���
	 * 
	 * @param book
	 *            ��Ҫ���ĵ��鱾
	 * @return �ɹ����飬����true�����򷵻�false��
	 */
	public boolean borrowBook(Book book) {
		BorrowBooks borrowBooks = new BorrowBooks();
		CheckBooks checkBooks = new CheckBooks();
		
		Book bookInLb = checkBooks.checkBySameName(book);
		myListBooks.addListBook(bookInLb);
		return borrowBooks.borrowBooks(bookInLb);
	}

	/**
	 * �������ֲ����鱾
	 * 
	 * @param book
	 *            ��Ҫ���ĵ��鱾
	 * @return �ɹ����飬����true�����򷵻�false��
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
	 * ����
	 * 
	 * @param book
	 *            ��Ҫ������
	 * @return �ɹ����飬����true�����򷵻�false��
	 */
	public boolean returnBooks(Book book) {
		ReturnBooks myReturnBooks = new ReturnBooks();
		CheckBooks checkBooks = new CheckBooks();
		Book bookInLb = checkBooks.checkBySameName(book);
		
		myListBooks.removeListBook(bookInLb);
		return myReturnBooks.returnBooks(bookInLb);
	}

}
