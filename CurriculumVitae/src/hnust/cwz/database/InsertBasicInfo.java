package hnust.cwz.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import hnust.cwz.encapsulation.BasicInformation;

public class InsertBasicInfo {
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
		System.out.println("基本信息数据保存完成！");
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

	// 数据封装
	public static BasicInformation dataEncapsulation(BasicInformation bi, String formatted_data) {
		bi.setNo(file_no);
		String str = formatted_data.substring(1 + formatted_data.indexOf("	"));
		if (formatted_data.startsWith("姓名"))
			bi.setName(str);
		else if (formatted_data.startsWith("性别"))
			bi.setSex(str);
		else if (formatted_data.startsWith("民族"))
			bi.setNationality(str);
		else if (formatted_data.startsWith("出生年月"))
			bi.setBirthday(str);
		else if (formatted_data.startsWith("籍贯"))
			bi.setNativePlace(str);
		else if (formatted_data.startsWith("参加工作时间"))
			bi.setDate(str);
		else if (formatted_data.startsWith("党派"))
			bi.setParty(str);
		else if (formatted_data.startsWith("毕业学校") && bi.getSchool() == null)
			bi.setSchool(str);
		return bi;
	}

	// 数据插入
	public static void insert(BasicInformation bi) {
		DBOperation dbo = new DBOperation("curriculum_vitae");
		if (dbo.createConnection()) {
			String sql = "insert into basic_information (简历编号,姓名,性别,民族,出生年月,籍贯,参加工作时间,党派,毕业学校) values ('" + bi.getNo()
					+ "0000','" + bi.getName() + "','" + bi.getSex() + "','" + bi.getNationality() + "','"
					+ bi.getBirthday() + "','" + bi.getNativePlace() + "','" + bi.getDate() + "','" + bi.getParty()
					+ "','" + bi.getSchool() + "')";
			dbo.operate(sql);
			dbo.close();
		}
	}

	public static void work(Integer no) {
		try {
			File f = new File(file_path + "formatted data " + no.toString() + ".txt");
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			BasicInformation bi = new BasicInformation();
			for (String formatted_data = br.readLine(); formatted_data != null; formatted_data = br.readLine()) {
				if (!(formatted_data.startsWith("19") || formatted_data.startsWith("20")
						|| formatted_data.startsWith("现任职位")))
					bi = dataEncapsulation(bi, formatted_data);
			}
			insert(bi);
			br.close();
			fr.close();
			System.out.println("文件formatted data " + no.toString() + "的基本信息数据保存成功！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
