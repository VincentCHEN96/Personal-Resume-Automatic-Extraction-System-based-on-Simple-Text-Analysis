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
	private static String file_path_read;// 文件读取路径
	private static String file_path_write;// 文件写入路径
	private static int file_count;// 文件数目
	private static int file_no;// 文件编号
	private static List<String> buffer;// 数据缓冲

	public static void main(String[] args) {
		file_path_read = "G:\\本科毕业设计\\简历数据\\原始数据集\\";
		file_path_write = "G:\\本科毕业设计\\简历数据\\有效数据集（已清洗）\\";
		file_count = getFileCount();
		file_no = 0;// （中央）
		//file_no = 58;// （地方）
		while (++file_no <= file_count) {
			buffer = new ArrayList<String>();
			Integer no = file_no;
			work(no);
		}
		System.out.println("数据清洗完成！");
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

	// 判断字符串是否包含中文字符
	public static boolean isChinese(String str) {
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher matcher = p.matcher(str);
		boolean flag = false;
		if (matcher.find())
			flag = true;
		return flag;
	}

	// 数据清洗
	public static String cleaning(String raw_data) {
		raw_data = raw_data.substring(1 + raw_data.indexOf("	"));// 去除每行首部的"
																	// "（中央）
		//raw_data = raw_data.substring(4 + raw_data.indexOf("	<p>"));// 去除每行首部的"
																	// <p>"（地方）
		if (raw_data.startsWith("男") || raw_data.startsWith("女") || raw_data.startsWith("现任")// 判断为基本信息或现任职位条目
				|| raw_data.charAt(1 + raw_data.indexOf("，")) == '男'
				|| raw_data.charAt(1 + raw_data.indexOf("，")) == '女') {
			raw_data = raw_data.substring(0, raw_data.indexOf("</p>"));// 去掉尾部的"</p>"（中央）
			raw_data = raw_data.substring(0, raw_data.indexOf("。"));// 截掉"。"之后的杂乱信息
			return raw_data;
		} else if (raw_data.startsWith("19") || raw_data.startsWith("20")) {// 判断为经历信息条目
			// 去掉起止时间与地点职务之间的杂乱信息（中央）
			String str1, str2;
			if (raw_data.indexOf("年") != -1) {
				str1 = raw_data.substring(0, 1 + raw_data.indexOf("年"));
				str2 = raw_data.substring(1 + raw_data.indexOf("年"));
			} else if (raw_data.indexOf("―") != -1) {
				str1 = raw_data.substring(0, 1 + raw_data.indexOf("―"));
				str2 = raw_data.substring(1 + raw_data.indexOf("―"));
			} else {
				str1 = raw_data.substring(0, 1 + raw_data.indexOf("－"));
				str2 = raw_data.substring(1 + raw_data.indexOf("－"));
			}
			// 找到地点职务信息的起点（第一个中文字符）并截取之后的数据
			int i = 0;
			while (!isChinese(String.valueOf(str2.charAt(i))))
				++i;
			str2 = str2.substring(i);
			raw_data = str1 + str2;// 拼接成一条完整的经历信息条目
			raw_data = raw_data.substring(0, raw_data.indexOf("</p>"));// 去掉尾部的"</p>"
			return raw_data;
		} else {// 判断为姓名单独一行的情况（中央）
			// 判断"</p>"出现在第九个字符或之前，并且前一个字符是中文
			if ((raw_data.indexOf("</p>") <= 8)
					&& isChinese(String.valueOf(raw_data.charAt(raw_data.indexOf("</p>") - 1)))) {
				raw_data = raw_data.substring(0, raw_data.indexOf("</p>"));// 去掉尾部的"</p>"
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
			// 读出源文件数据至数据缓冲
			for (String raw_data = br.readLine(); raw_data != null; raw_data = br.readLine()) {
				buffer.add(raw_data);
			}
			File f_write = new File(file_path_write + "effective data " + no.toString() + ".txt");
			PrintWriter pw = new PrintWriter(f_write);
			// 处理数据缓冲中的各条数据
			for (String raw_data : buffer) {
				String effective_data = cleaning(raw_data);// 清洗源数据
				if (effective_data != null)
					pw.println(effective_data);
			}
			pw.close();
			br.close();
			fr.close();
			System.out.println("文件raw data " + no.toString() + "清洗为文件effective data " + no.toString() + "成功！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
