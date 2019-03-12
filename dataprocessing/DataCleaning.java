package hnust.cwz.dataprocessing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataCleaning {
	private static String file_path_read;// �ļ���ȡ·��
	private static String file_path_write;// �ļ�д��·��
	private static int file_count;// �ļ���Ŀ
	private static int file_no;// �ļ����
	private static List<String> buffer;// ���ݻ���

	public static void main(String[] args) {
		file_path_read = "G:\\���Ʊ�ҵ���\\��������\\ԭʼ���ݼ�\\";
		file_path_write = "G:\\���Ʊ�ҵ���\\��������\\��Ч���ݼ�������ϴ��\\";
		file_count = getFileCount();
		file_no = 0;// �����룩
		//file_no = 58;// ���ط���
		while (++file_no <= file_count) {
			buffer = new ArrayList<String>();
			Integer no = file_no;
			work(no);
		}
		System.out.println("������ϴ��ɣ�");
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

	// �ж��ַ����Ƿ���������ַ�
	public static boolean isChinese(String str) {
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher matcher = p.matcher(str);
		boolean flag = false;
		if (matcher.find())
			flag = true;
		return flag;
	}

	// ������ϴ
	public static String cleaning(String raw_data) {
		raw_data = raw_data.substring(1 + raw_data.indexOf("	"));// ȥ��ÿ���ײ���"
																	// "�����룩
		//raw_data = raw_data.substring(4 + raw_data.indexOf("	<p>"));// ȥ��ÿ���ײ���"
																	// <p>"���ط���
		if (raw_data.startsWith("��") || raw_data.startsWith("Ů") || raw_data.startsWith("����")// �ж�Ϊ������Ϣ������ְλ��Ŀ
				|| raw_data.charAt(1 + raw_data.indexOf("��")) == '��'
				|| raw_data.charAt(1 + raw_data.indexOf("��")) == 'Ů') {
			raw_data = raw_data.substring(0, raw_data.indexOf("</p>"));// ȥ��β����"</p>"�����룩
			raw_data = raw_data.substring(0, raw_data.indexOf("��"));// �ص�"��"֮���������Ϣ
			return raw_data;
		} else if (raw_data.startsWith("19") || raw_data.startsWith("20")) {// �ж�Ϊ������Ϣ��Ŀ
			// ȥ����ֹʱ����ص�ְ��֮���������Ϣ�����룩
			String str1, str2;
			if (raw_data.indexOf("��") != -1) {
				str1 = raw_data.substring(0, 1 + raw_data.indexOf("��"));
				str2 = raw_data.substring(1 + raw_data.indexOf("��"));
			} else if (raw_data.indexOf("�D") != -1) {
				str1 = raw_data.substring(0, 1 + raw_data.indexOf("�D"));
				str2 = raw_data.substring(1 + raw_data.indexOf("�D"));
			} else {
				str1 = raw_data.substring(0, 1 + raw_data.indexOf("��"));
				str2 = raw_data.substring(1 + raw_data.indexOf("��"));
			}
			// �ҵ��ص�ְ����Ϣ����㣨��һ�������ַ�������ȡ֮�������
			int i = 0;
			while (!isChinese(String.valueOf(str2.charAt(i))))
				++i;
			str2 = str2.substring(i);
			raw_data = str1 + str2;// ƴ�ӳ�һ�������ľ�����Ϣ��Ŀ
			raw_data = raw_data.substring(0, raw_data.indexOf("</p>"));// ȥ��β����"</p>"
			return raw_data;
		} else {// �ж�Ϊ��������һ�е���������룩
			// �ж�"</p>"�����ڵھŸ��ַ���֮ǰ������ǰһ���ַ�������
			if ((raw_data.indexOf("</p>") <= 8)
					&& isChinese(String.valueOf(raw_data.charAt(raw_data.indexOf("</p>") - 1)))) {
				raw_data = raw_data.substring(0, raw_data.indexOf("</p>"));// ȥ��β����"</p>"
				return raw_data;
			}
			return null;
		}
	}

	public static void work(Integer no) {
		try {
			File f_read = new File(file_path_read + "raw data " + no.toString() + ".txt");
			FileReader fr = new FileReader(f_read);
			BufferedReader br = new BufferedReader(fr);
			// ����Դ�ļ����������ݻ���
			for (String raw_data = br.readLine(); raw_data != null; raw_data = br.readLine()) {
				buffer.add(raw_data);
			}
			File f_write = new File(file_path_write + "effective data " + no.toString() + ".txt");
			PrintWriter pw = new PrintWriter(f_write);
			// �������ݻ����еĸ�������
			for (String raw_data : buffer) {
				String effective_data = cleaning(raw_data);// ��ϴԴ����
				if (effective_data != null)
					pw.println(effective_data);
			}
			pw.close();
			br.close();
			fr.close();
			System.out.println("�ļ�raw data " + no.toString() + "��ϴΪ�ļ�effective data " + no.toString() + "�ɹ���");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
