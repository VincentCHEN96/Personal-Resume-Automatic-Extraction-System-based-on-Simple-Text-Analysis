package hnust.cwz.javabeans;

import java.sql.*;

public class DBAccessBean {
	private final String driver;// 数据库驱动程序名
	private String url;// URL指向待访问的数据库名
	private final String username;// MySQL连接用户名
	private final String password;// MySQL连接密码
	private Connection connection;// 数据库连接
	private Statement statement;
	private ResultSet rs;// 数据库查询结果集

	public DBAccessBean(String db_name) {
		super();
		driver = "com.mysql.jdbc.Driver";
		url = "jdbc:mysql://localhost:3306/" + db_name;
		username = "root";
		password = "Chenweizheng0226";
		connection = null;
		statement = null;
		rs = null;
	}

	// 建立数据库连接
	public boolean createConnection() {
		boolean flag = false;
		try {
			Class.forName(driver);// 加载JDBC驱动程序
			connection = DriverManager.getConnection(url, username, password);// 打开数据库连接
			flag = true;
		} catch (ClassNotFoundException e) {
		} catch (Exception e) {
		}
		return flag;
	}

	// 关闭
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
		}
		return flag;
	}

	// 查询
	public boolean query(String sql) {
		boolean flag = false;
		try {
			statement = connection.createStatement();// 用statement来执行SQL语句
			rs = statement.executeQuery(sql);// 查询结果集
			flag = true;
		} catch (SQLException e) {
		} catch (Exception e) {
		}
		return flag;
	}

	// 增、删、改
	public boolean operate(String sql) {
		boolean flag = false;
		try {
			statement = connection.createStatement();
			statement.execute(sql);// 用statement来执行SQL语句
			flag = true;
		} catch (SQLException e) {
		} catch (Exception e) {
		}
		return flag;
	}

	// 获取结果集某字段的数据
	public String getValue(String column) {
		String value = null;
		try {
			if (rs != null)
				value = rs.getString(column);// 获取结果集column字段的数据
		} catch (Exception e) {
		}
		return value;
	}

	// 判断结果集是否存在下一条记录
	public boolean hasNext() {
		boolean flag = false;
		try {
			if (rs.next())
				flag = true;
		} catch (Exception e) {
		}
		return flag;
	}
}
