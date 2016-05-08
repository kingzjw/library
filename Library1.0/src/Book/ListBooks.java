package Book;


import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



import com.mysql.jdbc.Connection;



/**
 * 这个类是一个书表，其中分装如何管理整个表格的接口
 * 
 * @author king
 *
 */
public class ListBooks {
	private static final int maxBooks = 10;
	// 书的目录（学生的借书目录，管理员的购书目录等）
	// 清空目录
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
	 * 如果这个表格，那么把数据库中的数据复制到listbooks中，如果没有，则创建表格。
	 * 
	 * @param isStudent
	 *            数据库中表格的名字
	 */
	public ListBooks(boolean isStudent) {
		this.isStudent = isStudent;
		listBooks = new ArrayList<Book>();

		Connection con = ConnectDB.connectDB();
		Statement stmt = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;

		try {
			// 如果没有创建过，那么创建这个表格。
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
			// 复制数据库表格中的书本到list中
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
	 * 通过对计算的表格中的书本数量，给totalnumber赋值
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
	 * 清空书表。
	 * 
	 * @return 成功清空返回true，否则返回false
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

		listBooks.removeAll(listBooks);// 删除所有的书本
		setBkNmb();// 更新total的数量

		// 判断是否清空
		if (totalNmb == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断是否为空
	 * 
	 * @return 如果为空，返回true，否则反之。
	 */
	public boolean isEmpty() {
		if (totalNmb == 0)
			return true;
		return false;
	}

	/**
	 * 对学生开放的接口，判断是否以及达到借书数量的最大上限
	 * 
	 * @return 如果达到上限返回true，否则返回false。
	 */
	public boolean isFull() {
		if (totalNmb == maxBooks)
			return true;
		return false;
	}

	/**
	 * 显示整个书的目录
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
	 * 往list中放书本。
	 * 
	 * @return如果放成功了，返回true，否则返回false；
	 */
	public boolean addListBook(Book book) {
		if (isFull() && isStudent) {
			System.out.println("对不起，您的借阅书本数量达到上限，请归还若干本您借阅的书籍后继续借阅!");
			return false;
		} else {
			Book myBook = null;
			//检查是否已经借过一样的书本
			for(int i= 0;i<listBooks.size(); i++) {
				myBook = listBooks.get(i);
				if(myBook.getId() ==book.getId()){
					System.out.println("你已经借过这本书了，不能借相同的书本！");
					return false;
				}
			}
			listBooks.add(book);
			totalNmb++;

			// 往数据库列表添加书本
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
	 * 把一张表格中的数据加到另外一张表中
	 * 
	 * @param myListBooks
	 *            需要添加的表格
	 * @return 如果成功添加返回true，否则反之false；
	 */
	public boolean addListBook(ListBooks myListBooks) {
		if (!isFull() && isStudent) {
			System.out.println("对不起，您的借阅书本数量达到上限，请归还若干本您借阅的书籍后继续借阅!");
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
	 * 从列表中去掉该书本
	 * 
	 * @return 如果成功去掉返回true，如果没有这本书或其他意外情况返回false；
	 */
	public boolean removeListBook(Book book) {
		boolean isFind = false;//记录是否已经找到
		
		if (isEmpty()) {
			System.out.println("你的书本列表中现在没有书！");
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
			System.out.println("你没有借过这本书，根本不需要还书！");
			return isFind;
		}
		
		// 从列表中删除这本书本
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

				// 如果没有借出过，就有人过来还书，报错
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
