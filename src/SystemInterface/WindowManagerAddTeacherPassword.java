package SystemInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class WindowManagerAddTeacherPassword extends JFrame implements ActionListener {
    JPanel panel = new JPanel();
    JPanel panel1 = new JPanel();
    JFrame frame1 = new JFrame();
    JLabel labelEmp_No = new JLabel("教师工号");
    JTextField txtEmp_No = new JTextField(10);
    JLabel labelEmp_name = new JLabel("教师姓名");
    JTextField txtEmp_name = new JTextField(10);
    JLabel labelSex = new JLabel("性别");
    JTextField txtSex = new JTextField(10);
    JLabel labelEmp_SdeptNo = new JLabel("所属院系编号");
    JTextField txtEmp_SdeptNo = new JTextField(10);
    JButton btnAdd = new JButton("添加");

    public WindowManagerAddTeacherPassword() {
        frame1.setTitle("教务管理系统管理员界面--添加管理员用户");
        frame1.setVisible(true);
        frame1.setSize(400, 400);
        frame1.setLocation(300, 249);
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame1.add(panel, BorderLayout.CENTER);
        panel.setLayout(new GridLayout(4,2));
        frame1.add(panel1, BorderLayout.SOUTH);
        panel.add(labelEmp_No);panel.add(txtEmp_No);
        panel.add(labelEmp_name);panel.add(txtEmp_name);
        panel.add(labelSex);panel.add(txtSex);
        panel.add(labelEmp_SdeptNo);panel.add(txtEmp_SdeptNo);
        panel1.add(btnAdd);
        btnAdd.addActionListener(this);
    }

    public static void main(String[] args) {
        new WindowManagerAddTeacherPassword();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdd) {
            frame1.dispose();
            String Driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Academic_Affairs_Management_System_20211576;encrypt=false";
            String userName = "s20211576"; // 默认用户名
            String userPwd = "s20211576"; // 密码
            String sql1 = "use Academic_Affairs_Management_System_20211576 insert into Teacher_20211576 values ('" + txtEmp_No.getText().toString() + "','" + txtEmp_name.getText().toString() + "','" + txtSex.getText().toString() + "','" + txtEmp_SdeptNo.getText().toString() + "','教务管理人员')"; // SQL语句
            Connection dbConn = null;
            try {
                Class.forName(Driver);
                dbConn = DriverManager.getConnection(url, userName, userPwd);
                System.out.println("Connection Successful!"); // 如果连接成功 控制台输出
                Statement stmt = dbConn.createStatement();
                ResultSet rs = stmt.executeQuery(sql1);
                JOptionPane.showMessageDialog(null, "教师表添加成功！密码表添加成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                rs.close();
                stmt.close();
                dbConn.close();
            } catch (Exception em) {
                em.printStackTrace();
            } finally {
                try {
                } catch (Exception ec) {
                    ec.printStackTrace();
                }
            }
        }
    }
}
