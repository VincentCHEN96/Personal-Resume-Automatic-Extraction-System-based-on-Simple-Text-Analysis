package hnust.cwz.encapsulation;

public class Experience {
	private int no;// �������
	private String start_date;// ��ʼʱ��
	private String end_date;// ��ֹʱ��
	private String place;// �ص�
	private String position;// ְλ

	public Experience() {
		super();
		no = 0;
		start_date = null;
		end_date = null;
		place = null;
		position = null;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
}
