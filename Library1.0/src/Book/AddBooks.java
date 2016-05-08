package Book;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;

/**
 * �ṩ��ͼ��ݻ��飬����鱾
 * 
 * @author king
 *
 */
public class AddBooks {

	/**
	 * ��ӹ黹�鱾�Ľӿ�
	 * 
	 * @param book
	 *            ��Ҫ��ӵ��鱾�����߹黹���鱾
	 * @return �����ӹ黹�ɹ�������true�����򷵻�false��
	 */
	public boolean addBooks(Book book) {
		CheckBooks myCheckBooks = new CheckBooks();
		ArrayList<Book> mylisBooks = new ArrayList<Book>();

		if (myCheckBooks.checkBksTotalSame(book, mylisBooks)) {
			// ���������������е���ȫһ��
			return addBookHave(book);
		} else if (myCheckBooks.checkId(book.getId())) {
			//�����û���Ȿ�飬��������ظ���
			System.out.println("����д�޸��Ȿ�����ţ��������鱾������ظ���");
			return false;
		} else
			//�����Ҫ��ӵ��鱾�У����ݿ��ܱ�����û��.
			return addBookNotHave(book);
	}

	/**
	 * ��������û�е��鱾
	 * 
	 * @param book
	 *            ��Ҫ��ӹ黹���鱾
	 * @return �����ӹ黹�ɹ�������true�����򷵻�false��
	 */
	private boolean addBookNotHave(Book book) {
		Connection con = ConnectDB.connectDB();
		PreparedStatement pstmt = null;

		// ִ�ж�̬������statement
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
	 * ��ӹ黹����б������е��鱾
	 * 
	 * @param book
	 *            ��Ҫ��ӹ黹���鱾
	 * @return �����ӹ黹�ɹ�������true�����򷵻�false��
	 */
	private boolean addBookHave(Book book) {
		Connection con = ConnectDB.connectDB();
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;

		// ִ�ж�̬������statement
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
