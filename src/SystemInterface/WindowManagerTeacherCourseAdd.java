package SystemInterface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Calendar;

public class WindowManagerTeacherCourseAdd extends JFrame implements ActionListener {
    JPanel panel = new JPanel();
    JFrame frame1 = new JFrame();
    JLabel labelSemester = new JLabel("学期");
    JComboBox comSearchSemester = new JComboBox();
    JLabel labelEmp_no = new JLabel("教工号");
    JTextField txtEmp_no = new JTextField(12);
    JLabel labelCno = new JLabel("课程号");
    JPasswordField txtCno = new JPasswordField(12);
    JLabel labelCourseOfferedSdeNo = new JLabel("允许选课院系");
    JTextField txtCourseOfferedSdeNo = new JTextField(12);
    JLabel labelCname = new JLabel("课程名");
    JTextField txtCname = new JTextField(12);
    JLabel labelCcredit = new JLabel("学分");
    JTextField txtCcredit = new JTextField(12);
    JLabel labelStuTotal = new JLabel("学生人数");
    JTextField txtStuTotal = new JTextField(12);
    JButton btnAdd = new JButton("添加");
    public WindowManagerTeacherCourseAdd() {
        frame1.setTitle("教务管理系统学生界面");
        frame1.setVisible(true);
        frame1.setSize(380,400);
        frame1.setLocation(300, 249);
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame1.add(panel, BorderLayout.CENTER);
        panel.setLayout(new GridLayout(8,2));
        panel.add(labelSemester);panel.add(comSearchSemester);
        panel.add(labelEmp_no);panel.add(txtEmp_no);
        panel.add(labelCno);panel.add(txtCno);
        panel.add(labelCourseOfferedSdeNo);panel.add(txtCourseOfferedSdeNo);
        panel.add(labelCname);panel.add(txtCname);
        panel.add(labelCcredit);panel.add(txtCcredit);
        panel.add(labelStuTotal);panel.add(txtStuTotal);
        panel.add(btnAdd);
        int date = Calendar.getInstance().get(Calendar.YEAR);
        int i = date - 10;
        for (; i < date + 1; i++) {
            comSearchSemester.addItem(i + "-" + (i + 1) + "年第1学期");
            comSearchSemester.addItem(i + "-" + (i + 1) + "年第2学期");
        }
        btnAdd.addActionListener(this);
    }
    public static void main(String[] args) {
        new WindowManagerTeacherCourseAdd();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdd){
            String Driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Academic_Affairs_Management_System_20211576;encrypt=false";
            String userName = "s20211576"; // 默认用户名
            String userPwd = "s20211576"; // 密码
            String sql = "insert into Course_Available_20211576(Semester,Emp_no,Cno,Course_Offered_Sde_no,Cname,Credit,Stu_Total) values ('" + comSearchSemester.getSelectedItem() + "','" + txtEmp_no.getText() + "','" + txtCno.getText() + "','" + txtCourseOfferedSdeNo.getText() + "','" + txtCname.getText() + "','" + txtCcredit.getText() + "','" + txtStuTotal.getText() + "')";
            Connection dbConn = null;
            try {
                Class.forName(Driver);
                dbConn = DriverManager.getConnection(url, userName, userPwd);
                Statement stmt = dbConn.createStatement();
                System.out.println(sql);
                ResultSet rs = stmt.executeQuery(sql);
                rs.close();
                stmt.close();
                dbConn.close();
            } catch (Exception em) {
                em.printStackTrace();
                frame1.dispose();
                JOptionPane.showMessageDialog(null, "添加失败,请重新打开界面！", "提示", JOptionPane.INFORMATION_MESSAGE);
            } finally {
                try {
                } catch (Exception ec) {
                    ec.printStackTrace();
                }
            }
        }
    }
}
