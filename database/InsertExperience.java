package hnust.cwz.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import hnust.cwz.encapsulation.Experience;

public class InsertExperience {
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
		System.out.println("经历数据保存完成！");
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
	public static Experience[] dataEncapsulation(String formatted_data) {
		// 记录数据formatted_data对应所有经历信息的 起止时间
		Experience e = new Experience();
		e.setNo(file_no);
		if (formatted_data.indexOf("―") != -1) {
			e.setStart_date(formatted_data.substring(0, formatted_data.indexOf("―")) + "年");
			if (formatted_data.indexOf("年") != -1)
				e.setEnd_date(
						formatted_data.substring(1 + formatted_data.indexOf("―"), formatted_data.indexOf("年")) + "年");
		} else if (formatted_data.indexOf("－") != -1) {
			e.setStart_date(formatted_data.substring(0, formatted_data.indexOf("－")) + "年");
			if (formatted_data.indexOf("年") != -1)
				e.setEnd_date(
						formatted_data.substring(1 + formatted_data.indexOf("－"), formatted_data.indexOf("年")) + "年");
		}
		/*if ((formatted_data.indexOf("-") != -1) && (formatted_data.indexOf("-") < formatted_data.indexOf("	"))) {
			e.setStart_date(formatted_data.substring(0, formatted_data.indexOf("-")));
			e.setEnd_date(
					formatted_data.substring(1 + formatted_data.indexOf("-"), formatted_data.indexOf("	")));
		} else if ((formatted_data.indexOf("－") != -1) && (formatted_data.indexOf("－") < formatted_data.indexOf("	"))) {
			e.setStart_date(formatted_data.substring(0, formatted_data.indexOf("－")));
			e.setEnd_date(
					formatted_data.substring(1 + formatted_data.indexOf("－"), formatted_data.indexOf("	")));
		} else if ((formatted_data.indexOf("―") != -1) && (formatted_data.indexOf("―") < formatted_data.indexOf("	"))) {
			e.setStart_date(formatted_data.substring(0, formatted_data.indexOf("―")));
			e.setEnd_date(
					formatted_data.substring(1 + formatted_data.indexOf("―"), formatted_data.indexOf("	")));
		} else
			e.setStart_date(formatted_data.substring(0, formatted_data.indexOf("	")));*/
		String str = formatted_data.substring(1 + formatted_data.indexOf("	"));// 拆分出整体的经历信息
		String[] string = str.split("	");// 以" "为分隔符拆分出每条经历信息
		Experience[] experience = new Experience[string.length];// 共有string.length条经历信息
		// 优先按市级拆分经历信息中的地点，其次按省级拆分
		ConsultDictionary cd = new ConsultDictionary();
		for (int i = 0; i < string.length; i++) {
			experience[i] = new Experience();
			experience[i].setNo(e.getNo());
			experience[i].setStart_date(e.getStart_date());
			experience[i].setEnd_date(e.getEnd_date());
			String str1 = string[i];
			String str2 = cd.consultCity(str1);
			if (str2 != str1) {
				experience[i].setPlace(str2.substring(0, str2.indexOf("	")));
				experience[i].setPosition(str2.substring(1 + str2.indexOf("	")));
			} else {
				str2 = cd.consultProvince(str1);
				if (str2 != str1) {
					experience[i].setPlace(str2.substring(0, str2.indexOf("	")));
					experience[i].setPosition(str2.substring(1 + str2.indexOf("	")));
				} else
					experience[i].setPosition(str2);
			}
			// 解决"省委"、"市委"拆分错误问题
			String str3 = experience[i].getPlace();
			String str4 = experience[i].getPosition();
			if (str3 != null && str4 != null)
				if (str3.endsWith("省") && str4.startsWith("委"))
					experience[i].setPosition("省" + str4);
				else if (str3.endsWith("市") && str4.startsWith("委"))
					experience[i].setPosition("市" + str4);
		}
		return experience;
	}

	// 数据插入
	public static void insert(Experience experience) {
		DBOperation dbo = new DBOperation("curriculum_vitae");
		if (dbo.createConnection()) {
			String sql = "insert into experience (简历编号,起始时间,截止时间,地点,职位) values ('" + experience.getNo() + "0000','"
					+ experience.getStart_date() + "','" + experience.getEnd_date() + "','" + experience.getPlace()
					+ "','" + experience.getPosition() + "')";
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
				if (formatted_data.startsWith("19") || formatted_data.startsWith("20")) {
					Experience[] experience = dataEncapsulation(formatted_data);
					for (int i = 0; i < experience.length; i++)
						insert(experience[i]);
				}
			}
			br.close();
			fr.close();
			System.out.println("文件formatted data " + no.toString() + "的经历数据保存成功！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
