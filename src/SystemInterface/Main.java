package SystemInterface;

import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        // 所有可选的学期、Sde_no 和课程信息
        String[] semesters = {"2021-2022年第1学期", "2021-2022年第2学期", "2022-2023年第1学期", "2022-2023年第2学期", "2023-2024年第1学期"};
        String[] sde_nos = {"061", "062", "063", "064", "065"};
        String[][] courses = {{"0036", "网络安全技术", "3"}, {"0038", "虚拟现实技术", "3"}, {"0039", "大数据应用案例分析", "2"}};
        String emp_no = "201919190";

        // 随机生成选课人数
        Random random = new Random();
        ArrayList<String[]> rows = new ArrayList<String[]>();
        for (String semester : semesters) {
            for (String sde_no : sde_nos) {
                for (String[] course : courses) {
                    int stu_total = random.nextInt(21) + 30;  // 随机生成 30-50 的整数
                    String[] row = {"('" + semester + "'", "'" + emp_no + "'", "'" + course[0] + "'", "'" + sde_no + "'", "'" + course[1] + "'", course[2], String.valueOf(stu_total), "0"};
                    rows.add(row);
                }
            }
        }

        // 输出所有选课情况
        for (String[] row : rows) {
            System.out.print(String.join(",", row));
            System.out.println("),");
        }
    }
}
