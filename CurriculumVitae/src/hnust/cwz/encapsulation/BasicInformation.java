package hnust.cwz.encapsulation;

public class BasicInformation {
	private int no;// �������
	private String name;// ����
	private String sex;// �Ա�
	private String nationality;// ����
	private String birthday;// ��������
	private String native_place;// ����
	private String date;// �μӹ���ʱ��
	private String party;// ����
	private String school;// ��ҵѧУ

	public BasicInformation() {
		super();
		no = 0;
		name = null;
		sex = null;
		nationality = null;
		birthday = null;
		native_place = null;
		date = null;
		party = null;
		school = null;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getNativePlace() {
		return native_place;
	}

	public void setNativePlace(String native_place) {
		this.native_place = native_place;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getParty() {
		return party;
	}

	public void setParty(String party) {
		this.party = party;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}
}
