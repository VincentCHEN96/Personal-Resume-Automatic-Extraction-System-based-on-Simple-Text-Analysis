package hnust.cwz.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import hnust.cwz.encapsulation.Experience;

public class InsertExperience {
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
		System.out.println("�������ݱ�����ɣ�");
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
	public static Experience[] dataEncapsulation(String formatted_data) {
		// ��¼����formatted_data��Ӧ���о�����Ϣ�� ��ֹʱ��
		Experience e = new Experience();
		e.setNo(file_no);
		if (formatted_data.indexOf("�D") != -1) {
			e.setStart_date(formatted_data.substring(0, formatted_data.indexOf("�D")) + "��");
			if (formatted_data.indexOf("��") != -1)
				e.setEnd_date(
						formatted_data.substring(1 + formatted_data.indexOf("�D"), formatted_data.indexOf("��")) + "��");
		} else if (formatted_data.indexOf("��") != -1) {
			e.setStart_date(formatted_data.substring(0, formatted_data.indexOf("��")) + "��");
			if (formatted_data.indexOf("��") != -1)
				e.setEnd_date(
						formatted_data.substring(1 + formatted_data.indexOf("��"), formatted_data.indexOf("��")) + "��");
		}
		/*if ((formatted_data.indexOf("-") != -1) && (formatted_data.indexOf("-") < formatted_data.indexOf("	"))) {
			e.setStart_date(formatted_data.substring(0, formatted_data.indexOf("-")));
			e.setEnd_date(
					formatted_data.substring(1 + formatted_data.indexOf("-"), formatted_data.indexOf("	")));
		} else if ((formatted_data.indexOf("��") != -1) && (formatted_data.indexOf("��") < formatted_data.indexOf("	"))) {
			e.setStart_date(formatted_data.substring(0, formatted_data.indexOf("��")));
			e.setEnd_date(
					formatted_data.substring(1 + formatted_data.indexOf("��"), formatted_data.indexOf("	")));
		} else if ((formatted_data.indexOf("�D") != -1) && (formatted_data.indexOf("�D") < formatted_data.indexOf("	"))) {
			e.setStart_date(formatted_data.substring(0, formatted_data.indexOf("�D")));
			e.setEnd_date(
					formatted_data.substring(1 + formatted_data.indexOf("�D"), formatted_data.indexOf("	")));
		} else
			e.setStart_date(formatted_data.substring(0, formatted_data.indexOf("	")));*/
		String str = formatted_data.substring(1 + formatted_data.indexOf("	"));// ��ֳ�����ľ�����Ϣ
		String[] string = str.split("	");// ��" "Ϊ�ָ�����ֳ�ÿ��������Ϣ
		Experience[] experience = new Experience[string.length];// ����string.length��������Ϣ
		// ���Ȱ��м���־�����Ϣ�еĵص㣬��ΰ�ʡ�����
		ConsultDictionary cd = new ConsultDictionary();
		for (int i = 0; i < string.length; i++) {
			experience[i] = new Experience();
			experience[i].setNo(e.getNo());
			experience[i].setStart_date(e.getStart_date());
			experience[i].setEnd_date(e.getEnd_date());
			String str1 = string[i];
			String str2 = cd.consultCity(str1);
			if (str2 != str1) {
				experience[i].setPlace(str2.substring(0, str2.indexOf("	")));
				experience[i].setPosition(str2.substring(1 + str2.indexOf("	")));
			} else {
				str2 = cd.consultProvince(str1);
				if (str2 != str1) {
					experience[i].setPlace(str2.substring(0, str2.indexOf("	")));
					experience[i].setPosition(str2.substring(1 + str2.indexOf("	")));
				} else
					experience[i].setPosition(str2);
			}
			// ���"ʡί"��"��ί"��ִ�������
			String str3 = experience[i].getPlace();
			String str4 = experience[i].getPosition();
			if (str3 != null && str4 != null)
				if (str3.endsWith("ʡ") && str4.startsWith("ί"))
					experience[i].setPosition("ʡ" + str4);
				else if (str3.endsWith("��") && str4.startsWith("ί"))
					experience[i].setPosition("��" + str4);
		}
		return experience;
	}

	// ���ݲ���
	public static void insert(Experience experience) {
		DBOperation dbo = new DBOperation("curriculum_vitae");
		if (dbo.createConnection()) {
			String sql = "insert into experience (�������,��ʼʱ��,��ֹʱ��,�ص�,ְλ) values ('" + experience.getNo() + "0000','"
					+ experience.getStart_date() + "','" + experience.getEnd_date() + "','" + experience.getPlace()
					+ "','" + experience.getPosition() + "')";
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
				if (formatted_data.startsWith("19") || formatted_data.startsWith("20")) {
					Experience[] experience = dataEncapsulation(formatted_data);
					for (int i = 0; i < experience.length; i++)
						insert(experience[i]);
				}
			}
			br.close();
			fr.close();
			System.out.println("�ļ�formatted data " + no.toString() + "�ľ������ݱ���ɹ���");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
