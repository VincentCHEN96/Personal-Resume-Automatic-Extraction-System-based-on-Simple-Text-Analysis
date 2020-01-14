package hnust.cwz.database;

public class ConsultDictionary {
	private DBOperation dbo;// ���ݿ���������

	public ConsultDictionary() {
		dbo = new DBOperation("dictionary");// �����ֵ��
	}

	public String consultSex(String data) {
		if (dbo.createConnection()) {
			String sql = "select * from sex";
			dbo.query(sql);
			while (dbo.hasNext()) {
				String column = dbo.getValue("�Ա�");
				if (data.indexOf(column) != -1) {
					String category = "�Ա�";
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
				String column = dbo.getValue("����");
				if (data.indexOf(column) != -1) {
					String category = "����";
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
				String column1 = dbo.getValue("ʡ��");
				String column2 = dbo.getValue("���");
				if ((data.indexOf(column1) != -1) || (data.indexOf(column2) != -1)) {
					String category = "����";
					// ��ֹѧУ��Ϣ�ڴ˴�����ƥ��ɼ�����Ϣ
					if (data.indexOf("��") != -1)
						data = category + "	" + data.substring(0, data.indexOf("��"));
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
				String column1 = dbo.getValue("����");
				String column2 = dbo.getValue("���");
				if ((data.indexOf(column1) != -1) || (data.indexOf(column2) != -1)) {
					String category = "����";
					data = category + "	" + column1;// ��¼������ȫ��
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
				String column = dbo.getValue("ѧУʶ���");
				if (data.indexOf(column) != -1) {
					String category = "��ҵѧУ";
					data = category + "	" + data.substring(0, column.length() + data.indexOf(column));// ��ʶ�������֮ǰ���ַ���ȡ
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
				String column = dbo.getValue("ʡ��");
				int i = 0;
				// ������i����ʼ����
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
				String column = dbo.getValue("����");
				int i = 0;
				// ������i����ʼ����
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
