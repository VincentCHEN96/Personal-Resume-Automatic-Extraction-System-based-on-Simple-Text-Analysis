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
	private static String file_path_read;// 文件读取路径
	private static String file_path_write;// 文件写入路径
	private static int file_count;// 文件数目
	private static int file_no;// 文件编号
	private static List<String> buffer;// 数据缓冲
	private static String regular_expression;// 正则表达式（匹配简历信息）

	public static void main(String[] args) {
		file_path_read = "G:\\本科毕业设计\\简历数据\\网页源代码\\";
		file_path_write = "G:\\本科毕业设计\\简历数据\\原始数据集\\";
		file_count = getFileCount();
		file_no = 0;// (中央)
		//file_no = 58;// （地方）
		regular_expression = "	[\u4e00-\u9fa5-0-9].+</p>";// 观察网页源码可知，其中的简历信息都是
															// 汉字或数字...</p>的形式（中央）
		//regular_expression = "	<p>.+";// 观察网页源码可知，其中的简历信息都是
															// <p>...的形式（地方）
		while (++file_no <= file_count) {
			buffer = new ArrayList<String>();
			Integer no = file_no;
			work(no);
		}
		System.out.println("数据提取完成！");
	}

	// 获取文件夹中的文件数目
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
			// 读出源文件数据至数据缓冲
			for (String wsc = br.readLine(); wsc != null; wsc = br.readLine()) {
				buffer.add(wsc);
			}
			File f_write = new File(file_path_write + "raw data " + no.toString() + ".txt");
			PrintWriter pw = new PrintWriter(f_write);
			// 处理数据缓冲中的各条数据
			Pattern p = Pattern.compile(regular_expression);
			for (String wsc : buffer) {
				// 按正则表达式规则匹配简历信息
				Matcher m = p.matcher(wsc);
				while (m.find()) {
					String raw_data = m.group();
					pw.println(raw_data);
				}
			}
			pw.close();
			br.close();
			fr.close();
			System.out.println("从文件WSC " + no.toString() + "提取原始简历数据至文件raw data " + no.toString() + "成功！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
