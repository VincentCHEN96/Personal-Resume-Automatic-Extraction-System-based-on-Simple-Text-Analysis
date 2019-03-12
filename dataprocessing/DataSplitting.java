package hnust.cwz.dataprocessing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import hnust.cwz.database.ConsultDictionary;

public class DataSplitting {
	private static String file_path_read;// 文件读取路径
	private static String file_path_write;// 文件写入路径
	private static int file_count;// 文件数目
	private static int file_no;// 文件编号
	private static List<String> buffer;// 数据缓冲

	public static void main(String[] args) {
		file_path_read = "G:\\本科毕业设计\\简历数据\\有效数据集（已清洗）\\";
		file_path_write = "G:\\本科毕业设计\\简历数据\\格式化数据集（已拆分）\\";
		file_count = getFileCount();
		file_no = 0;// （中央）
		//file_no = 58;// （地方）
		while (++file_no <= file_count) {
			buffer = new ArrayList<String>();
			Integer no = file_no;
			work(no);
		}
		System.out.println("数据拆分完成！");
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

	// 查字典
	public static void consultDictionary(String str, PrintWriter pw) {
		ConsultDictionary cd = new ConsultDictionary();// 查字典类对象
		if (str != cd.consultSex(str))// 判断为性别信息
			pw.println(cd.consultSex(str));
		else if (str != cd.consultNationality(str))// 判断为民族信息
			pw.println(cd.consultNationality(str));
		else if (str != cd.consultNativePlace(str))// 判断为籍贯信息
			pw.println(cd.consultNativePlace(str));
		else if (str != cd.consultParty(str))// 判断为党派信息
			pw.println(cd.consultParty(str));
		else if (str != cd.consultSchool(str))// 判断为毕业学校信息
			pw.println(cd.consultSchool(str));
	}

	// 数据信息拆分
	public static void splitting(String effective_data, PrintWriter pw) {
		if (effective_data.startsWith("现任")) {// 判断为现任职位信息
			String str1 = effective_data.substring(0, 2) + "职位";
			String str2 = effective_data.substring(2);
			effective_data = str1 + "	" + str2;
			pw.println(effective_data);
		} else if (effective_data.startsWith("19") || effective_data.startsWith("20")) {// 判断为经历信息
			// 拆分起止时间、地点、职务信息
			String str1 = null, str2 = null;
			if (effective_data.indexOf("年") != -1) {
				str1 = effective_data.substring(0, 1 + effective_data.indexOf("年"));
				str2 = effective_data.substring(1 + effective_data.indexOf("年"));
			} else if (effective_data.indexOf("―") != -1) {
				str1 = effective_data.substring(0, 1 + effective_data.indexOf("―"));
				str2 = effective_data.substring(1 + effective_data.indexOf("―"));
			} else {
				str1 = effective_data.substring(0, 1 + effective_data.indexOf("－"));
				str2 = effective_data.substring(1 + effective_data.indexOf("－"));
			}
			/*if (effective_data.indexOf(" ") != -1) {
				str1 = effective_data.substring(0, effective_data.indexOf(" "));
				str2 = effective_data.substring(1 + effective_data.indexOf(" "));
			} else if (effective_data.indexOf("，") != -1) {
				str1 = effective_data.substring(0, effective_data.indexOf("，"));
				str2 = effective_data.substring(1 + effective_data.indexOf("，"));
			}*/
			String[] string = str2.split("，");// 以"，"为分隔符再拆分
			String str3 = "";
			for (int i = 0; i < string.length; i++) {
				str3 += (string[i] + "	");
			}
			effective_data = str1 + "	" + str3;
			pw.println(effective_data);
		} else {// 判断为基本信息
			boolean flag = true;// 判断姓名单独一行的标志位
			// 以逗号做分割符分割数据
			while (effective_data.indexOf("，") != -1) {
				String str = effective_data.substring(0, effective_data.indexOf("，"));
				if (str.indexOf("月生") != -1) {// 拆分出生年月字段
					str = "出生年月	" + str.substring(0, str.indexOf("生"));
					pw.println(str);
				} else if (str.indexOf("出生") != -1) {// 拆分出生年月字段
					str = "出生年月	" + str.substring(0, str.indexOf("出"));
					pw.println(str);
				} else if (str.indexOf("参加工作") != -1) {// 拆分参加工作时间字段
					str = "参加工作时间	" + str.substring(0, str.indexOf("参加工作"));
					pw.println(str);
				} else if (flag && !(str.startsWith("男") || str.startsWith("女"))) {// 拆分姓名字段
					pw.println("姓名	" + str);
					flag = false;
				} else {
					consultDictionary(str, pw);
					flag = false;
				}
				effective_data = effective_data.substring(1 + effective_data.indexOf("，"));
			}
			if (flag) {// 判断为姓名单独一行的情况
				pw.println("姓名	" + effective_data);
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
			// 读出源文件数据至数据缓冲
			for (String effective_data = br.readLine(); effective_data != null; effective_data = br.readLine()) {
				buffer.add(effective_data);
			}
			File f_write = new File(file_path_write + "formatted data " + no.toString() + ".txt");// 创建存放数据的文本文件，带编号命名，以防重复
			PrintWriter pw = new PrintWriter(f_write);
			// 处理数据缓冲中的各条数据
			for (String effective_data : buffer) {
				splitting(effective_data, pw);// 拆分源数据
			}
			pw.close();
			br.close();
			fr.close();
			System.out
					.println("文件effective data " + no.toString() + "拆分简历数据至文件formatted data " + no.toString() + "成功！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
