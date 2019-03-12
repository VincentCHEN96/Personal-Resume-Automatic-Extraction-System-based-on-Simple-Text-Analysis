package hnust.cwz.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class InsertPosition {
	private static String file_path;// 文件路径
	private static int file_count;// 文件数目
	private static int file_no;// 文件编号

	public static void main(String[] args) {
		file_path = "G:\\本科毕业设计\\简历数据\\格式化数据集（已拆分）\\";
		file_count = getFileCount();
		file_no = 0;// （中央）
		//file_no = 58;// （地方）
		while (++file_no <= file_count) {
			Integer no = file_no;
			work(no);
		}
		System.out.println("现任职位数据保存完成！");
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

	// 数据插入
	public static void insert(String formatted_data) {
		DBOperation dbo = new DBOperation("curriculum_vitae");
		if (dbo.createConnection()) {
			String sql, str1 = formatted_data.substring(1 + formatted_data.indexOf("	"));
			while (str1.indexOf("，") != -1) {
				String str2 = str1.substring(0, str1.indexOf("，"));
				sql = "insert into position (简历编号,现任职位) values ('" + file_no + "0000','" + str2 + "')";
				dbo.operate(sql);
				str1 = str1.substring(1 + str1.indexOf("，"));
			}
			sql = "insert into position (简历编号,现任职位) values ('" + file_no + "0000','" + str1 + "')";
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
				if (formatted_data.startsWith("现任职位"))
					insert(formatted_data);
			}
			br.close();
			fr.close();
			System.out.println("文件formatted data " + no.toString() + "的现任职位数据保存成功！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
