package hnust.cwz.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import hnust.cwz.encapsulation.BasicInformation;

public class InsertBasicInfo {
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
		System.out.println("������Ϣ���ݱ�����ɣ�");
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

	// ���ݷ�װ
	public static BasicInformation dataEncapsulation(BasicInformation bi, String formatted_data) {
		bi.setNo(file_no);
		String str = formatted_data.substring(1 + formatted_data.indexOf("	"));
		if (formatted_data.startsWith("����"))
			bi.setName(str);
		else if (formatted_data.startsWith("�Ա�"))
			bi.setSex(str);
		else if (formatted_data.startsWith("����"))
			bi.setNationality(str);
		else if (formatted_data.startsWith("��������"))
			bi.setBirthday(str);
		else if (formatted_data.startsWith("����"))
			bi.setNativePlace(str);
		else if (formatted_data.startsWith("�μӹ���ʱ��"))
			bi.setDate(str);
		else if (formatted_data.startsWith("����"))
			bi.setParty(str);
		else if (formatted_data.startsWith("��ҵѧУ") && bi.getSchool() == null)
			bi.setSchool(str);
		return bi;
	}

	// ���ݲ���
	public static void insert(BasicInformation bi) {
		DBOperation dbo = new DBOperation("curriculum_vitae");
		if (dbo.createConnection()) {
			String sql = "insert into basic_information (�������,����,�Ա�,����,��������,����,�μӹ���ʱ��,����,��ҵѧУ) values ('" + bi.getNo()
					+ "0000','" + bi.getName() + "','" + bi.getSex() + "','" + bi.getNationality() + "','"
					+ bi.getBirthday() + "','" + bi.getNativePlace() + "','" + bi.getDate() + "','" + bi.getParty()
					+ "','" + bi.getSchool() + "')";
			dbo.operate(sql);
			dbo.close();
		}
	}

	public static void work(Integer no) {
		try {
			File f = new File(file_path + "formatted data " + no.toString() + ".txt");
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			BasicInformation bi = new BasicInformation();
			for (String formatted_data = br.readLine(); formatted_data != null; formatted_data = br.readLine()) {
				if (!(formatted_data.startsWith("19") || formatted_data.startsWith("20")
						|| formatted_data.startsWith("����ְλ")))
					bi = dataEncapsulation(bi, formatted_data);
			}
			insert(bi);
			br.close();
			fr.close();
			System.out.println("�ļ�formatted data " + no.toString() + "�Ļ�����Ϣ���ݱ���ɹ���");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
