package hnust.cwz.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class InsertRawData {
	private static String file_path;// 文件路径
	private static int file_count;// 文件数目
	private static int file_no;// 文件编号

	public static void main(String[] args) {
		file_path = "G:\\本科毕业设计\\简历数据\\有效数据集（已清洗）\\";
		file_count = getFileCount();
		file_no = 0;// （中央）
		//file_no = 58;// （地方）
		while (++file_no <= file_count) {
			Integer no = file_no;
			work(no);
		}
		System.out.println("原始数据保存完成！");
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
	public static void insert(String big_str) {
		DBOperation dbo = new DBOperation("curriculum_vitae");
		if (dbo.createConnection()) {
			String sql = "insert into raw_data (简历编号,原始数据) values ('" + file_no + "0000','" + big_str + "')";
			dbo.operate(sql);
			dbo.close();
		}
	}

	public static void work(Integer no) {
		try {
			File f = new File(file_path + "effective data " + no.toString() + ".txt");
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String big_str = "";
			for (String effective_data = br.readLine(); effective_data != null; effective_data = br.readLine()) {
				big_str += effective_data;
			}
			insert(big_str);
			br.close();
			fr.close();
			System.out.println("文件effective data " + no.toString() + "的原始数据保存成功！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
