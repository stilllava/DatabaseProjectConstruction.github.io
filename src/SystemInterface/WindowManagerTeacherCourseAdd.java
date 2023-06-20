package SystemInterface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Calendar;
import java.util.Random;

public class WindowManagerTeacherCourseAdd extends JFrame implements ActionListener {
    JPanel panel = new JPanel();
    JFrame frame1 = new JFrame();
    JLabel labelSemester = new JLabel("学期");
    JComboBox comSearchSemester = new JComboBox();
    JLabel labelEmp_no = new JLabel("教工号");
    JTextField txtEmp_no = new JTextField(12);
    JLabel labelCno = new JLabel("课程号");
    JTextField txtCno = new JTextField(12);
    JLabel labelCourseOfferedSdeNo = new JLabel("允许选课院系");
    JTextField txtCourseOfferedSdeNo = new JTextField(12);
    JLabel labelCname = new JLabel("课程名");
    JTextField txtCname = new JTextField(12);
    JLabel labelCcredit = new JLabel("学分");
    JTextField txtCcredit = new JTextField(12);
    JLabel labelStuotal = new JLabel("可选学生数");
    JTextField txtStuotal = new JTextField(12);
    JButton btnAdd = new JButton("添加");
    JButton btnUpdate = new JButton("修改");
    public WindowManagerTeacherCourseAdd() {
        frame1.setTitle("教务管理系统-管理员界面-教师授课管理-添加课程信息");
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
        panel.add(labelStuotal);panel.add(txtStuotal);
        panel.add(btnAdd);panel.add(btnUpdate);
        int date = Calendar.getInstance().get(Calendar.YEAR);
        int i = date - 10;
        for (; i < date + 1; i++) {
            comSearchSemester.addItem(i + "-" + (i + 1) + "年第1学期");
            comSearchSemester.addItem(i + "-" + (i + 1) + "年第2学期");
        }
        btnAdd.addActionListener(this);
        btnUpdate.addActionListener(this);
    }
    public static void main(String[] args) {
        new WindowManagerTeacherCourseAdd();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdd){
            if(comSearchSemester.toString().equals("") == false
                    &&txtEmp_no.getText().trim().equals("") == false
                    && txtCno.getText().trim().equals("") == false
                    && txtCourseOfferedSdeNo.getText().trim().equals("") == false
                    && txtCname.getText().trim().equals("") == false
                    && txtCcredit.getText().trim().equals("") == false
                    && txtStuotal.getText().trim().equals("") == false){
                String Driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
                String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Academic_Affairs_Management_System_20211576;encrypt=false";
                String userName = "s20211576"; // 默认用户名
                String userPwd = "s20211576"; // 密码
                String sql = "insert into Course_Available_20211576(Semester,Emp_no,Cno,Course_Offered_Sde_no,Cname,Credit,Stu_Total) values ('" + comSearchSemester.getSelectedItem() + "','" + txtEmp_no.getText() + "','" + txtCno.getText() + "','" + txtCourseOfferedSdeNo.getText() + "','" + txtCname.getText() + "','" + txtCcredit.getText() + "',37)";
                Connection dbConn = null;
                try {
                    Class.forName(Driver);
                    dbConn = DriverManager.getConnection(url, userName, userPwd);
                    Statement stmt = dbConn.createStatement();
                    System.out.println(sql);
                    ResultSet rs = stmt.executeQuery(sql);
                    JOptionPane.showMessageDialog(null, "添加成功,请重新打开界面！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    rs.close();
                    stmt.close();
                    dbConn.close();
                } catch (Exception em) {
                    em.printStackTrace();
                    frame1.dispose();
                } finally {
                    try {
                    } catch (Exception ec) {
                        ec.printStackTrace();
                    }
                }
            }
            else{
                frame1.dispose();
                JOptionPane.showMessageDialog(null, "请填写完整信息!", "提示", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        if(e.getSource() == btnUpdate){
            if(comSearchSemester.toString().equals("") == false
                    &&txtEmp_no.getText().trim().equals("") == false
                    && txtCno.getText().trim().equals("") == false
                    && txtCourseOfferedSdeNo.getText().trim().equals("") == false
                    && (txtCname.getText().trim().equals("") == true
                    || txtCcredit.getText().trim().equals("") == true
                    || txtStuotal.getText().trim().equals("") == true)){
                String Driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
                String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Academic_Affairs_Management_System_20211576;encrypt=false";
                String userName = "s20211576"; // 默认用户名
                String userPwd = "s20211576"; // 密码
                String sql = null;
                if(txtCname.getText().trim().equals("") == false)
                    sql = "update Academic_Affairs_Management_System_20211576.dbo.Course_Available_20211576 set Cname = '"+txtCname.getText()+"' where Emp_no = '"+txtEmp_no.getText()+"' and Semester = '"+comSearchSemester.getSelectedItem()+"' and Cno = '"+txtCno.getText()+"' and Course_Offered_Sde_no = '"+txtCourseOfferedSdeNo.getText()+"'";
                else if(txtCcredit.getText().trim().equals("") == false)
                    sql = "update Academic_Affairs_Management_System_20211576.dbo.Course_Available_20211576 set Credit = '"+txtCcredit.getText()+"' where Emp_no = '"+txtEmp_no.getText()+"' and Semester = '"+comSearchSemester.getSelectedItem()+"' and Cno = '"+txtCno.getText()+"' and Course_Offered_Sde_no = '"+txtCourseOfferedSdeNo.getText()+"'";
                else if(txtStuotal.getText().trim().equals("") == false)
                    sql = "update Academic_Affairs_Management_System_20211576.dbo.Course_Available_20211576 set Stu_Total = '"+txtStuotal.getText()+"' where Emp_no = '"+txtEmp_no.getText()+"' and Semester = '"+comSearchSemester.getSelectedItem()+"' and Cno = '"+txtCno.getText()+"' and Course_Offered_Sde_no = '"+txtCourseOfferedSdeNo.getText()+"'";
                Connection dbConn = null;
                try {
                    Class.forName(Driver);
                    dbConn = DriverManager.getConnection(url, userName, userPwd);
                    System.out.println(sql); // 如果连接成功 控制台输出
                    JOptionPane.showMessageDialog(null, "授课信息修改成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    Statement stmt = dbConn.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
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
            else{
                JOptionPane.showMessageDialog(null, "工号和（姓名，性别，所属系别编号职位中至少有一项）不为空!");
            }
        }
    }
}
