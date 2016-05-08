package Book;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;

/**
 * �ṩ�����鱾�Ľӿ�
 * 
 * @author king
 *
 */
public class CheckBooks {

	/**
	 * ��ӡ����������鱾
	 */
	public void showAllBooks() {
		ArrayList<Book > mylisBooks = new ArrayList<Book>();
		
		Connection con = ConnectDB.connectDB();
		Statement stmt = null;
		ResultSet res = null;
		try {
			stmt = con.createStatement();
			res = stmt.executeQuery("select * from books;");

			Book showBook = null;

			// ������⣬������������ֵ��鱾����ʾ������
			while (res.next()) {
				
					showBook = new Book();
					showBook.setId(res.getInt(1));
					showBook.setBookName(res.getString(2));
					showBook.setAuthor(res.getString(3));
					showBook.setBookPrice(res.getInt(4));
					showBook.setTotalNmb(res.getInt(5));
					showBook.setBorrowedNmb(res.getInt(6));
					showBook.setLeftNmb(res.getInt(7));

					boolean bool = mylisBooks.add(showBook);
				}
			

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				if (res != null) {
					res.close();
				}

				if (stmt != null) {
					stmt.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		ConnectDB.disConnectDB(con);
		System.out.println(mylisBooks);
	}


	/**
	 * 
	 * @param bookName ��Ҫ���ҵ�����
	 * @return ����ҵ����Ƶ����֣�����true�����򷵻�false
	 */
	public boolean checkBksByName(String bookName,
			ArrayList<Book> mylisBooks) {
		Book book = new Book();
		book.setBookName(bookName);
		
		return checkBksByName(book,mylisBooks);
		
	}
	
	/**
	 * �������������鱾������������������������� ��
	 * @param book ��Ҫ���ҵ��鱾
	 * @param mylistBooks  �Ѳ��ҵ����鱾�Ž�list�У����û����ôlist��û���鱾
	 * @return ����ҵ��������Ƶ��鱾������true�����򷵻�false
	 */
	public boolean checkBksByName(Book book,
			ArrayList<Book> mylistBooks) {
		// ��־�������Ƶ��鼮
		boolean flag = false;
		Connection con = ConnectDB.connectDB();
		Statement stmt = null;
		ResultSet res = null;
		try {
			stmt = con.createStatement();
			res = stmt.executeQuery("select * from books;");

			Book showBook = null;

			// ������⣬������������ֵ��鱾����ʾ������
			while (res.next()) {
				String bkNm = res.getString(2);
				if (bkNm.indexOf(book.getBookName()) != -1) {
					showBook = new Book();
					flag = true;
					showBook.setId(res.getInt(1));
					showBook.setBookName(bkNm);
					showBook.setAuthor(res.getString(3));
					showBook.setBookPrice(res.getInt(4));
					showBook.setTotalNmb(res.getInt(5));
					showBook.setBorrowedNmb(res.getInt(6));
					showBook.setLeftNmb(res.getInt(7));

					boolean bool = mylistBooks.add(showBook);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				if (res != null) {
					res.close();
				}

				if (stmt != null) {
					stmt.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		ConnectDB.disConnectDB(con);
		return flag;
	}
	
	/**
	 * �������������������鱾
	 * @param book ��Ҫ���ҵ��鱾
	 * @return ����ҵ��������Ƶ��鱾������true�����򷵻�false
	 */
	public Book checkBySameName(Book book) {
		// ��־�������Ƶ��鼮
		boolean flag = false;
		Connection con = ConnectDB.connectDB();
		Statement stmt = null;
		ResultSet res = null;
		Book showBook = null;
		
		try {
			stmt = con.createStatement();
			res = stmt.executeQuery("select * from books;");

			

			// ������⣬������������ֵ��鱾����ʾ������
			while (res.next()) {
				String bkNm = res.getString(2);
				if (bkNm.equals(book.getBookName())) {
					showBook = new Book();
					flag = true;
					showBook.setId(res.getInt(1));
					showBook.setBookName(bkNm);
					showBook.setAuthor(res.getString(3));
					showBook.setBookPrice(res.getInt(4));
					showBook.setTotalNmb(res.getInt(5));
					showBook.setBorrowedNmb(res.getInt(6));
					showBook.setLeftNmb(res.getInt(7));

				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				if (res != null) {
					res.close();
				}

				if (stmt != null) {
					stmt.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		ConnectDB.disConnectDB(con);
		return showBook;
	}
	
	/**
	 * ��������������֣����ߣ����������
	 * @param book  ��Ҫ���ҵ��鱾
	 * @return ������ҳɹ�����true�����򷵻�false��
	 */
	public boolean checkBksTotalSame(Book book,
			ArrayList<Book> mylisBooks) {
		// ��־�������Ƶ��鼮
		boolean flag = false;
		Connection con = ConnectDB.connectDB();
		Statement stmt = null;
		ResultSet res = null;
		try {
			stmt = con.createStatement();
			res = stmt.executeQuery("select * from books;");

			Book showBook = new Book();

			// ������⣬������������ֵ��鱾����ʾ������
			while (res.next()) {
				String bkNm = res.getString(2);
				String author = res.getString(3);
				int bkPrice = res.getInt(4);
				if (bkNm.equals(book.getBookName()) && author.equals(author)
						&& bkPrice == book.getBookPrice()  ) {
					flag = true;
					showBook.setId(res.getInt(1));
					showBook.setBookName(bkNm);
					showBook.setAuthor(res.getString(3));
					showBook.setBookPrice(res.getInt(4));
					showBook.setTotalNmb(res.getInt(5));
					showBook.setBorrowedNmb(res.getInt(6));
					showBook.setLeftNmb(res.getInt(7));
					
					mylisBooks.add(showBook);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				if (res != null) {
					res.close();
				}

				if (stmt != null) {
					stmt.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		ConnectDB.disConnectDB(con);
		return flag;
	}

	
	/**
	 * �����Ҫʹ�õ�ID�Ƿ��ͼ������������ظ�
	 * @param id  ���ID
	 * @return �����ͼ����������������ظ�������true�����򷵻�false��
	 */
	public boolean checkId(int id){
		
		boolean flag = false;
		Connection con = ConnectDB.connectDB();
		Statement stmt = null;
		ResultSet res = null;
		try {	
			stmt = con.createStatement();
			res = stmt.executeQuery("select * from books;");

			Book showBook = new Book();

			// ������⣬������������ֵ��鱾����ʾ������
			while (res.next()) {
				int bookId = res.getInt(1);
				if (bookId == id) {
					flag = true;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				if (res != null) {
					res.close();
				}

				if (stmt != null) {
					stmt.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		ConnectDB.disConnectDB(con);
		return flag;
	}
	
}