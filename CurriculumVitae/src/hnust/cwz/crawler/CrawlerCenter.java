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
	private static String url;// 目标网页地址
	private static String file_path;// 数据存放路径
	private static int file_no;// 文件编号
	private static String regular_expression;// 正则表达式（匹配简历相关的URL）
	private static List<String> wait;// 待爬取的URL集
	private static Set<String> over;// 已爬取的URL集
	private static Map<String, Integer> depth;// URL深度表
	private static int max_depth;// 最大爬取深度
	private static int max_thread;// 最大线程数
	private static int free_thread;// 空闲线程数
	private static Object obj;// 生命对象，协助进行线程的等待操作

	public static void main(String args[]) {
		url = "http://cpc.people.com.cn/GB/64162/394696/index.html";// 中国政要资料库--资料中心--人民网
		file_path = "G:\\本科毕业设计\\简历数据\\网页源代码\\";
		file_no = getFileCount();// 文件编号的起点为当前文件夹中最后一个文件的编号;
		regular_expression = "<p><a href=\"http.+</a></p>";// 观察网页源码可知，其中简历相关的URL都是<p><a
															// href="http...</a></p>的形式
		wait = new ArrayList<String>();
		over = new HashSet<String>();
		depth = new HashMap<String, Integer>();
		max_depth = 2;// 观察网站结构可知，所有简历信息都在次级页面中，所以最大爬取深度为2
		max_thread = 5;// 最大开5个线程同时去爬
		free_thread = 0;
		obj = new Object();
		addURL(url, 0);// 将初始页面放入等待队列，由此开始爬取
		// 开5个线程同时去爬，加快速度
		for (int i = 0; i < max_thread; i++) {
			new CrawlerCenter().new MyThread().start();
		}
	}

	// 获取文件夹中的文件数目
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

	// 将获取的URL放入等待队列，同时判断是否已存在
	public static synchronized void addURL(String url, int current_depth) {
		// 判断URL是否已存在,不存在才进队（不重复爬取同一个页面）
		if (!depth.containsKey(url)) {
			wait.add(url);
			depth.put(url, current_depth + 1);// 此URL的深度为当前所在深度+1
		}
	}

	// 获取下一个待爬取的URL，同时将其从等待队列中移除
	public static synchronized String getURL() {
		String next_url = wait.get(0);// 获取队头元素
		wait.remove(0);// 移除队头元素
		return next_url;
	}

	public static void work(String url, int current_depth) {
		// 判断此URL未爬取过且其深度不大于最大爬取深度
		if (!(over.contains(url) || current_depth > max_depth)) {
			try {
				URL u = new URL(url);
				URLConnection uc = u.openConnection();
				InputStream is = uc.getInputStream();
				InputStreamReader isr = new InputStreamReader(is, "GB2312");// 编码格式GB2312
				BufferedReader br = new BufferedReader(isr);
				// 各线程互斥访问文件编号，以防重复
				Integer no;
				synchronized (obj) {
					no = ++file_no;
				}
				File f = new File(file_path + "WSC " + no.toString() + ".txt");
				PrintWriter pw = new PrintWriter(f);
				Pattern p = Pattern.compile(regular_expression);
				for (String line = br.readLine(); line != null; line = br.readLine()) {
					pw.println(line);// 将读取的网页源码写入文件
					// 按正则表达式规则匹配简历相关的URL
					Matcher m = p.matcher(line);
					while (m.find()) {
						String href = m.group();
						href = href.substring(href.indexOf("http"));
						href = href.substring(0, href.indexOf("\""));
						// http:或https:开头的URL进入等待队列
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
			over.add(url);// 将当前已爬取的网页URL放入已爬取队列
			System.out.println("网页" + url + "爬取完成，已爬取网页数量：" + over.size() + "，待爬取网页数量：" + wait.size());
		}
		// 等待队列中还有待爬取的URL，则唤醒一个处于等待状态的线程
		if (wait.size() > 0) {
			synchronized (obj) {
				obj.notify();
			}
		} else {
			System.out.println("网站爬取结束......");
		}
	}

	// 线程任务
	public class MyThread extends Thread {
		public void run() {
			while (true) {
				if (wait.size() > 0) {
					String url = getURL();
					work(url, depth.get(url));
				} else {
					System.out.println("线程" + this.getName() + "空闲，等待爬取...");
					free_thread++;
					// 利用Object对象让线程进入等待状态
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
