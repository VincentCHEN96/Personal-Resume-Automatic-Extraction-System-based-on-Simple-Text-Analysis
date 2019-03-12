package hnust.cwz.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBOperation {
	private final String driver;// ���ݿ�����������
	private String url;// URLָ������ʵ����ݿ���
	private final String username;// MySQL�����û���
	private final String password;// MySQL��������
	private Connection connection;// ���ݿ�����
	private Statement statement;
	private ResultSet rs;// ���ݿ��ѯ�����

	public DBOperation(String db_name) {
		super();
		driver = "com.mysql.jdbc.Driver";
		url = "jdbc:mysql://localhost:3306/" + db_name;
		username = "root";
		password = "Chenweizheng0226";
		connection = null;
		statement = null;
		rs = null;
	}

	// �������ݿ�����
	public boolean createConnection() {
		boolean flag = false;
		try {
			Class.forName(driver);// ����JDBC��������
			connection = DriverManager.getConnection(url, username, password);// �����ݿ�����
			flag = true;
		} catch (ClassNotFoundException e) {
			System.out.println("δ�ҵ��������������ݿ�ʧ�ܣ�");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	// �ر�
	public boolean close() {
		boolean flag = false;
		try {
			if (rs != null)
				rs.close();
			if (statement != null)
				statement.close();
			if (connection != null)
				connection.close();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	// ��ѯ
	public boolean query(String sql) {
		boolean flag = false;
		try {
			statement = connection.createStatement();// ��statement��ִ��SQL���
			rs = statement.executeQuery(sql);// ��ѯ�����
			flag = true;
		} catch (SQLException e) {
			System.out.println("SQL�������ִ��ʧ�ܣ�");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	// ����ɾ����
	public boolean operate(String sql) {
		boolean flag = false;
		try {
			statement = connection.createStatement();
			statement.execute(sql);// ��statement��ִ��SQL���
			flag = true;
		} catch (SQLException e) {
			System.out.println("SQL�������ִ��ʧ�ܣ�");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	// ��ȡ�����ĳ�ֶε�����
	public String getValue(String column) {
		String value = null;
		try {
			if (rs != null)
				value = rs.getString(column);// ��ȡ�����column�ֶε�����
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	// �жϽ�����Ƿ������һ����¼
	public boolean hasNext() {
		boolean flag = false;
		try {
			if (rs.next())
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
}
