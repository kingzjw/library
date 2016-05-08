package Person;

import java.io.IOException;
import java.util.ArrayList;

import Book.AddBooks;
import Book.Book;
import Book.CheckBooks;
import Book.ListBooks;

/**
 * ͼ��ݹ���Ա
 * @author king 
 */
public class Manager {
	private String managerName;
	private int workId;
	private boolean isBuyed = false; // ������Ϣ�����鵽��
	private ListBooks myListBooks = new ListBooks(false); // ��¼��Ҫ������鱾
	private ListBooks buyedList; // �����Ѿ�������鱾

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
	 * ��ɹ�Ա��������֪ͨ
	 * 
	 * @param buyer
	 *            �ɹ�Ա
	 * @return ������ͳɹ�����ô����true������֮
	 */
	public boolean SendListToBuyer(Buyer buyer) {
		buyer.setSectorLisr(myListBooks);
		myListBooks = null; // �ɹ����ѳ����嵥���
		return true;
	}

	/**
	 * ������м���
	 * @return �ɹ���ӷ���true������֮false��
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
			System.out.println("û�й���õ��鱾��");
			return false;
		}
	}

	/**
	 * �������ֲ����鱾
	 * 
	 * @param book
	 *            ��Ҫ���ĵ��鱾
	 * @return �ɹ����飬����true�����򷵻�false��
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
	 * �����⣬�Ƿ�ʧ�鱾
	 * 
	 * @return �ɹ�����true�����򷵻�false
	 */
	public boolean loseBook() {
		// ?????????
		return true;
	}
}
