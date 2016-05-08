package Book;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;

/**
 * 提供往图书馆还书，添加书本
 * 
 * @author king
 *
 */
public class AddBooks {

	/**
	 * 添加归还书本的接口
	 * 
	 * @param book
	 *            需要添加的书本，或者归还的书本
	 * @return 如果添加归还成功，返回true，否则返回false。
	 */
	public boolean addBooks(Book book) {
		CheckBooks myCheckBooks = new CheckBooks();
		ArrayList<Book> mylisBooks = new ArrayList<Book>();

		if (myCheckBooks.checkBksTotalSame(book, mylisBooks)) {
			// 如果输入的书和书库中的完全一致
			return addBookHave(book);
		} else if (myCheckBooks.checkId(book.getId())) {
			//书库中没有这本书，但是序号重复了
			System.out.println("请重写修改这本书的序号（和已有书本的序号重复）");
			return false;
		} else
			//如果需要添加的书本中，数据库总本来就没有.
			return addBookNotHave(book);
	}

	/**
	 * 添加书库中没有的书本
	 * 
	 * @param book
	 *            需要添加归还的书本
	 * @return 如果添加归还成功，返回true，否则返回false。
	 */
	private boolean addBookNotHave(Book book) {
		Connection con = ConnectDB.connectDB();
		PreparedStatement pstmt = null;

		// 执行动态的语句的statement
		try {
			pstmt = con.prepareStatement("insert books values(?,?,?,?,?,?,?);");
			pstmt.setInt(1, book.getId());
			pstmt.setString(2, book.getBookName());
			pstmt.setString(3, book.getAuthor());
			pstmt.setLong(4, book.getBookPrice());
			pstmt.setLong(5, book.getTotalNmb());
			pstmt.setLong(6, book.getBorrowedNmb());
			pstmt.setLong(7, book.getLeftNmb());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		ConnectDB.disConnectDB(con);
		return true;
	}

	/**
	 * 添加归还书库中本来就有的书本
	 * 
	 * @param book
	 *            需要添加归还的书本
	 * @return 如果添加归还成功，返回true，否则返回false。
	 */
	private boolean addBookHave(Book book) {
		Connection con = ConnectDB.connectDB();
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;

		// 执行动态的语句的statement
		try {
			int totalNmbInLb = 0;
			int leftNmbInLb = 0;
			stmt = con.createStatement();
			rs = stmt.executeQuery("select * from books");
			
			while (rs.next()) {
				String bookName = rs.getString("bookName");

				if (bookName.equals(book.getBookName())) {
					totalNmbInLb = rs.getInt("totalNmb");
					leftNmbInLb = rs.getInt("leftNmb");
					break;
				}
			}

			pstmt = con
					.prepareStatement("update books Set totalNmb=? Where bookName = ? ");
			pstmt.setInt(1, totalNmbInLb + book.getTotalNmb());
			pstmt.setString(2, book.getBookName());
			pstmt.executeUpdate();
			pstmt = con
					.prepareStatement("update books Set leftNmb=? Where bookName = ? ");
			pstmt.setInt(1, leftNmbInLb + book.getTotalNmb());
			pstmt.setString(2, book.getBookName());
			pstmt.executeUpdate();

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

		return true;
	}

}
