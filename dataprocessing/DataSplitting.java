package hnust.cwz.dataprocessing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import hnust.cwz.database.ConsultDictionary;

public class DataSplitting {
	private static String file_path_read;// �ļ���ȡ·��
	private static String file_path_write;// �ļ�д��·��
	private static int file_count;// �ļ���Ŀ
	private static int file_no;// �ļ����
	private static List<String> buffer;// ���ݻ���

	public static void main(String[] args) {
		file_path_read = "G:\\���Ʊ�ҵ���\\��������\\��Ч���ݼ�������ϴ��\\";
		file_path_write = "G:\\���Ʊ�ҵ���\\��������\\��ʽ�����ݼ����Ѳ�֣�\\";
		file_count = getFileCount();
		file_no = 0;// �����룩
		//file_no = 58;// ���ط���
		while (++file_no <= file_count) {
			buffer = new ArrayList<String>();
			Integer no = file_no;
			work(no);
		}
		System.out.println("���ݲ����ɣ�");
	}

	// ��ȡ�ļ����е��ļ���Ŀ
	public static int getFileCount() {
		File f = new File(file_path_read);
		File[] list = f.listFiles();
		int file_count = 0;
		for (File file : list) {
			if (file.isFile()) {
				file_count++;
			}
		}
		return file_count;
	}

	// ���ֵ�
	public static void consultDictionary(String str, PrintWriter pw) {
		ConsultDictionary cd = new ConsultDictionary();// ���ֵ������
		if (str != cd.consultSex(str))// �ж�Ϊ�Ա���Ϣ
			pw.println(cd.consultSex(str));
		else if (str != cd.consultNationality(str))// �ж�Ϊ������Ϣ
			pw.println(cd.consultNationality(str));
		else if (str != cd.consultNativePlace(str))// �ж�Ϊ������Ϣ
			pw.println(cd.consultNativePlace(str));
		else if (str != cd.consultParty(str))// �ж�Ϊ������Ϣ
			pw.println(cd.consultParty(str));
		else if (str != cd.consultSchool(str))// �ж�Ϊ��ҵѧУ��Ϣ
			pw.println(cd.consultSchool(str));
	}

	// ������Ϣ���
	public static void splitting(String effective_data, PrintWriter pw) {
		if (effective_data.startsWith("����")) {// �ж�Ϊ����ְλ��Ϣ
			String str1 = effective_data.substring(0, 2) + "ְλ";
			String str2 = effective_data.substring(2);
			effective_data = str1 + "	" + str2;
			pw.println(effective_data);
		} else if (effective_data.startsWith("19") || effective_data.startsWith("20")) {// �ж�Ϊ������Ϣ
			// �����ֹʱ�䡢�ص㡢ְ����Ϣ
			String str1 = null, str2 = null;
			if (effective_data.indexOf("��") != -1) {
				str1 = effective_data.substring(0, 1 + effective_data.indexOf("��"));
				str2 = effective_data.substring(1 + effective_data.indexOf("��"));
			} else if (effective_data.indexOf("�D") != -1) {
				str1 = effective_data.substring(0, 1 + effective_data.indexOf("�D"));
				str2 = effective_data.substring(1 + effective_data.indexOf("�D"));
			} else {
				str1 = effective_data.substring(0, 1 + effective_data.indexOf("��"));
				str2 = effective_data.substring(1 + effective_data.indexOf("��"));
			}
			/*if (effective_data.indexOf(" ") != -1) {
				str1 = effective_data.substring(0, effective_data.indexOf(" "));
				str2 = effective_data.substring(1 + effective_data.indexOf(" "));
			} else if (effective_data.indexOf("��") != -1) {
				str1 = effective_data.substring(0, effective_data.indexOf("��"));
				str2 = effective_data.substring(1 + effective_data.indexOf("��"));
			}*/
			String[] string = str2.split("��");// ��"��"Ϊ�ָ����ٲ��
			String str3 = "";
			for (int i = 0; i < string.length; i++) {
				str3 += (string[i] + "	");
			}
			effective_data = str1 + "	" + str3;
			pw.println(effective_data);
		} else {// �ж�Ϊ������Ϣ
			boolean flag = true;// �ж���������һ�еı�־λ
			// �Զ������ָ���ָ�����
			while (effective_data.indexOf("��") != -1) {
				String str = effective_data.substring(0, effective_data.indexOf("��"));
				if (str.indexOf("����") != -1) {// ��ֳ��������ֶ�
					str = "��������	" + str.substring(0, str.indexOf("��"));
					pw.println(str);
				} else if (str.indexOf("����") != -1) {// ��ֳ��������ֶ�
					str = "��������	" + str.substring(0, str.indexOf("��"));
					pw.println(str);
				} else if (str.indexOf("�μӹ���") != -1) {// ��ֲμӹ���ʱ���ֶ�
					str = "�μӹ���ʱ��	" + str.substring(0, str.indexOf("�μӹ���"));
					pw.println(str);
				} else if (flag && !(str.startsWith("��") || str.startsWith("Ů"))) {// ��������ֶ�
					pw.println("����	" + str);
					flag = false;
				} else {
					consultDictionary(str, pw);
					flag = false;
				}
				effective_data = effective_data.substring(1 + effective_data.indexOf("��"));
			}
			if (flag) {// �ж�Ϊ��������һ�е����
				pw.println("����	" + effective_data);
				flag = false;
			} else
				consultDictionary(effective_data, pw);
		}
	}

	public static void work(Integer no) {
		try {
			File f_read = new File(file_path_read + "effective data " + no.toString() + ".txt");
			FileReader fr = new FileReader(f_read);
			BufferedReader br = new BufferedReader(fr);
			// ����Դ�ļ����������ݻ���
			for (String effective_data = br.readLine(); effective_data != null; effective_data = br.readLine()) {
				buffer.add(effective_data);
			}
			File f_write = new File(file_path_write + "formatted data " + no.toString() + ".txt");// ����������ݵ��ı��ļ���������������Է��ظ�
			PrintWriter pw = new PrintWriter(f_write);
			// �������ݻ����еĸ�������
			for (String effective_data : buffer) {
				splitting(effective_data, pw);// ���Դ����
			}
			pw.close();
			br.close();
			fr.close();
			System.out
					.println("�ļ�effective data " + no.toString() + "��ּ����������ļ�formatted data " + no.toString() + "�ɹ���");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
