package hnust.cwz.database;

public class ConsultDictionary {
	private DBOperation dbo;// 数据库操作类对象

	public ConsultDictionary() {
		dbo = new DBOperation("dictionary");// 操作字典库
	}

	public String consultSex(String data) {
		if (dbo.createConnection()) {
			String sql = "select * from sex";
			dbo.query(sql);
			while (dbo.hasNext()) {
				String column = dbo.getValue("性别");
				if (data.indexOf(column) != -1) {
					String category = "性别";
					data = category + "	" + column;
					break;
				}
			}
			dbo.close();
		}
		return data;
	}

	public String consultNationality(String data) {
		if (dbo.createConnection()) {
			String sql = "select * from nationality";
			dbo.query(sql);
			while (dbo.hasNext()) {
				String column = dbo.getValue("民族");
				if (data.indexOf(column) != -1) {
					String category = "民族";
					data = category + "	" + column;
					break;
				}
			}
			dbo.close();
		}
		return data;
	}

	public String consultNativePlace(String data) {
		if (dbo.createConnection()) {
			String sql = "select * from province";
			dbo.query(sql);
			while (dbo.hasNext()) {
				String column1 = dbo.getValue("省份");
				String column2 = dbo.getValue("简称");
				if ((data.indexOf(column1) != -1) || (data.indexOf(column2) != -1)) {
					String category = "籍贯";
					// 防止学校信息在此处错误匹配成籍贯信息
					if (data.indexOf("人") != -1)
						data = category + "	" + data.substring(0, data.indexOf("人"));
					break;
				}
			}
			dbo.close();
		}
		return data;
	}

	public String consultParty(String data) {
		if (dbo.createConnection()) {
			String sql = "select * from party";
			dbo.query(sql);
			while (dbo.hasNext()) {
				String column1 = dbo.getValue("党派");
				String column2 = dbo.getValue("简称");
				if ((data.indexOf(column1) != -1) || (data.indexOf(column2) != -1)) {
					String category = "党派";
					data = category + "	" + column1;// 记录党派名全称
					break;
				}
			}
			dbo.close();
		}
		return data;
	}

	public String consultSchool(String data) {
		if (dbo.createConnection()) {
			String sql = "select * from school_identifier";
			dbo.query(sql);
			while (dbo.hasNext()) {
				String column = dbo.getValue("学校识别符");
				if (data.indexOf(column) != -1) {
					String category = "毕业学校";
					data = category + "	" + data.substring(0, column.length() + data.indexOf(column));// 将识别符及其之前的字符截取
					break;
				}
			}
			dbo.close();
		}
		return data;
	}

	public String consultProvince(String data) {
		if (dbo.createConnection()) {
			String sql = "select * from province";
			dbo.query(sql);
			while (dbo.hasNext()) {
				String column = dbo.getValue("省份");
				int i = 0;
				// 从索引i处开始搜索
				while ((data.indexOf(column, i) != -1)) {
					String str1 = data.substring(0, column.length() + data.indexOf(column, i));
					String str2 = data.substring(column.length() + data.indexOf(column, i));
					i = column.length() + data.indexOf(column, i);
					data = str1 + "	" + str2;
				}
			}
			dbo.close();
		}
		return data;
	}

	public String consultCity(String data) {
		if (dbo.createConnection()) {
			String sql = "select * from city";
			dbo.query(sql);
			while (dbo.hasNext()) {
				String column = dbo.getValue("城市");
				int i = 0;
				// 从索引i处开始搜索
				while ((data.indexOf(column, i) != -1)) {
					String str1 = data.substring(0, column.length() + data.indexOf(column, i));
					String str2 = data.substring(column.length() + data.indexOf(column, i));
					i = column.length() + data.indexOf(column, i);
					data = str1 + "	" + str2;
				}
			}
			dbo.close();
		}
		return data;
	}
}
