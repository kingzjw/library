package Person;

import java.io.IOException;
import Book.ListBooks;
import Book.ReturnBooks;

/**
 * 图书馆的馆长
 * @author king
 *
 */
public class Curator {
	private String name;
	private int id;
	static private ListBooks totalList; // 购书总表
	private boolean buy = false; // 是否决定购书

	public Curator(String name, int id) {
		this.name = name;
		this.id = id;
	}

	public static void setTotalList(ListBooks totalList) {
		Curator.totalList = totalList;
	}

	/**
	 * 查看购书清单
	 * 
	 * @return 如果成功查看返回true，否则false；
	 */
	public boolean showList() {
		if (totalList == null) {
			System.out.println("没有收到买书的申请！");
			return false;
		} else {
			totalList.showList();
			System.out.println("是否购买这个书单的书？（y/n）\n");
			return true;
		}
	}

	/**
	 * 判断是否买书，然后把信息返回给采购员
	 * 
	 * @param myBuyer
	 *            需要传给信息的对象
	 * @return 如果最后是买书，那么返回true，否则反之
	 */
	boolean judjeBuying(Buyer myBuyer) {
		char ch = '0';

		try {
			ch = (char) System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}

		switch (ch) {
		case 'y':
			buy = true;
			break;
		case 'n':
			buy = false;
			break;
		}

		myBuyer.setIsbuy(buy);

		buy = false; // 重写置为false；
		return buy;

	}

}
