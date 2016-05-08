package Person;

import Book.AddBooks;
import Book.ListBooks;
import Book.ReturnBooks;

/**
 * �ɹ�Ա���������飩
 * @author king
 *
 */
public class Buyer {
	String name;
	int workId;
	
	private static ListBooks totalList; // �����ܱ�
	private ListBooks sectorList;   //���ܹ���Ա�ϴ����鵥
	private boolean isbuy = false;  //�����Ƿ�����Ľ��;

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
	 * ��manager���������¼���ܵ��嵥��
	 */
	public void savingBuying() {
		totalList.addListBook(sectorList);
		sectorList = null;
	}

	/**
	 * ����Ҫ������嵥����ͼ���
	 * @param myCurator ��Ҫ���ݸ���ͼ��ݹݳ�
	 */
	public void SendBuyToCurator(Curator myCurator) {
		myCurator.setTotalList(totalList);
	}

	/**
	 * �õ��ݳ����������ɺ󣬰����嵥�����鱾
	 * @param manager ������Ϣ�ĸ�ͼ��ݹݳ�
	 * @return ����ɹ�����true�����򷵻�false��
	 */
	public boolean buyBksNow(Manager manager) {
		if(isbuy) {
			manager.setBuyedList(totalList);
			manager.setBuyed(isbuy);
			totalList = null;
			isbuy = false;
			return true;
		}else{
			System.out.println("û�й���Ȩ�ޣ�");
			return  false;
		}
	}

}
