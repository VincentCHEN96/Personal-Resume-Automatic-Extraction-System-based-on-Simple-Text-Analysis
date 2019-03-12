package hnust.cwz.dataprocessing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataExtraction {
	private static String file_path_read;// �ļ���ȡ·��
	private static String file_path_write;// �ļ�д��·��
	private static int file_count;// �ļ���Ŀ
	private static int file_no;// �ļ����
	private static List<String> buffer;// ���ݻ���
	private static String regular_expression;// ������ʽ��ƥ�������Ϣ��

	public static void main(String[] args) {
		file_path_read = "G:\\���Ʊ�ҵ���\\��������\\��ҳԴ����\\";
		file_path_write = "G:\\���Ʊ�ҵ���\\��������\\ԭʼ���ݼ�\\";
		file_count = getFileCount();
		file_no = 0;// (����)
		//file_no = 58;// ���ط���
		regular_expression = "	[\u4e00-\u9fa5-0-9].+</p>";// �۲���ҳԴ���֪�����еļ�����Ϣ����
															// ���ֻ�����...</p>����ʽ�����룩
		//regular_expression = "	<p>.+";// �۲���ҳԴ���֪�����еļ�����Ϣ����
															// <p>...����ʽ���ط���
		while (++file_no <= file_count) {
			buffer = new ArrayList<String>();
			Integer no = file_no;
			work(no);
		}
		System.out.println("������ȡ��ɣ�");
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

	public static void work(Integer no) {
		try {
			File f_read = new File(file_path_read + "WSC " + no.toString() + ".txt");
			FileReader fr = new FileReader(f_read);
			BufferedReader br = new BufferedReader(fr);
			// ����Դ�ļ����������ݻ���
			for (String wsc = br.readLine(); wsc != null; wsc = br.readLine()) {
				buffer.add(wsc);
			}
			File f_write = new File(file_path_write + "raw data " + no.toString() + ".txt");
			PrintWriter pw = new PrintWriter(f_write);
			// �������ݻ����еĸ�������
			Pattern p = Pattern.compile(regular_expression);
			for (String wsc : buffer) {
				// ��������ʽ����ƥ�������Ϣ
				Matcher m = p.matcher(wsc);
				while (m.find()) {
					String raw_data = m.group();
					pw.println(raw_data);
				}
			}
			pw.close();
			br.close();
			fr.close();
			System.out.println("���ļ�WSC " + no.toString() + "��ȡԭʼ�����������ļ�raw data " + no.toString() + "�ɹ���");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
