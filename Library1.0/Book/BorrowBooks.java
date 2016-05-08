package Book;

import java.security.interfaces.RSAKey;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;

/**
 * 实现借书的功能，或者发生丢失
 * @author king
 */
public class BorrowBooks {
	private CheckBooks myCheckBooks = new CheckBooks();

	/**
	 * 根据完整的名字，学生借书的接口
	 * 
	 * @param book
	 *            需要借阅的书本
	 * @return 如果没有这本书，或者这本书已经被借完另外返回false，否则返回true
	 */
	public boolean borrowBooks(Book book) {
		boolean flag = false;
		ArrayList<Book> mylisBooks = new ArrayList<Book>();//为了适配check函数接口用的，分装了找到的书本

		if (!myCheckBooks.checkBksByName(book, mylisBooks)) {
			System.out.println("Sorry，there is no such book!");
			return flag;
		} else {
			Connection con = ConnectDB.connectDB();
			PreparedStatement pstmt = null;
			Statement stmt = null;
			ResultSet rs = null;

			// 执行动态的语句的statement
			try {
				// 存放这本书在书库中的总量，以及被借出书的数量
				int totalNmbInLb = -1;
				int borrowedNmbInLb = -1;
				int leftNmbInLb = -1;
				
				stmt = con.createStatement();
				rs = stmt.executeQuery("select * from books");

				while (rs.next()) {
					String bookName = rs.getString("bookName");

					if (bookName.equals(book.getBookName())) { // ==和equals的区别
						totalNmbInLb = rs.getInt("totalNmb");
						borrowedNmbInLb = rs.getInt("borrowedNmb");
						leftNmbInLb = rs.getInt("leftNmb");
						
						// 判断这本书是否被借完，如果借完直接退出，否则实现借书
						if (borrowedNmbInLb == totalNmbInLb) {
							System.out
									.println("Sorry，this book all have be borrowed!");
						} else {
							flag = true;
							pstmt = con
									.prepareStatement("update books Set borrowedNmb=? Where bookName = ? ");
							pstmt.setInt(1, borrowedNmbInLb + 1);
							pstmt.setString(2, book.getBookName());
							pstmt.executeUpdate();
							
							pstmt = con
									.prepareStatement("update books Set leftNmb= ? Where bookName = ? ");
							pstmt.setInt(1, leftNmbInLb - 1);
							pstmt.setString(2, book.getBookName());
							pstmt.executeUpdate();
						}
						break;
					}
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (rs != null) {
						rs.close();
					}
					if (pstmt != null) {
						pstmt.close();
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
}