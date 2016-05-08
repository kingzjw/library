package Book;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;

/**
 * 提供查阅书本的接口
 * 
 * @author king
 *
 */
public class CheckBooks {

	/**
	 * 打印书库中所有书本
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

			// 遍历书库，如果有类似名字的书本，显示给作者
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
	 * @param bookName 需要查找的书名
	 * @return 如果找到类似的名字，返回true，否则返回false
	 */
	public boolean checkBksByName(String bookName,
			ArrayList<Book> mylisBooks) {
		Book book = new Book();
		book.setBookName(bookName);
		
		return checkBksByName(book,mylisBooks);
		
	}
	
	/**
	 * 按照书名查找书本（如果包含传进来的书名，就 ）
	 * @param book 需要查找的书本
	 * @param mylistBooks  把查找到的书本放进list中，如果没有那么list中没有书本
	 * @return 如果找到名字类似的书本，返回true，否则返回false
	 */
	public boolean checkBksByName(Book book,
			ArrayList<Book> mylistBooks) {
		// 标志有无类似的书籍
		boolean flag = false;
		Connection con = ConnectDB.connectDB();
		Statement stmt = null;
		ResultSet res = null;
		try {
			stmt = con.createStatement();
			res = stmt.executeQuery("select * from books;");

			Book showBook = null;

			// 遍历书库，如果有类似名字的书本，显示给作者
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
	 * 按照完整的书名查找书本
	 * @param book 需要查找的书本
	 * @return 如果找到名字类似的书本，返回true，否则返回false
	 */
	public Book checkBySameName(Book book) {
		// 标志有无类似的书籍
		boolean flag = false;
		Connection con = ConnectDB.connectDB();
		Statement stmt = null;
		ResultSet res = null;
		Book showBook = null;
		
		try {
			stmt = con.createStatement();
			res = stmt.executeQuery("select * from books;");

			

			// 遍历书库，如果有类似名字的书本，显示给作者
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
	 * 按照整个书的名字，作者，书价来查找
	 * @param book  需要查找的书本
	 * @return 如果查找成功返回true，否则返回false；
	 */
	public boolean checkBksTotalSame(Book book,
			ArrayList<Book> mylisBooks) {
		// 标志有无类似的书籍
		boolean flag = false;
		Connection con = ConnectDB.connectDB();
		Statement stmt = null;
		ResultSet res = null;
		try {
			stmt = con.createStatement();
			res = stmt.executeQuery("select * from books;");

			Book showBook = new Book();

			// 遍历书库，如果有类似名字的书本，显示给作者
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
	 * 检查需要使用的ID是否和图书馆中书的序号重复
	 * @param id  书的ID
	 * @return 如果和图书馆中已有书的序号重复，返回true，否则返回false；
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

			// 遍历书库，如果有类似名字的书本，显示给作者
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