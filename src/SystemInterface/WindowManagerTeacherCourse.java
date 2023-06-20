package SystemInterface;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.Calendar;
import java.util.Vector;

import static javax.swing.UIManager.getInt;

public class WindowManagerTeacherCourse extends JFrame implements ActionListener {
    JPanel panel = new JPanel();
    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel panel4 = new JPanel();
    JPanel panel5 = new JPanel();
    JPanel panel6 = new JPanel();
    JPanel panel7 = new JPanel();
    JFrame frame1 = new JFrame();
    JLabel labelSemester = new JLabel("学期");
    JComboBox comlabelSearchSemester = new JComboBox();
    JLabel labelCno = new JLabel("课程号");
    JPasswordField txtCno = new JPasswordField(12);
    JLabel labelEmp_no = new JLabel("教工号");
    JTextField txtEmp_no = new JTextField(12);
    JLabel labelCourseOfferedSdeNo = new JLabel("允许选课院系");
    JTextField txtCourseOfferedSdeNo = new JTextField(12);
    JButton btnAdd = new JButton("添加/修改");
    JLabel labelSearchSemester = new JLabel("学期");
    JComboBox comSearchSemester = new JComboBox();
    JLabel labelSearchCname = new JLabel("课程名");
    JTextField txtSearchCname = new JTextField(12);
    JLabel labelSearchName = new JLabel("授课教师");
    JTextField txtSearchName = new JTextField(12);
    JButton btnSearchSemester = new JButton("查询当前学期教师授课情况");
    JButton btnSearchCname = new JButton("查询当前课程教师授课情况");
    JButton btnSearchName = new JButton("查询当前教师授课情况");
    JButton btnSearchCnameAndName = new JButton("查询当前学期当前课程当前教师授课情况");
    JButton btnList = new JButton("显示所有学期所有教师授课情况");
    String initialText = null;
    JTable table = new JTable();
    JScrollPane scrollPane = new JScrollPane(table);
    String[] columnNames = {"学期", "可选课系号", "可选课系名", "课程号", "课程名", "授课教师", "学分", "总学时"};
    Object[][] data = new Object[getColumns()][8];

    public WindowManagerTeacherCourse() {
        frame1.setTitle("教务管理系统管理员界面--教师授课管理");
        frame1.setVisible(true);
        frame1.setSize(620, 680);
        frame1.setLocation(10, 10);
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame1.add(panel, BorderLayout.NORTH);
        frame1.add(panel4, BorderLayout.CENTER);
        frame1.add(panel5, BorderLayout.SOUTH);
        panel.add(panel1);
        panel.add(panel2);
        panel1.setLayout(new GridLayout(4, 2));
        panel2.setLayout(new GridLayout(4, 1));
        panel1.add(labelSemester);
        panel1.add(comlabelSearchSemester);
        panel1.add(labelCno);
        panel1.add(txtCno);
        panel1.add(labelEmp_no);
        panel1.add(txtEmp_no);
        panel1.add(labelCourseOfferedSdeNo);
        panel1.add(txtCourseOfferedSdeNo);
        panel2.add(btnAdd);
        panel2.add(btnList);
        panel4.add(scrollPane);
        scrollPane.setViewportView(table);
        panel5.add(panel6, BorderLayout.EAST);
        panel5.add(panel7, BorderLayout.WEST);
        panel7.setLayout(new GridLayout(4, 2));
        panel6.setLayout(new GridLayout(4, 1));
        panel7.add(labelSearchSemester);
        panel7.add(comSearchSemester);
        panel7.add(labelSearchCname);
        panel7.add(txtSearchCname);
        panel7.add(labelSearchName);
        panel7.add(txtSearchName);
        panel6.add(btnSearchSemester);
        panel6.add(btnSearchCname);
        panel6.add(btnSearchName);
        panel6.add(btnSearchCnameAndName);
        int date = Calendar.getInstance().get(Calendar.YEAR);
        int i = date - 10;
        for (; i < date + 1; i++) {
            comSearchSemester.addItem(i + "-" + (i + 1) + "年第1学期");
            comlabelSearchSemester.addItem(i + "-" + (i + 1) + "年第1学期");
            comSearchSemester.addItem(i + "-" + (i + 1) + "年第2学期");
            comlabelSearchSemester.addItem(i + "-" + (i + 1) + "年第2学期");
        }
        comSearchSemester.setSelectedIndex(18);
        comlabelSearchSemester.setSelectedIndex(18);
        initialText = (String) comlabelSearchSemester.getSelectedItem();
        btnAdd.addActionListener(this);
        btnList.addActionListener(this);
        btnSearchSemester.addActionListener(this);
        btnSearchCname.addActionListener(this);
        btnSearchName.addActionListener(this);
        btnSearchCnameAndName.addActionListener(this);
    }

