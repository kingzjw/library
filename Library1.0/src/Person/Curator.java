package Person;

import java.io.IOException;
import Book.ListBooks;
import Book.ReturnBooks;

/**
 * ͼ��ݵĹݳ�
 * @author king
 *
 */
public class Curator {
	private String name;
	private int id;
	static private ListBooks totalList; // �����ܱ�
	private boolean buy = false; // �Ƿ��������

	public Curator(String name, int id) {
		this.name = name;
		this.id = id;
	}

	public static void setTotalList(ListBooks totalList) {
		Curator.totalList = totalList;
	}

	/**
	 * �鿴�����嵥
	 * 
	 * @return ����ɹ��鿴����true������false��
	 */
	public boolean showList() {
		if (totalList == null) {
			System.out.println("û���յ���������룡");
			return false;
		} else {
			totalList.showList();
			System.out.println("�Ƿ�������鵥���飿��y/n��\n");
			return true;
		}
	}

	/**
	 * �ж��Ƿ����飬Ȼ�����Ϣ���ظ��ɹ�Ա
	 * 
	 * @param myBuyer
	 *            ��Ҫ������Ϣ�Ķ���
	 * @return �����������飬��ô����true������֮
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

		buy = false; // ��д��Ϊfalse��
		return buy;

	}

}
