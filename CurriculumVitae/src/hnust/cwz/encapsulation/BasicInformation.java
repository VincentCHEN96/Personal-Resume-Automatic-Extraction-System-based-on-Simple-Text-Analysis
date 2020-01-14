package hnust.cwz.encapsulation;

public class BasicInformation {
	private int no;// 简历编号
	private String name;// 姓名
	private String sex;// 性别
	private String nationality;// 民族
	private String birthday;// 出生年月
	private String native_place;// 籍贯
	private String date;// 参加工作时间
	private String party;// 党派
	private String school;// 毕业学校

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
