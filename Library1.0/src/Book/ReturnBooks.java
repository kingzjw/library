package Book;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Connection;

public class ReturnBooks {

	/**
	 * ��������������ġ�
	 * 
	 * @param book
	 *            ������Ҫ����
	 * @return �ɹ��򷵻�true������֮��
	 */
	public boolean returnBooks(Book book) {
		boolean flag = false;
		Connection con = ConnectDB.connectDB();
		PreparedStatement pstmt = null;
		Statement stmt = null;

		// ִ�ж�̬������statement
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
					// ���û�н�����������˹������飬����
					if (borrowedNmbInLb <= 0) {
						System.out.println("�Ȿ��û�н�����������쳣"
								+ "��������returnBooks����");
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