    public static void main(String[] args) {
        new WindowManagerTeacherCourse();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdd) {
            WindowManagerTeacherCourseAdd wmtca = new WindowManagerTeacherCourseAdd();
        }
        if (e.getSource() == btnList) {
            String sql = "use Academic_Affairs_Management_System_20211576 select * from Manager_Course_Teacher_manage_view";
            Connect(sql);
        }
        if (e.getSource() == btnSearchSemester) {
            String sql = "use Academic_Affairs_Management_System_20211576 select * from Manager_Course_Teacher_manage_view where Semester='" + comSearchSemester.getSelectedItem() + "'";
            Connect(sql);
        }
        if (e.getSource() == btnSearchCname) {
            String sql = "use Academic_Affairs_Management_System_20211576 select * from Manager_Course_Teacher_manage_view where Cname='" + txtSearchCname.getText() + "'";
            Connect(sql);
        }
        if (e.getSource() == btnSearchName) {
            String sql = "use Academic_Affairs_Management_System_20211576 select * from Manager_Course_Teacher_manage_view where Emp_name='" + txtSearchName.getText() + "'";
            Connect(sql);
        }
        if (e.getSource() == btnSearchCnameAndName) {
            String sql = "use Academic_Affairs_Management_System_20211576 select * from Manager_Course_Teacher_manage_view where Cname='" + txtSearchCname.getText() + "' and Emp_name='" + txtSearchName.getText() + "'";
            Connect(sql);
        }
    }
    public int getColumns () {
        String Driver2 = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Academic_Affairs_Management_System_20211576;encrypt=false";
        String userName = "s20211576"; // 默认用户名
        String userPwd = "s20211576"; // 密码
        Connection dbConn = null;
        try {
            Class.forName(Driver2);
            dbConn = DriverManager.getConnection(url, userName, userPwd);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        int columnCount;
        try {
            Statement stmt = dbConn.createStatement();
            ResultSet rs = stmt.executeQuery("use Academic_Affairs_Management_System_20211576 select * from Manager_Course_Teacher_manage_view");
            ResultSetMetaData rsmd = rs.getMetaData();
            columnCount = 0;
            while (rs.next()) {
                columnCount++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                dbConn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return columnCount;
    }
    public void Connect(String sql){
        for (int i = 0; i < getColumns(); i++) {
            for (int j = 0; j < 8; j++) {
                data[i][j] = "";
            }
        }
        String Driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Academic_Affairs_Management_System_20211576;encrypt=false";
        String userName = "s20211576"; // 默认用户名
        String userPwd = "s20211576"; // 密码
        Connection dbConn = null;
        try {
            Class.forName(Driver);
            dbConn = DriverManager.getConnection(url, userName, userPwd);
            Statement stmt = dbConn.createStatement();
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int numColumns = rsmd.getColumnCount();
            int row = 0;
            while (rs.next()) {
                for (int col = 0; col < numColumns; col++) {
                    data[row][col] = rs.getString(col + 1);
                }
                row++;
            }
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
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        table.setModel(model);
    }
}
