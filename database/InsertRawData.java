package hnust.cwz.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class InsertRawData {
	private static String file_path;// �ļ�·��
	private static int file_count;// �ļ���Ŀ
	private static int file_no;// �ļ����

	public static void main(String[] args) {
		file_path = "G:\\���Ʊ�ҵ���\\��������\\��Ч���ݼ�������ϴ��\\";
		file_count = getFileCount();
		file_no = 0;// �����룩
		//file_no = 58;// ���ط���
		while (++file_no <= file_count) {
			Integer no = file_no;
			work(no);
		}
		System.out.println("ԭʼ���ݱ�����ɣ�");
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
	public static void insert(String big_str) {
		DBOperation dbo = new DBOperation("curriculum_vitae");
		if (dbo.createConnection()) {
			String sql = "insert into raw_data (�������,ԭʼ����) values ('" + file_no + "0000','" + big_str + "')";
			dbo.operate(sql);
			dbo.close();
		}
	}

	public static void work(Integer no) {
		try {
			File f = new File(file_path + "effective data " + no.toString() + ".txt");
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String big_str = "";
			for (String effective_data = br.readLine(); effective_data != null; effective_data = br.readLine()) {
				big_str += effective_data;
			}
			insert(big_str);
			br.close();
			fr.close();
			System.out.println("�ļ�effective data " + no.toString() + "��ԭʼ���ݱ���ɹ���");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
