package hnust.cwz.javabeans;

public class BusinessBean {
	// ��½��֤
	public boolean vaild(String username, String password) {
		boolean isVaild = false;
		DBAccessBean db = new DBAccessBean("automatic_extraction_system");
		if (db.createConnection()) {
			String sql = "select * from user where username='" + username
					+ "'and password='" + password + "'";
			db.query(sql);
			if (db.hasNext()) {
				isVaild = true;
			}
			db.close();
		}
		return isVaild;
	}

	// �û����Ѵ�����֤
	public boolean isExist(String username) {
		boolean isExist = false;
		DBAccessBean db = new DBAccessBean("automatic_extraction_system");
		if (db.createConnection()) {
			String sql = "select * from user where username='" + username + "'";
			db.query(sql);
			if (db.hasNext()) {
				isExist = true;
			}
			db.close();
		}
		return isExist;
	}

	// �û�ע��
	public void user_insert(String username, String password) {
		DBAccessBean db = new DBAccessBean("automatic_extraction_system");
		if (db.createConnection()) {
			String sql = "insert into user(username,password)values('"
					+ username + "','" + password + "')";
			db.operate(sql);
			db.close();
		}
	}

	// �û�����
	public void user_update(String password, String username) {
		DBAccessBean db = new DBAccessBean("automatic_extraction_system");
		if (db.createConnection()) {
			String sql = "update user set password='" + password
					+ "' where username='" + username + "'";
			db.operate(sql);
			db.close();
		}
	}
}
