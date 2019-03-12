package hnust.cwz.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class InsertPosition {
	private static String file_path;// �ļ�·��
	private static int file_count;// �ļ���Ŀ
	private static int file_no;// �ļ����

	public static void main(String[] args) {
		file_path = "G:\\���Ʊ�ҵ���\\��������\\��ʽ�����ݼ����Ѳ�֣�\\";
		file_count = getFileCount();
		file_no = 0;// �����룩
		//file_no = 58;// ���ط���
		while (++file_no <= file_count) {
			Integer no = file_no;
			work(no);
		}
		System.out.println("����ְλ���ݱ�����ɣ�");
	}

	// ��ȡ�ļ����е��ļ���Ŀ
	public static int getFileCount() {
		File f = new File(file_path);
		File[] list = f.listFiles();
		int file_count = 0;
		for (File file : list) {
			if (file.isFile()) {
				file_count++;
			}
		}
		return file_count;
	}

	// ���ݲ���
	public static void insert(String formatted_data) {
		DBOperation dbo = new DBOperation("curriculum_vitae");
		if (dbo.createConnection()) {
			String sql, str1 = formatted_data.substring(1 + formatted_data.indexOf("	"));
			while (str1.indexOf("��") != -1) {
				String str2 = str1.substring(0, str1.indexOf("��"));
				sql = "insert into position (�������,����ְλ) values ('" + file_no + "0000','" + str2 + "')";
				dbo.operate(sql);
				str1 = str1.substring(1 + str1.indexOf("��"));
			}
			sql = "insert into position (�������,����ְλ) values ('" + file_no + "0000','" + str1 + "')";
			dbo.operate(sql);
			dbo.close();
		}
	}

	public static void work(Integer no) {
		try {
			File f = new File(file_path + "formatted data " + no.toString() + ".txt");
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			for (String formatted_data = br.readLine(); formatted_data != null; formatted_data = br.readLine()) {
				if (formatted_data.startsWith("����ְλ"))
					insert(formatted_data);
			}
			br.close();
			fr.close();
			System.out.println("�ļ�formatted data " + no.toString() + "������ְλ���ݱ���ɹ���");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
