package Book;


import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



import com.mysql.jdbc.Connection;



/**
 * �������һ��������з�װ��ι����������Ľӿ�
 * 
 * @author king
 *
 */
public class ListBooks {
	private static final int maxBooks = 10;
	// ���Ŀ¼��ѧ���Ľ���Ŀ¼������Ա�Ĺ���Ŀ¼�ȣ�
	// ���Ŀ¼
	public ArrayList<Book> listBooks = null;
	private String name = null;

	private int totalNmb = 0;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private boolean isStudent = false;

	/**
	 * �����������ô�����ݿ��е����ݸ��Ƶ�listbooks�У����û�У��򴴽����
	 * 
	 * @param isStudent
	 *            ���ݿ��б�������
	 */
	public ListBooks(boolean isStudent) {
		this.isStudent = isStudent;
		listBooks = new ArrayList<Book>();

		Connection con = ConnectDB.connectDB();
		Statement stmt = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;

		try {
			// ���û�д���������ô����������
			if (isStudent) {
				cstmt = con.prepareCall("{call createStuList}");
				cstmt.execute();
				setName("stuList");
				stmt = con.createStatement();
				rs = stmt.executeQuery("select * from stuList");
			} else {
				cstmt = con.prepareCall("{call createmngList}");
				cstmt.execute();
				setName("mngList");
				stmt = con.createStatement();
				rs = stmt.executeQuery("select * from mngList");
			}

			Book showBook = null;
			// �������ݿ����е��鱾��list��
			while (rs.next()) {
				
				showBook = new Book();
				showBook.setId(rs.getInt(1));
				showBook.setBookName(rs.getString(2));
				showBook.setAuthor(rs.getString(3));
				showBook.setBookPrice(rs.getInt(4));
				showBook.setTotalNmb(rs.getInt(5));
				showBook.setBorrowedNmb(rs.getInt(6));
				showBook.setLeftNmb(rs.getInt(7));
				listBooks.add(showBook);
				totalNmb++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (cstmt != null)
					cstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		ConnectDB.disConnectDB(con);
	}

	public int getBkNmb() {
		return totalNmb;
	}

	/**
	 * ͨ���Լ���ı���е��鱾��������totalnumber��ֵ
	 */
	public void setBkNmb() {
		totalNmb = listBooks.size();
	}

	public boolean isStudent() {
		return isStudent;
	}

	public void setStudent(boolean isStudent) {
		this.isStudent = isStudent;
	}

	/**
	 * ������
	 * 
	 * @return �ɹ���շ���true�����򷵻�false
	 */
	public boolean empty() {
		Connection con = ConnectDB.connectDB();
		PreparedStatement pstmt = null;

		try {
			pstmt = con.prepareStatement("delete * from ?");
			pstmt.setString(1, name);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		listBooks.removeAll(listBooks);// ɾ�����е��鱾
		setBkNmb();// ����total������

		// �ж��Ƿ����
		if (totalNmb == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * �ж��Ƿ�Ϊ��
	 * 
	 * @return ���Ϊ�գ�����true������֮��
	 */
	public boolean isEmpty() {
		if (totalNmb == 0)
			return true;
		return false;
	}

	/**
	 * ��ѧ�����ŵĽӿڣ��ж��Ƿ��Լ��ﵽ�����������������
	 * 
	 * @return ����ﵽ���޷���true�����򷵻�false��
	 */
	public boolean isFull() {
		if (totalNmb == maxBooks)
			return true;
		return false;
	}

	/**
	 * ��ʾ�������Ŀ¼
	 */
	public void showList() {
		if (!this.isEmpty()) {
			Book myBook = null;
			
			for(int i = 0; i< listBooks.size(); i++){
				myBook = listBooks.get(i);
				myBook.info();
			}
		} else {
			System.out.println("this is no books in your List!");
		}
	}

	/**
	 * ��list�з��鱾��
	 * 
	 * @return����ųɹ��ˣ�����true�����򷵻�false��
	 */
	public boolean addListBook(Book book) {
		if (isFull() && isStudent) {
			System.out.println("�Բ������Ľ����鱾�����ﵽ���ޣ���黹���ɱ������ĵ��鼮���������!");
			return false;
		} else {
			Book myBook = null;
			//����Ƿ��Ѿ����һ�����鱾
			for(int i= 0;i<listBooks.size(); i++) {
				myBook = listBooks.get(i);
				if(myBook.getId() ==book.getId()){
					System.out.println("���Ѿ�����Ȿ���ˣ����ܽ���ͬ���鱾��");
					return false;
				}
			}
			listBooks.add(book);
			totalNmb++;

			// �����ݿ��б�����鱾
			Connection con = ConnectDB.connectDB();
			PreparedStatement pstmt = null;
			try {
				if(isStudent) {
					pstmt = con.prepareStatement("insert stulist values(?,?,?,?,?,?,?);");
				}else {
					pstmt = con.prepareStatement("insert mnglist values(?,?,?,?,?,?,?);");
				}
		
				pstmt.setInt(1, book.getId());
				pstmt.setString(2, book.getBookName());
				pstmt.setString(3, book.getAuthor());
				pstmt.setInt(4, book.getBookPrice());
				pstmt.setInt(5, book.getTotalNmb());
				pstmt.setInt(6, book.getBorrowedNmb());
				pstmt.setInt(7, book.getLeftNmb());
				pstmt.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (pstmt != null) {
						pstmt.close();
					}

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			ConnectDB.disConnectDB(con);
			return true;
		}
	}

	/**
	 * ��һ�ű���е����ݼӵ�����һ�ű���
	 * 
	 * @param myListBooks
	 *            ��Ҫ��ӵı��
	 * @return ����ɹ���ӷ���true������֮false��
	 */
	public boolean addListBook(ListBooks myListBooks) {
		if (!isFull() && isStudent) {
			System.out.println("�Բ������Ľ����鱾�����ﵽ���ޣ���黹���ɱ������ĵ��鼮���������!");
			return false;
		} else {
			Book book = null;

			if (myListBooks.listBooks.size() == 0)
				return false;

			for (int i = 0; i < myListBooks.listBooks.size(); i++) {
				book = myListBooks.listBooks.get(i);
				addListBook(book);
			}
			return true;
		}
	}

	/**
	 * ���б���ȥ�����鱾
	 * 
	 * @return ����ɹ�ȥ������true�����û���Ȿ������������������false��
	 */
	public boolean removeListBook(Book book) {
		boolean isFind = false;//��¼�Ƿ��Ѿ��ҵ�
		
		if (isEmpty()) {
			System.out.println("����鱾�б�������û���飡");
			return isFind;
		}

		Book myBook = null;
		for (int i = 0; i < listBooks.size(); i++) {
			myBook = listBooks.get(i);

			if (book.getBookName().equals(myBook.getBookName())) {
				isFind = true;
				listBooks.remove(i);
				totalNmb--;
				break;
			}
		}

		if(isFind != true) {
			System.out.println("��û�н���Ȿ�飬��������Ҫ���飡");
			return isFind;
		}
		
		// ���б���ɾ���Ȿ�鱾
		Connection con = ConnectDB.connectDB();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement("library");
			if (isStudent) {

				rs = pstmt.executeQuery("select * from stuList");
			} else {

				rs = pstmt.executeQuery("select * from mngList");
			}
			;

			while (rs.next()) {
				String bookName = rs.getString("bookName");

				// ���û�н�����������˹������飬����
				if (bookName.equals(book.getBookName())) {
					if(isStudent){
						pstmt = con
								.prepareStatement("delete from stulist where bookName = ?");						
					}else {
						pstmt = con
								.prepareStatement("delete from mnglist where bookName = ?");
					}
					pstmt.setString(1, book.getBookName());
					pstmt.executeUpdate();
					break;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		ConnectDB.disConnectDB(con);
		return isFind;
	}
}
