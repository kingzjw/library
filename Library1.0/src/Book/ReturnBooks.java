package Book;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Connection;

public class ReturnBooks {

	/**
	 * 根据书名来还书的。
	 * 
	 * @param book
	 *            传入需要的书
	 * @return 成功则返回true，否则反之。
	 */
	public boolean returnBooks(Book book) {
		boolean flag = false;
		Connection con = ConnectDB.connectDB();
		PreparedStatement pstmt = null;
		Statement stmt = null;

		// 执行动态的语句的statement
		try {
			int borrowedNmbInLb = 0;
			int leftNmbInLb = 0;

			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from books");
			while (rs.next()) {
				String bookName = rs.getString("bookName");
				borrowedNmbInLb = rs.getInt("borrowedNmb");
				leftNmbInLb = rs.getInt("leftNmb");
				if (bookName.equals(book.getBookName())) {
					// 如果没有借出过，就有人过来还书，报错
					if (borrowedNmbInLb <= 0) {
						System.out.println("这本书没有借出过，发生异常"
								+ "错误，我在returnBooks类中");
						break;
					} else {
						flag = true;
						
						
						pstmt = con
								.prepareStatement("update books Set borrowedNmb=? Where bookName = ? ");
						pstmt.setInt(1, borrowedNmbInLb - 1);
						pstmt.setString(2, book.getBookName());
						pstmt.executeUpdate();
						pstmt = con
								.prepareStatement("update books Set leftNmb =? Where bookName = ? ");
						pstmt.setInt(1, leftNmbInLb + 1);
						pstmt.setString(2, book.getBookName());
						pstmt.executeUpdate();
						break;
					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
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