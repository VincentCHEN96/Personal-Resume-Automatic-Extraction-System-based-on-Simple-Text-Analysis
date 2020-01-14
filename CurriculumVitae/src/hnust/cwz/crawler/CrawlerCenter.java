package hnust.cwz.crawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CrawlerCenter {
	private static String url;// Ŀ����ҳ��ַ
	private static String file_path;// ���ݴ��·��
	private static int file_no;// �ļ����
	private static String regular_expression;// ������ʽ��ƥ�������ص�URL��
	private static List<String> wait;// ����ȡ��URL��
	private static Set<String> over;// ����ȡ��URL��
	private static Map<String, Integer> depth;// URL��ȱ�
	private static int max_depth;// �����ȡ���
	private static int max_thread;// ����߳���
	private static int free_thread;// �����߳���
	private static Object obj;// ��������Э�������̵߳ĵȴ�����

	public static void main(String args[]) {
		url = "http://cpc.people.com.cn/GB/64162/394696/index.html";// �й���Ҫ���Ͽ�--��������--������
		file_path = "G:\\���Ʊ�ҵ���\\��������\\��ҳԴ����\\";
		file_no = getFileCount();// �ļ���ŵ����Ϊ��ǰ�ļ��������һ���ļ��ı��;
		regular_expression = "<p><a href=\"http.+</a></p>";// �۲���ҳԴ���֪�����м�����ص�URL����<p><a
															// href="http...</a></p>����ʽ
		wait = new ArrayList<String>();
		over = new HashSet<String>();
		depth = new HashMap<String, Integer>();
		max_depth = 2;// �۲���վ�ṹ��֪�����м�����Ϣ���ڴμ�ҳ���У����������ȡ���Ϊ2
		max_thread = 5;// ���5���߳�ͬʱȥ��
		free_thread = 0;
		obj = new Object();
		addURL(url, 0);// ����ʼҳ�����ȴ����У��ɴ˿�ʼ��ȡ
		// ��5���߳�ͬʱȥ�����ӿ��ٶ�
		for (int i = 0; i < max_thread; i++) {
			new CrawlerCenter().new MyThread().start();
		}
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

	// ����ȡ��URL����ȴ����У�ͬʱ�ж��Ƿ��Ѵ���
	public static synchronized void addURL(String url, int current_depth) {
		// �ж�URL�Ƿ��Ѵ���,�����ڲŽ��ӣ����ظ���ȡͬһ��ҳ�棩
		if (!depth.containsKey(url)) {
			wait.add(url);
			depth.put(url, current_depth + 1);// ��URL�����Ϊ��ǰ�������+1
		}
	}

	// ��ȡ��һ������ȡ��URL��ͬʱ����ӵȴ��������Ƴ�
	public static synchronized String getURL() {
		String next_url = wait.get(0);// ��ȡ��ͷԪ��
		wait.remove(0);// �Ƴ���ͷԪ��
		return next_url;
	}

	public static void work(String url, int current_depth) {
		// �жϴ�URLδ��ȡ��������Ȳ����������ȡ���
		if (!(over.contains(url) || current_depth > max_depth)) {
			try {
				URL u = new URL(url);
				URLConnection uc = u.openConnection();
				InputStream is = uc.getInputStream();
				InputStreamReader isr = new InputStreamReader(is, "GB2312");// �����ʽGB2312
				BufferedReader br = new BufferedReader(isr);
				// ���̻߳�������ļ���ţ��Է��ظ�
				Integer no;
				synchronized (obj) {
					no = ++file_no;
				}
				File f = new File(file_path + "WSC " + no.toString() + ".txt");
				PrintWriter pw = new PrintWriter(f);
				Pattern p = Pattern.compile(regular_expression);
				for (String line = br.readLine(); line != null; line = br.readLine()) {
					pw.println(line);// ����ȡ����ҳԴ��д���ļ�
					// ��������ʽ����ƥ�������ص�URL
					Matcher m = p.matcher(line);
					while (m.find()) {
						String href = m.group();
						href = href.substring(href.indexOf("http"));
						href = href.substring(0, href.indexOf("\""));
						// http:��https:��ͷ��URL����ȴ�����
						if (href.startsWith("http:") || href.startsWith("https:"))
							addURL(href, current_depth);
					}
				}
				pw.close();
				br.close();
				isr.close();
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			over.add(url);// ����ǰ����ȡ����ҳURL��������ȡ����
			System.out.println("��ҳ" + url + "��ȡ��ɣ�����ȡ��ҳ������" + over.size() + "������ȡ��ҳ������" + wait.size());
		}
		// �ȴ������л��д���ȡ��URL������һ�����ڵȴ�״̬���߳�
		if (wait.size() > 0) {
			synchronized (obj) {
				obj.notify();
			}
		} else {
			System.out.println("��վ��ȡ����......");
		}
	}

	// �߳�����
	public class MyThread extends Thread {
		public void run() {
			while (true) {
				if (wait.size() > 0) {
					String url = getURL();
					work(url, depth.get(url));
				} else {
					System.out.println("�߳�" + this.getName() + "���У��ȴ���ȡ...");
					free_thread++;
					// ����Object�������߳̽���ȴ�״̬
					synchronized (obj) {
						try {
							obj.wait();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					free_thread--;
				}
			}
		}
	}
}
