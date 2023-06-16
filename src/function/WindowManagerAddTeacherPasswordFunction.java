package function;

import javax.swing.*;
import java.sql.*;
import java.util.Calendar;

public class WindowManagerAddTeacherPasswordFunction {
    public void add(String txtEmp_No, String txtEmp_name, String txtSex, String txtEmp_Sdept, String txtPosition){
        // 检查各个位置是否为空
        if (txtEmp_No.toString().trim().equals("") || txtEmp_name.toString().trim().equals("")
                || txtSex.toString().trim().equals("") || txtEmp_Sdept.toString().trim().equals("")
                || txtPosition.toString().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "请填写完整信息", "提示", JOptionPane.ERROR_MESSAGE);
        } else {
            String emp_no = txtEmp_No.toString().trim();
            String emp_name = txtEmp_name.toString().trim();
            String sex = txtSex.toString().trim();
            String emp_sdept = txtEmp_Sdept.toString().trim();
            String position = txtPosition.toString().trim();
            String Driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Academic_Affairs_Management_System_20211576;encrypt=false";
            String userName = "s20211576"; // 默认用户名
            String userPwd = "s20211576"; // 密码
            Connection conn = null;
            try {
                Class.forName(Driver);
                conn = DriverManager.getConnection(url, userName, userPwd);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            Statement stml = null;
            ResultSet rs;
            Statement stmt = null;
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT COUNT(*) FROM Teacher_20211576 WHERE Emp_no='" + emp_no + "'");
                rs.next();
                int count = rs.getInt(1);
                if (count > 0) {
                    JOptionPane.showMessageDialog(null, "该工号已存在", "提示", JOptionPane.ERROR_MESSAGE);
                } else {
                    // 向 Teacher_20211576 表中插入教师信息
                    String sql = "INSERT INTO Teacher_20211576(Emp_no,Emp_name,Sex,Emp_sdept,Position) VALUES('" + emp_no + "','" + emp_name + "','" + sex + "','" + emp_sdept + "','" + position + "')";
                    stmt.executeUpdate(sql);
                    JOptionPane.showMessageDialog(null, "添加成功");
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    public static void main(String[] args) {
        int dateEnquire = Calendar.getInstance().get(Calendar.YEAR);
        int m=dateEnquire-10;
        for(;m<dateEnquire+1;m++){
            System.out.println(m+"-"+(m+1)+"学年第一学期");
            System.out.println(m+"-"+(m+1)+"学年第二学期");
        }
    }
}
