package Person;

import java.io.IOException;
import java.util.ArrayList;

import Book.AddBooks;
import Book.Book;
import Book.CheckBooks;
import Book.ListBooks;

/**
 * 图书馆管理员
 * @author king 
 */
public class Manager {
	private String managerName;
	private int workId;
	private boolean isBuyed = false; // 接受信息有新书到了
	private ListBooks myListBooks = new ListBooks(false); // 记录需要购买的书本
	private ListBooks buyedList; // 保存已经购买的书本

	public void setBuyed(boolean isBuyed) {
		this.isBuyed = isBuyed;
	}


	public void setBuyedList(ListBooks buyedList) {
		this.buyedList = buyedList;
	}

	public Manager(String managerName, int workId) {
		this.managerName = managerName;
		this.workId = workId;
	}

	/**
	 * 向采购员发出购书通知
	 * 
	 * @param buyer
	 *            采购员
	 * @return 如果发送成功，那么返回true，否则反之
	 */
	public boolean SendListToBuyer(Buyer buyer) {
		buyer.setSectorLisr(myListBooks);
		myListBooks = null; // 成功发把出后，清单清空
		return true;
	}

	/**
	 * 往书库中加书
	 * @return 成功添加返回true，否则反之false；
	 */
	public boolean AddBuyedListBooks() {
		AddBooks myAddBooks = new AddBooks();

		if (buyedList.getBkNmb() != 0) {
			Book book = null;
			for (int i = 0; i < buyedList.listBooks.size(); i++) {
				book = buyedList.listBooks.get(i);
				myAddBooks.addBooks(book);
			}
			buyedList = null;
			isBuyed = false;

			return true;
		} else {
			System.out.println("没有购买好的书本！");
			return false;
		}
	}

	/**
	 * 按照名字查阅书本
	 * 
	 * @param book
	 *            需要查阅的书本
	 * @return 成功借书，返回true，否则返回false；
	 */

	public boolean checkBooks(Book book) {
		CheckBooks checkBooks = new CheckBooks();
		ArrayList<Book> showList = new ArrayList<Book>();
		Book mbook = null;
		boolean flag = checkBooks.checkBksByName(book, showList);
		
		for(int i = 0; i < showList.size(); i++) {
			mbook = showList.get(i);
			mbook.toString();
		}
		return flag;
	}
	
	/**
	 * 检查书库，是否丢失书本
	 * 
	 * @return 成功返回true，否则返回false
	 */
	public boolean loseBook() {
		// ?????????
		return true;
	}
}
