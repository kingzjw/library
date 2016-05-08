package Book;

import java.security.interfaces.RSAKey;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;

/**
 * ʵ�ֽ���Ĺ��ܣ����߷�����ʧ
 * @author king
 */
public class BorrowBooks {
	private CheckBooks myCheckBooks = new CheckBooks();

	/**
	 * �������������֣�ѧ������Ľӿ�
	 * 
	 * @param book
	 *            ��Ҫ���ĵ��鱾
	 * @return ���û���Ȿ�飬�����Ȿ���Ѿ����������ⷵ��false�����򷵻�true
	 */
	public boolean borrowBooks(Book book) {
		boolean flag = false;
		ArrayList<Book> mylisBooks = new ArrayList<Book>();//Ϊ������check�����ӿ��õģ���װ���ҵ����鱾

		if (!myCheckBooks.checkBksByName(book, mylisBooks)) {
			System.out.println("Sorry��there is no such book!");
			return flag;
		} else {
			Connection con = ConnectDB.connectDB();
			PreparedStatement pstmt = null;
			Statement stmt = null;
			ResultSet rs = null;

			// ִ�ж�̬������statement
			try {
				// ����Ȿ��������е��������Լ�������������
				int totalNmbInLb = -1;
				int borrowedNmbInLb = -1;
				int leftNmbInLb = -1;
				
				stmt = con.createStatement();
				rs = stmt.executeQuery("select * from books");

				while (rs.next()) {
					String bookName = rs.getString("bookName");

					if (bookName.equals(book.getBookName())) { // ==��equals������
						totalNmbInLb = rs.getInt("totalNmb");
						borrowedNmbInLb = rs.getInt("borrowedNmb");
						leftNmbInLb = rs.getInt("leftNmb");
						
						// �ж��Ȿ���Ƿ񱻽��꣬�������ֱ���˳�������ʵ�ֽ���
						if (borrowedNmbInLb == totalNmbInLb) {
							System.out
									.println("Sorry��this book all have be borrowed!");
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