package Person;

import Book.AddBooks;
import Book.ListBooks;
import Book.ReturnBooks;

/**
 * 采购员（负责买书）
 * @author king
 *
 */
public class Buyer {
	String name;
	int workId;
	
	private static ListBooks totalList; // 购书总表
	private ListBooks sectorList;   //接受管理员上传的书单
	private boolean isbuy = false;  //接受是否买书的结果;

	public Buyer(String name, int workId) {
		super();
		this.name = name;
		this.workId = workId;
	}

	
	public void setIsbuy(boolean isbuy) {
		this.isbuy = isbuy;
	}

	public void setSectorLisr(ListBooks sectorList) {
		this.sectorList = sectorList;
	}

	
	/**
	 * 把manager发的买书记录在总的清单中
	 */
	public void savingBuying() {
		totalList.addListBook(sectorList);
		sectorList = null;
	}

	/**
	 * 把需要购买的清单发给图书馆
	 * @param myCurator 需要传递给的图书馆馆长
	 */
	public void SendBuyToCurator(Curator myCurator) {
		myCurator.setTotalList(totalList);
	}

	/**
	 * 得到馆长的允许的许可后，按照清单购买书本
	 * @param manager 传递信息的给图书馆馆长
	 * @return 如果成功返回true，否则返回false；
	 */
	public boolean buyBksNow(Manager manager) {
		if(isbuy) {
			manager.setBuyedList(totalList);
			manager.setBuyed(isbuy);
			totalList = null;
			isbuy = false;
			return true;
		}else{
			System.out.println("没有购买权限！");
			return  false;
		}
	}

}
